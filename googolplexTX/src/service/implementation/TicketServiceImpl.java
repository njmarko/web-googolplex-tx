package service.implementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.Customer;
import model.CustomerType;
import model.Manifestation;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.CustomerTypeDAO;
import repository.ManifestationDAO;
import repository.TicketDAO;
import repository.UserDAO;
import service.TicketService;
import web.dto.ReservationDTO;
import web.dto.TicketSearchDTO;

public class TicketServiceImpl implements TicketService {

	private TicketDAO ticketDAO;
	private UserDAO userDAO;
	private ManifestationDAO manifestationDAO;
	private CustomerTypeDAO custTypeDAO;

	private static final Map<TicketType, Double> typePrices;
    static {
        Map<TicketType, Double> tmpMap = new HashMap<TicketType, Double>();
        tmpMap.put(TicketType.REGULAR, 1.0);
        tmpMap.put(TicketType.FAN_PIT, 2.0);
        tmpMap.put(TicketType.VIP, 4.0);
        typePrices = Collections.unmodifiableMap(tmpMap);
    }
	
	public TicketServiceImpl(TicketDAO ticketDAO, UserDAO userDAO, ManifestationDAO manifestationDAO,  CustomerTypeDAO custTypeDAO) {
		super();
		this.ticketDAO = ticketDAO;
		this.userDAO = userDAO;
		this.manifestationDAO = manifestationDAO;
		this.custTypeDAO = custTypeDAO;

	}

