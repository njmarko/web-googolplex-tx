package service.implementation;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.Customer;
import model.Manifestation;
import model.User;
import model.enumerations.UserRole;
import repository.UserDAO;
import service.UserService;
import web.dto.UserSearchDTO;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;
	
	public UserServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	@Override
	public Collection<User> findAll() {
		return this.userDAO.findAll()
				.stream()
				.filter((ent)->{return !ent.getDeleted();}).collect(Collectors.toList());
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
				}else {
					return true;
				}											
			}).collect(Collectors.toList());
		}
		
		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			final Map<String, Comparator<User>> critMap = new HashMap<String, Comparator<User>>();
			critMap.put("firstName", Comparator.comparing(User::getFirstName));
			critMap.put("lastName", Comparator.comparing(User::getLastName));
			critMap.put("username", Comparator.comparing(User::getUsername));
			critMap.put("points", (o1,o2)->{
				if (!(o1 instanceof Customer) && !(o2 instanceof Customer)) {
					return 0;
				}else if (!(o1 instanceof Customer)){
					return 1; // second is customer so it should be in front of other types of users
				}else if (!(o2 instanceof Customer)) {
					return -1; // first is customer so it should be in front of other types of users
				}else {
					Customer c1 = (Customer) o1;
					Customer c2 = (Customer) o2;
					if (c1.getPoints() > c2.getPoints()) {
						return -1;
					}else if(c1.getPoints() < c2.getPoints()) {
						return 1;
					}else {
						return 0;
					}
				}
			});

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<User> comp = critMap.get(searchParams.getSortCriteria().toLowerCase().trim());
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
		return this.userDAO.findOne(key);
	}

	@Override
	public User save(User entity) {
		return this.userDAO.save(entity);
	}

	@Override
	public User delete(String key) {
		return this.userDAO.delete(key);
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



}
