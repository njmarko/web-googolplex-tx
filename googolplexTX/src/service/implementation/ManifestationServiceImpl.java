package service.implementation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.Comment;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.UserRole;
import model.Manifestation;
import model.Ticket;
import repository.CommentDAO;
import repository.ManifestationDAO;
import repository.ManifestationTypeDAO;
import repository.UserDAO;
import service.ManifestationService;
import web.dto.CommentDTO;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;

public class ManifestationServiceImpl implements ManifestationService {

	private ManifestationDAO manifestationDAO;
	private ManifestationTypeDAO manifestationTypeDAO;
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	// TODO: Add required DAOs

	public ManifestationServiceImpl(ManifestationDAO manifestationDAO, ManifestationTypeDAO manifestationTypeDAO, UserDAO userDao, CommentDAO commentDAO) {
		super();
		this.manifestationDAO = manifestationDAO;
		this.manifestationTypeDAO = manifestationTypeDAO;
		this.userDAO = userDao;
		this.commentDAO = commentDAO;
	}

	@Override
	public Collection<Manifestation> findAll() {
		Collection<Manifestation> entities = this.manifestationDAO.findAll();
		entities = entities.stream().filter((Manifestation ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
		return entities;
	}

	@Override
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams) {
		Collection<Manifestation> entities = this.findAll();

		// Search
		if (searchParams.getName() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getName().toLowerCase().contains(searchParams.getName().toLowerCase());
			}).collect(Collectors.toList());
		}

		// From local date time to miliseconds
//		date.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();

		if (searchParams.getBeginDate() != null) {

			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getBeginDate());

