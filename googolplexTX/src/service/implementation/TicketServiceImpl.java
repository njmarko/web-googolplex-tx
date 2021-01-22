package service.implementation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import model.Customer;
import model.Manifestation;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.ManifestationDAO;
import repository.TicketDAO;
import repository.UserDAO;
import service.TicketService;
import web.dto.TicketSearchDTO;

public class TicketServiceImpl implements TicketService {

	private TicketDAO ticketDAO;
	private UserDAO userDAO;
	private ManifestationDAO manifestationDAO;

	public TicketServiceImpl(TicketDAO ticketDAO, UserDAO userDAO, ManifestationDAO manifestationDAO) {
		super();
		this.ticketDAO = ticketDAO;
		this.userDAO = userDAO;
		this.manifestationDAO = manifestationDAO;
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
		return this.ticketDAO.delete(key);
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
	public Collection<Ticket> findAllBySalesman(String key) {
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
		
		return entities;
	}
	
	@Override
	public Collection<Ticket> searchByUser(String key, TicketSearchDTO searchParams) {
		Collection<Ticket> entities = this.findAllByUser(key);

		
		
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
			critMap.put("name", Comparator.comparing( x -> ((Ticket) x).getManifestation().getName()));
			critMap.put("price", Comparator.comparing(Ticket::getPrice));
			critMap.put("date", Comparator.comparing(Ticket::getDateOfManifestation));
			// TODO add radius search for location

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<Ticket> comp = critMap.get(searchParams.getSortCriteria().toLowerCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			}
		}

		
		
		return entities;
	}

	@Override
	public Collection<Ticket> findAllByManifestation(String key) {
		Manifestation manifestation = manifestationDAO.findOne(key);
		return manifestation.tickets;
	}

}
