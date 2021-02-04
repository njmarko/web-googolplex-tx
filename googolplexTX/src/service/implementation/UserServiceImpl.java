package service.implementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.Customer;
import model.CustomerType;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.Gender;
import model.enumerations.TicketStatus;
import model.enumerations.UserRole;
import repository.CustomerTypeDAO;
import repository.UserDAO;
import service.UserService;
import support.DateConverter;
import web.dto.CustomerTypeDTO;
import web.dto.LoginDTO;
import web.dto.PasswordDTO;
import web.dto.RegisterDTO;
import web.dto.SuspiciousSearchDTO;
import web.dto.UserDTO;
import web.dto.UserSearchDTO;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;
	private CustomerTypeDAO custTypeDAO;

	public UserServiceImpl(UserDAO userDAO, CustomerTypeDAO custTypeDAO) {
		super();
		this.userDAO = userDAO;
		this.custTypeDAO = custTypeDAO;
	}

	@Override
	public Collection<User> findAll() {
		return this.userDAO.findAll().stream().filter((ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}

	@Override
	public Collection<User> search(UserSearchDTO searchParams) {

		Collection<User> entities = this.findAll();

		// Search

		if (searchParams.getFirstName() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getFirstName().toLowerCase().contains(searchParams.getFirstName().toLowerCase());
			}).collect(Collectors.toList());
		}

		if (searchParams.getLastName() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getLastName().toLowerCase().contains(searchParams.getLastName().toLowerCase());
			}).collect(Collectors.toList());
		}

		if (searchParams.getUsername() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getUsername().toLowerCase().contains(searchParams.getUsername().toLowerCase());
			}).collect(Collectors.toList());
		}

		// Filter
		if (searchParams.getUserRole() != null) {
			try {
				UserRole role = UserRole.valueOf(searchParams.getUserRole());
				entities = entities.stream().filter((ent) -> {
					return ent.getUserRole() == role;
				}).collect(Collectors.toList());

			} catch (Exception e) {
				// TODO decide how to handle when wrong user role is forwarded
				System.out.println("Couldn't parse user role from parameters!");
			}
		}
		if (searchParams.getCustomerType() != null) {
			entities = entities.stream().filter((ent) -> {
				if (ent.getUserRole() != null && ent.getUserRole() == UserRole.CUSTOMER && (ent instanceof Customer)) {
					return ((Customer) ent).getCustomerType().getName().equals(searchParams.getCustomerType());
				} else {
					return false;
				}
			}).collect(Collectors.toList());
		}

		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			final Map<String, Comparator<User>> critMap = new HashMap<String, Comparator<User>>();
			critMap.put("FIRST_NAME", (o1,o2)->{return o1.getFirstName().trim().compareToIgnoreCase(o2.getFirstName().trim());});
			critMap.put("LAST_NAME", (o1,o2)->{return o1.getLastName().trim().compareToIgnoreCase(o2.getLastName().trim());});
			critMap.put("USERNAME", (o1,o2)->{return o1.getUsername().trim().compareToIgnoreCase(o2.getUsername().trim());});
			critMap.put("POINTS", (o1, o2) -> {
				if (!(o1 instanceof Customer) && !(o2 instanceof Customer)) {
					return 0;
				} else if (!(o1 instanceof Customer)) {
					return 1; // second is customer so it should be in front of other types of users
				} else if (!(o2 instanceof Customer)) {
					return -1; // first is customer so it should be in front of other types of users
				} else {
					Customer c1 = (Customer) o1;
					Customer c2 = (Customer) o2;
					if (c1.getPoints() > c2.getPoints()) {
						return 1;
					} else if (c1.getPoints() < c2.getPoints()) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<User> comp = critMap.get(searchParams.getSortCriteria().toUpperCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			}
		}

		return entities;
	}

//
//	// Sort by name, surname, username, points
//	private Boolean ascending;
//	private String sortCriteria;
//
//	// Filter
//	private String userRole; // enumeration in our code
//	private String customerType; // class in our code

	@Override
	public User findOne(String key) {
		User user = this.userDAO.findOne(key);
		if (user != null && user.getDeleted()) {
			return null;
		}
		return user;
	}

	@Override
	public User save(User entity) {
		return this.userDAO.save(entity);
	}

	@Override
	public User delete(String key) {
		User user = findOne(key);
		if (user == null) {
			return null;
		}
		if (user.getUserRole() == UserRole.ADMIN)
			return user;
		
		user.setDeleted(true);
		return user;
	}

	/**
	 * Update all but password
	 */
	@Override
	public User update(User entity) {
		User user = findOne(entity.getUsername());
		entity.setPassword(user.getPassword());
		return this.userDAO.save(entity);
	}
	
	
	
	

	@Override
	public User registerUser(RegisterDTO registerData) {
		User user = userDAO.findOne(registerData.getUsername());
		if (user != null) {
			return null;
		}

		UserRole role = UserRole.CUSTOMER;
		// Validation for what type of user can be created was done in the validation
		// method of the dto
		if (registerData.getUserRole() != null) {
			if (registerData.getUserRole().compareToIgnoreCase("CUSTOMER")==0) {
				role = UserRole.CUSTOMER;
			} else if (registerData.getUserRole().compareToIgnoreCase("SALESMAN")==0) {
				role = UserRole.SALESMAN;
			} else {
				// Admin creation is not allowed. Additional admin checks exist in validate
				// method in RegisterDTO
				return null;
			}
		}

		Gender gender = null;
		if (registerData.getGender().trim().equalsIgnoreCase(Gender.MALE.toString())) {
			gender = Gender.MALE;
		} else if (registerData.getGender().trim().equalsIgnoreCase(Gender.FEMALE.toString())) {
			gender = Gender.FEMALE;
		} else {
			return null;
		}

		LocalDate birthDate = null;
		if (registerData.getBirthDate() != null) {
			Instant epochTime = Instant.ofEpochMilli(registerData.getBirthDate());
			birthDate = LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC")).toLocalDate();
		} else {
			return null;
		}

		if (role == UserRole.CUSTOMER) {

			CustomerType custType = this.determineCustomerType(0d);
			if (custType == null) {
				return null;
			}

			user = new Customer(registerData.getUsername(), registerData.getPassword1(), registerData.getFirstName(),
					registerData.getLastName(), gender, birthDate, role, 0d, custType);
		}else if (role == UserRole.SALESMAN) {
			user = new Salesman(registerData.getUsername(), registerData.getPassword1(), registerData.getFirstName(),
					registerData.getLastName(), gender, birthDate, role);
		}
		
		userDAO.save(user);
		// We want to manually call save file function
		userDAO.saveFile();

		return user;
	}

	@Override
	public CustomerType determineCustomerType(Double points) {

		Collection<CustomerType> custTypes = custTypeDAO.findAll();

		if (custTypes == null) {
			return null;
		}

		// sort customer types based on required points
		custTypes = custTypes.stream().sorted(Comparator.comparing(CustomerType::getRequiredPoints))
				.collect(Collectors.toList());

		// set the first customer type
		CustomerType adequateType = custTypes.iterator().next();

		// iterate to see if there are higher tiers that customer qualifies for

		for (CustomerType customerType : custTypes) {
			if (customerType.getRequiredPoints() <= points) {
				adequateType = customerType;
			}
		}

		return adequateType;
	}

	@Override
	public User login(LoginDTO loginData) {
		User found = findOne(loginData.getUsername());
		if (found == null || !found.getPassword().equals(loginData.getPassword())) {
			return null;
		}
		return found;
	}

	/**
	 * Currently sets firstName, lastName, gender and birthDate.
	 * Use other methods to set othe user attributes.
	 */
	@Override
	public User update(UserDTO dto) {
		
		User user = userDAO.findOne(dto.getUsername());
		if (user == null) {
			return null;
		}
		
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		Gender gender = null;
		if (dto.getGender().trim().equalsIgnoreCase(Gender.MALE.toString())) {
			gender = Gender.MALE;
		} else if (dto.getGender().trim().equalsIgnoreCase(Gender.FEMALE.toString())) {
			gender = Gender.FEMALE;
		} else {
			gender = user.getGender();
		}
		user.setGender(gender);
		
		LocalDate birthDate = null;
		if (dto.getBirthDate() != null) {
			Instant epochTime = Instant.ofEpochMilli(dto.getBirthDate());
			birthDate = LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC")).toLocalDate();
		} else {
			birthDate = user.getBirthDate();
		}
		user.setBirthDate(birthDate);
		
		userDAO.save(user);
		// We want to manually call save file function
		userDAO.saveFile();
		
		return user;
	}

	@Override
	public User changePassword(PasswordDTO dto) {
		User user = this.findOne(dto.getUsername());
		if (user != null && user.getPassword().compareTo(dto.getOldPassword()) == 0) {
			user.setPassword(dto.getNewPassword());
			this.userDAO.save(user);
			this.userDAO.saveFile();
			return user;
		}
		return null;
	}

	@Override
	public Collection<User> findUsersThatBoughtFromSalesman(String key) {
		// TODO: TEST

		User user = userDAO.findOne(key);
		if (user.getUserRole() != UserRole.SALESMAN) {
			return null;
		}
		Salesman salesman = (Salesman) user;
		
		Collection<Ticket> tickets = new ArrayList<Ticket>();
		for (Manifestation manifestation : salesman.getManifestation()) {
			tickets.addAll(manifestation.getTickets());
		}
		
		Set<User> users = new HashSet<User>();
		for (Ticket ticket : tickets) {
			if (ticket.getTicketStatus() == TicketStatus.RESERVED)
				users.add(ticket.getCustomer());
		}
		
		return users;
		
	}

	@Override
	public Collection<CustomerType> findAllCustomerTypes() {
		return this.custTypeDAO.findAll().stream().filter((ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}
	
	@Override
	public CustomerType findOneCustomerType(String key) {
		CustomerType found = this.custTypeDAO.findOne(key);
		if (found == null || found.getDeleted())
			return null;
		return found;
	}

	@Override
	public CustomerType deleteOneCustomerType(String key) {
		// TODO: save to file
		return custTypeDAO.delete(key);
	}

	@Override
	public CustomerType putOneCustomerType(String key, CustomerTypeDTO dto) {
		CustomerType foundEntity = this.findOneCustomerType(dto.getName());
		if (!dto.getName().equalsIgnoreCase(key) && foundEntity != null)	// Already exists
			return null;
		
		CustomerType customerType = null;
		if (key != null) {
			customerType = this.findOneCustomerType(key);
			if(customerType == null)
				return null;
			
		}
		
		if (customerType == null) {
			customerType = new CustomerType();
			customerType.setDeleted(false);
		} else if (!dto.getName().equalsIgnoreCase(key)) {
			
			custTypeDAO.delete(key);
			customerType = new CustomerType();
			customerType.setDeleted(false);
			
		}
		
		customerType.setName(dto.getName());
		customerType.setDiscount(dto.getDiscount());
		customerType.setRequiredPoints(dto.getRequiredPoints());
		custTypeDAO.save(customerType);
		custTypeDAO.saveFile();
		
		return customerType;
	}
	
	@Override
	public CustomerType postOneCustomerType(CustomerTypeDTO dto) {
		CustomerType customerType = this.findOneCustomerType(dto.getName());
		if (customerType != null)	// Already exists
			return null;
		
		customerType = new CustomerType();
		customerType.setDeleted(false);	
		customerType.setName(dto.getName());
		customerType.setDiscount(dto.getDiscount());
		customerType.setRequiredPoints(dto.getRequiredPoints());
		custTypeDAO.save(customerType);
		custTypeDAO.saveFile();
		
		return customerType;
	}
	
	private int countCancelations (Customer customer,  LocalDateTime begin, LocalDateTime end) {
		Collection<Ticket> tickets = customer.getTickets();
		int count = 0;
		for (Ticket ticket : tickets) {
			LocalDateTime cancelDate = ticket.getCancelationDate();
			if (cancelDate == null)
				continue;
			if (cancelDate.isBefore(begin))
				continue;
			if (cancelDate.isAfter(end))
				continue;
			count++;
				
		}
		return count;
	}
	
	@Override
	public Collection<User> findAllSuspiciousCustomers(SuspiciousSearchDTO dto) {
		Collection<User> allUsers = this.findAll();

		if (dto.getFrequency() == null) 
			dto.setFrequency(5);

		if (dto.getStartDate() == null)
			dto.setStartDate( DateConverter.dateToInt(LocalDateTime.now().minusDays(30)));;
	
		if (dto.getEndDate() == null)
			dto.setEndDate( DateConverter.dateToInt(LocalDateTime.now()));;
		
		
		LocalDateTime startDate = DateConverter.dateFromInt(dto.getStartDate());
		LocalDateTime endDate = DateConverter.dateFromInt(dto.getEndDate());
		Integer frequency = dto.getFrequency();
		
		allUsers = allUsers.stream().filter((User u) -> {
			return (u.getUserRole() == UserRole.CUSTOMER && this.countCancelations((Customer) u, startDate, endDate) >= frequency);
		}).collect(Collectors.toList());
		
		
		
		return allUsers;
	}

	@Override
	public User blockUser(String key) {
		User user = this.findOne(key);
		if (user == null)
			return null;
		
		if (user.getUserRole() != UserRole.ADMIN)
			user.setBlocked(true);
		
		return user;
	}

	@Override
	public User unblockUser(String key) {
		User user = this.findOne(key);
		if (user == null || user.getUserRole() == UserRole.ADMIN)
			return null;
		
		if (user.getUserRole() != UserRole.ADMIN)
			user.setBlocked(false);
		
		return user;
	}

	
}