			LocalDateTime beginDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfOccurence().isAfter(beginDate);
			}).collect(Collectors.toList());
		}

		if (searchParams.getEndDate() != null) {
			Instant epochTime = java.time.Instant.ofEpochMilli(searchParams.getEndDate());

			LocalDateTime endDate = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));

			entities = entities.stream().filter((ent) -> {
				return ent.getDateOfOccurence().isBefore(endDate);
			}).collect(Collectors.toList());
		}

		// Location in searchParams is just a string for City name OR Country name
		// TODO Add check for Country name if it is implemented in the model
		if (searchParams.getLocation() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getLocation().getCity().equalsIgnoreCase(searchParams.getLocation());
			}).collect(Collectors.toList());
		}

		if (searchParams.getMinPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getRegularPrice() >= searchParams.getMinPrice();
			}).collect(Collectors.toList());
		}

		if (searchParams.getMaxPrice() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getRegularPrice() <= searchParams.getMaxPrice();
			}).collect(Collectors.toList());
		}


		if (searchParams.getManifestationType() != null) {
			entities = entities.stream().filter((ent) -> {
				return ent.getManifestationType().getName().equalsIgnoreCase(searchParams.getManifestationType());
			}).collect(Collectors.toList());
		}

		if (searchParams.getHasAvailableTickets() != null && searchParams.getHasAvailableTickets() == true) {
			entities = entities.stream().filter((ent) -> {
				return ent.getTickets().size() < ent.getAvailableSeats();
			}).collect(Collectors.toList());
		}

		// It sorts by ascending by default
		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			final Map<String, Comparator<Manifestation>> critMap = new HashMap<String, Comparator<Manifestation>>();
			critMap.put("name", Comparator.comparing(Manifestation::getName));
			critMap.put("date", Comparator.comparing(Manifestation::getDateOfOccurence));
			critMap.put("price", Comparator.comparing(Manifestation::getRegularPrice));
			critMap.put("location", (o1, o2)->{ 
				// first check city, then street then number
				int cmp = o1.getLocation().getCity().compareTo(o2.getLocation().getCity());
				if (cmp == 0) {
					cmp = o1.getLocation().getStreet().compareTo(o2.getLocation().getStreet());
					if (cmp == 0) {
						cmp = o1.getLocation().getNumber().compareTo(o2.getLocation().getNumber());
					}
				}
				return cmp;}); // everything except lattitude and
																					// lognitude for now
			// TODO add radius search for location

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<Manifestation> comp = critMap.get(searchParams.getSortCriteria().toLowerCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			}
		}

		return entities;

	}

	public static int ascending(Manifestation o1, Manifestation o2) {

		return 0;
	}

	@Override
	public Manifestation findOne(String key) {
		Manifestation found = this.manifestationDAO.findOne(key);
		if (found != null && found.getDeleted()) {
			return null;
		}
		return found;
	}

	@Override
	public Manifestation save(Manifestation entity) {
		Manifestation manif = this.manifestationDAO.save(entity);
		this.manifestationDAO.saveFile();
		return manif;
	}

	@Override
	public Manifestation delete(String key) {
		return this.manifestationDAO.delete(key);
	}

	@Override
	public Manifestation update(Manifestation entity) {
		return this.manifestationDAO.save(entity);
	}

	@Override
	public Collection<Manifestation> findBySalesman(String salesman) {
		Collection<Manifestation> entities = this.findAll();
		entities = entities.stream().filter((Manifestation ent) -> {
			return ent.getSalesman().getUsername().equalsIgnoreCase(salesman);
		}).collect(Collectors.toList());
		return entities;
	}

	@Override
	public Collection<ManifestationType> findAllManifestationTypes() {
		return manifestationTypeDAO.findAll().stream().filter((ManifestationType ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}

	@Override
	public Collection<Comment> findAllCommentsFromManifestation(String key) {
		Manifestation manifestation = this.findOne(key);
		
		// TODO: Consider Creating using function to filter deleted like this.findAll
		return manifestation.getComments().stream().filter((Comment ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}


	@Override
	public Manifestation save(ManifestationDTO dto) {
		
		Manifestation found = null;
		if (dto.getId() != null) {
			found = manifestationDAO.findOne(dto.getId());
		}
		if (found == null) {
			found = new Manifestation();
			found.setId(manifestationDAO.findNextId());
			found.setDeleted(false);
		}
		
		// TODO: should i check for null and then add, is this save/edit or just save
		found.setName(dto.getName());
		found.setAvailableSeats(dto.getAvailableSeats());
		found.setPoster(dto.getPoster());
		
		Instant epochTime = java.time.Instant.ofEpochMilli(dto.getDateOfOccurence());
		LocalDateTime dateOfOccurence = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));
		found.setDateOfOccurence(dateOfOccurence);
		
		found.setRegularPrice(dto.getRegularPrice());
		
		
		ManifestationStatus status = null;
		if (dto.getStatus().trim().equalsIgnoreCase(ManifestationStatus.ACTIVE.name())) {
			status = ManifestationStatus.ACTIVE;
		} else if (dto.getStatus().trim().equalsIgnoreCase(ManifestationStatus.INACTIVE.name())) {
			status = ManifestationStatus.INACTIVE;
		} else {
			return null;
		}
		found.setStatus(status);
		

		ManifestationType mType = manifestationTypeDAO.findOne(dto.getManifestationType());
		if (mType == null)
			return null;
		found.setManifestationType(mType);
		
		// TODO add poster
		
		
		User foundUser = userDAO.findOne(dto.getSalesman());
		if (foundUser == null || foundUser.getUserRole() != UserRole.SALESMAN) {
			return null;
		}
		
		found.setSalesman((Salesman) foundUser);
		
		found.setLocation(dto.getLocation());
		
		// Tickets and comments are added with other methods and are not assigned here.
		
		if (found.getComments() == null) {
			found.setComments(new ArrayList<Comment>());
		}
		
		if (found.getTickets() == null) {
			found.setTickets(new ArrayList<Ticket>());
		}
		
		manifestationDAO.save(found);
		manifestationDAO.saveFile();
		
		return found;
	}

	@Override
	public Comment deleteComment(String key) {
		// TODO: save to file
		return commentDAO.delete(key);
	}
	
	@Override
	public Comment findOneComment(String key) {
		Comment found = this.commentDAO.findOne(key);
		if (found != null && found.getDeleted()) {
			return null;
		}
		return found;
	}

	@Override
	public ManifestationType deleteOneManifestationType(String key) {
		// TODO: save to file
		return manifestationTypeDAO.delete(key);
	}


}