	@Override
	public Collection<Ticket> findAll() {
		Collection<Ticket> entities = this.ticketDAO.findAll();
		entities = entities.stream().filter((Ticket ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
		return entities;
	}

	@Override
	public Ticket findOne(String key) {
		Ticket found = this.ticketDAO.findOne(key);
		if (found != null && found.getDeleted()) {
			return null;
		}
		return found;
		
	}

	@Override
	public Ticket save(Ticket entity) {
		return this.ticketDAO.save(entity);
	}

	@Override
	public Ticket delete(String key) {
		Ticket deleted = this.ticketDAO.delete(key);
		ticketDAO.saveFile();
		return deleted;
	}

	@Override
	public Ticket update(Ticket entity) {
		return this.ticketDAO.save(entity);
	}

	@Override
	public Collection<Ticket> findAllByUser(String key) {
		User user = userDAO.findOne(key);

		if (user != null && user.getUserRole().equals(UserRole.CUSTOMER)) {
			return ((Customer) user).getTickets().stream().filter(x -> !x.getDeleted()).collect(Collectors.toList());
		}
		return null;
		
	}

	@Override
	public Collection<Ticket> findAllBySalesman(String key, TicketSearchDTO searchParams) {
		// TODO: TEST
		User user = userDAO.findOne(key);
		if (user.getUserRole() != UserRole.SALESMAN) {
			return null;
		}
		Salesman salesman = (Salesman) user;
		
		Collection<Ticket> entities = new ArrayList<Ticket>();
		for (Manifestation manifestation : salesman.getManifestation()) {
			
			entities.addAll(manifestation.getTickets().stream().filter((Ticket ticket) -> {
				return ticket.getTicketStatus() == TicketStatus.RESERVED;
			}).collect(Collectors.toList()));
						
		}
		
		searchParams.setTicketStatus(TicketStatus.RESERVED.name());
		
		entities = searchTicketCollection(entities, searchParams);
		
		return entities;
	}
	
	@Override
	public Collection<Ticket> search(String username, TicketSearchDTO searchParams) {
		Collection<Ticket> entities;
		if (username == null) {
			entities = this.findAll();
		}else {
			entities = this.findAllByUser(username);
		}	
		
		entities = searchTicketCollection(entities, searchParams);
				
		return entities;
	}
	
	private Collection<Ticket> searchTicketCollection(Collection<Ticket> entities, TicketSearchDTO searchParams){
		
		if (searchParams.getManifestationName() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getManifestation().getName().toLowerCase().contains(searchParams.getManifestationName().toLowerCase());
			}).collect(Collectors.toList());
		}

		if (searchParams.getBeginDate() != null) {

			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getBeginDate());

			LocalDateTime beginDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfManifestation().isAfter(beginDate);
			}).collect(Collectors.toList());
		}

		if (searchParams.getEndDate() != null) {
			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getEndDate());

			LocalDateTime endDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfManifestation().isBefore(endDate);
			}).collect(Collectors.toList());
		}
		
		if (searchParams.getMinPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getPrice() >= searchParams.getMinPrice();
			}).collect(Collectors.toList());
		}

		if (searchParams.getMaxPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getPrice() <= searchParams.getMaxPrice();
			}).collect(Collectors.toList());
		}
		
		// Filter
		// TODO: Check is equals correct
		if (searchParams.getTicketType() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getTicketType().equals(TicketType.valueOf(searchParams.getTicketType()));
			}).collect(Collectors.toList());
		}
		
		if (searchParams.getTicketStatus() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getTicketStatus().equals(TicketStatus.valueOf(searchParams.getTicketStatus()));
			}).collect(Collectors.toList());
		}
		
		// Sort
		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			final Map<String, Comparator<Ticket>> critMap = new HashMap<String, Comparator<Ticket>>();
			critMap.put("MANIF_NAME", (o1,o2)->{return o1.getManifestation().getName().trim().compareToIgnoreCase(o2.getManifestation().getName().trim());});
			critMap.put("TICKET_PRICE", Comparator.comparing(Ticket::getPrice));
			critMap.put("MANIF_DATE", Comparator.comparing(Ticket::getDateOfManifestation));
			// TODO add radius search for location

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<Ticket> comp = critMap.get(searchParams.getSortCriteria().toUpperCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			}
		}
		
		return entities;
	}
	

	@Override
	public Collection<Ticket> findAllByManifestation(String key) {
		Manifestation manifestation = manifestationDAO.findOne(key);
		return manifestation.getTickets();
	}

	@Override
	public Collection<Ticket> reserve(ReservationDTO reservation) {
		// CUSTOMER
		
		User user = userDAO.findOne(reservation.getCustomer());
		if (user.getUserRole() != UserRole.CUSTOMER) {
			throw new IllegalArgumentException("Only users can reserve ticket");
		}
		Customer customer = (Customer) user;
		
		// MANIFESTATION
		Manifestation manifestation = manifestationDAO.findOne(reservation.getManifestation());
		
		if (manifestation.getStatus() == ManifestationStatus.INACTIVE) {
			throw new IllegalArgumentException("You can reserver ticket only from ACTIVE manifestations");
		}
		
		
		int currentSeats = manifestation.getAvailableSeats();
		if (currentSeats - reservation.getQuantity() < 0) {
			throw new IllegalArgumentException("No seats available");
		}
		manifestation.setAvailableSeats(currentSeats - reservation.getQuantity());
		
		
		
		Double total = calculatePrice(reservation.getTicketType(), reservation.getQuantity(), manifestation.getRegularPrice(), customer.getCustomerType());
		Double points = calculatePoints(total);
		
		double customerPoints = customer.getPoints() + points;
		customer.setPoints(customerPoints);
		customer.setCustomerType(this.determineCustomerType(customerPoints));
		
		
		Collection<Ticket> tickets = new ArrayList<Ticket>();
		
		Double individualTicketPrice = ((double) Math.round(total/reservation.getQuantity() * 10)) / 10;
		for (int i = 0; i < reservation.getQuantity() ; ++i) {
			Ticket ticket = new Ticket(ticketDAO.findNextId(), manifestation.getDateOfOccurence(),individualTicketPrice , reservation.getTicketType(), TicketStatus.RESERVED, null, false, customer, manifestation);
			customer.getTickets().add(ticket);
			manifestation.getTickets().add(ticket);
			
			tickets.add(ticket);
			ticketDAO.save(ticket);
		}

		
		// TODO: should I save
		ticketDAO.saveFile();
		userDAO.saveFile();
		manifestationDAO.saveFile();
		return tickets;
	}

	@Override
	public CustomerType determineCustomerType(Double points) {

		Collection<CustomerType> custTypes = this.findallCustomerTypes();

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
	
	public static double calculatePrice(TicketType ticketType, int quantity, double regularPrice, CustomerType custType) {
		double multiplier = typePrices.get(ticketType);
		// if discount is 5% i will multiply total price with 0.95
		double discount = 1d -custType.getDiscount() /100d; 
		double total = regularPrice * multiplier * quantity * discount;
		return ((double) Math.round(total * 10)) / 10;

	}
	
	public static double calculatePoints(double total) {
		double points = total / 1000 * 133;
		return ((double) Math.round(points * 10)) / 10;
	}

	public static double calculateLosedPoints(double total) {
		double points = total / 1000 * 133 * 4;
		return ((double) Math.round(points * 10)) / 10;
	}
	
	@Override
	public Ticket cancelTicket(String key) {
		Ticket ticket = findOne(key);
		if (ticket == null)
			return null;
		
		LocalDateTime weekAgoManif = ticket.getDateOfManifestation().minusDays(7);
		if (weekAgoManif.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("You cannot cancel manifestation in 7 days before beginning");
		}
		
		double losedPoints = calculateLosedPoints(ticket.getPrice());
		
		Manifestation manifestation = ticket.getManifestation();
		Customer customer = ticket.getCustomer();
		
		manifestation.setAvailableSeats(manifestation.getAvailableSeats() + 1);
		customer.setPoints(customer.getPoints() - losedPoints);
		
		ticket.setCancelationDate(LocalDateTime.now());
		ticket.setTicketStatus(TicketStatus.CANCELED);
		
		CustomerType newType = determineCustomerType(customer.getPoints());
		if (newType != null) {
			customer.setCustomerType(newType);
		}
		
		ticketDAO.saveFile();
		userDAO.saveFile();
		manifestationDAO.saveFile();
		return ticket;
	}

	@Override
	public Collection<CustomerType> findallCustomerTypes() {
		return this.custTypeDAO.findAll().stream().filter((ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}

}
