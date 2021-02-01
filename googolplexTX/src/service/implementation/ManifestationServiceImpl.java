package service.implementation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import model.Comment;
import model.Customer;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.CommentStatus;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
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
import web.dto.TicketSearchDTO;

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

		entities = searchManifCollection(entities, searchParams);

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
	public Collection<Manifestation> findBySalesman(String salesman, ManifestationSearchDTO searchParams) {
		Collection<Manifestation> entities = this.findAll();
		entities = entities.stream().filter((Manifestation ent) -> {
			return ent.getSalesman().getUsername().equalsIgnoreCase(salesman);
		}).collect(Collectors.toList());
		
		entities = searchManifCollection(entities, searchParams);
		
		return entities;
	}

	private Collection<Manifestation> searchManifCollection(Collection<Manifestation> entities, ManifestationSearchDTO searchParams){
		if (searchParams.getStatus() != null) {	
			entities = entities.stream().filter((ent) -> {
				return ent.getStatus().name().equalsIgnoreCase(searchParams.getStatus());
			}).collect(Collectors.toList());
		}
		
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
		
		// Map of comparators used for sorting
		final Map<String, Comparator<Manifestation>> critMap = new HashMap<String, Comparator<Manifestation>>();
		critMap.put("MANIF_NAME", Comparator.comparing(Manifestation::getName));
		critMap.put("MANIF_DATE", Comparator.comparing(Manifestation::getDateOfOccurence));
		critMap.put("TICKET_PRICE", Comparator.comparing(Manifestation::getRegularPrice));
		critMap.put("LOCATION", (o1, o2) -> {
			// first check city, then street then number
			int cmp = o1.getLocation().getCity().compareTo(o2.getLocation().getCity());
			if (cmp == 0) {
				cmp = o1.getLocation().getStreet().compareTo(o2.getLocation().getStreet());
				if (cmp == 0) {
					cmp = o1.getLocation().getNumber().compareTo(o2.getLocation().getNumber());
				}
			}
			return cmp;
		});

		// It sorts by ascending by default
		if (searchParams.getSortCriteria() != null) {
			Boolean ascending = searchParams.getAscending() != null ? searchParams.getAscending() : true;

			// everything except lattitude and
			// lognitude for now
			// TODO add radius search for location

			// If sortCriteria is wrong it doesn't sort the collection
			Comparator<Manifestation> comp = critMap.get(searchParams.getSortCriteria().toUpperCase().trim());
			if (comp != null) {
				entities = entities.stream().sorted(ascending ? comp : comp.reversed()).collect(Collectors.toList());
			} else {
				// if sort criteria is not set, sort manifestations by most recent date
				
			}
		}else {
			entities = entities.stream().sorted(critMap.get("MANIF_DATE").reversed()).collect(Collectors.toList());
		}
		
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
		if (manifestation == null || manifestation.getComments() == null) {
			return null;
		}
		// TODO: Consider Creating using function to filter deleted like this.findAll
		return manifestation.getComments().stream().filter((Comment ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
	}
	
	@Override
	public Collection<Comment> findAllApprovedCommentsForManif(String key) {
		Collection<Comment> retVal = findAllCommentsFromManifestation(key);
		if (retVal != null) {
			retVal = retVal.stream().filter((c)->{return c.getApproved() == CommentStatus.ACCEPTED;}).collect(Collectors.toList());
		}
		return retVal;
	}

	@Override
	public Manifestation save(ManifestationDTO dto) {

		


		
		Manifestation found = null;
		if (dto.getId() != null) {
			found = manifestationDAO.findOne(dto.getId());
		}
		if (found == null) {
			found = new Manifestation();
			found.setComments(new ArrayList<Comment>());
			found.setTickets(new ArrayList<Ticket>());
			found.setId(manifestationDAO.findNextId());
			
			found.setDeleted(false);	
		}
		
		Instant epochTime = java.time.Instant.ofEpochMilli(dto.getDateOfOccurence());
		LocalDateTime dateOfOccurence = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));
		
		
		// check if manifestation exists at the same place and the same time	
		Collection<Manifestation> manifs = this.findAll();
		if (dto.getId() != null && found.getId() == dto.getId()) {
			// if i edit manifestation i will not check for conflicts with the manifestation that i am editing
			// so i remove it from the list of manifs that i will check for same place and time.
			manifs.remove(found);
		}
		for (Manifestation m : manifs) {
			if (m.getStatus() == ManifestationStatus.ACTIVE
					&& m.getDateOfOccurence().toLocalDate().isEqual(dateOfOccurence.toLocalDate())
					&& m.getLocation().getCity().equals(dto.getLocation().getCity())
					&& m.getLocation().getStreet().equals(dto.getLocation().getStreet())
					&& m.getLocation().getNumber().equals(dto.getLocation().getNumber())
					&& m.getLocation().getZipCode().equals(dto.getLocation().getZipCode())) {
				// this is the case where active manifestation already exists on the same place in the same time
				return null;
			}
		}
		
		

		// TODO: should i check for null and then add, is this save/edit or just save
		found.setName(dto.getName());
		found.setAvailableSeats(dto.getAvailableSeats());
		found.setPoster(dto.getPoster());

		found.setDateOfOccurence(dateOfOccurence);
		
		// i need to change all of the dates for the existing tickets for the manifestation in case of editing of date
		for (Ticket t : found.getTickets()) {
			// i will only change the date for the reserved tickets
			if (t.getTicketStatus() == TicketStatus.RESERVED) {
				t.setDateOfManifestation(dateOfOccurence);
			}
		}
		

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

	@Override
	public Comment save(CommentDTO dto) {
		Comment comment = null;
		Boolean alreadyExists = false;
		if (dto.getId() != null) {
			comment = commentDAO.findOne(dto.getId());
			alreadyExists = true;
		}else {
			comment = new Comment();
			comment.setId(commentDAO.findNextId());
			comment.setDeleted(false);
		}
		if (comment == null) {
			return null;
		}
		
		
		CommentStatus approved = null;
		if (dto.getApproved() == null || dto.getApproved().trim().compareToIgnoreCase("PENDING") == 0) {
			approved = CommentStatus.PENDING;
		}else if (dto.getApproved().trim().compareToIgnoreCase("ACCEPTED") == 0) {
			approved = CommentStatus.ACCEPTED;
		}else if (dto.getApproved().trim().compareToIgnoreCase("REJECTED") == 0) {
			approved = CommentStatus.REJECTED;
		}else {
			return null;
		}
		
		comment.setApproved(approved);
		
		Manifestation manif = null;
		if (dto.getManifestation() != null) {
			manif = manifestationDAO.findOne(dto.getManifestation());
		};
		if (manif == null) {
			return null;
		}
		
		comment.setManifestation(manif);
		
		Customer cust = null;
		if (dto.getCustomer() != null) {
			User tempUser =  userDAO.findOne(dto.getCustomer());
			if (tempUser != null && tempUser.getUserRole() == UserRole.CUSTOMER) {
				cust = (Customer) tempUser;
			}
		}
		if (cust == null) {
			return null;
		}
		comment.setCustomer(cust);
		
		if (dto.getText() != null) {
			comment.setText(dto.getText());
		}else {
			return null;
		}
		
		if (dto.getRating() == null || (dto.getRating() < 1 || dto.getRating() > 5)) {
			return null;
		}else {
			comment.setRating(dto.getRating());
		}
		
		
		
		if (alreadyExists == false) {
			cust.getComments().add(comment);
			manif.getComments().add(comment);
			
			manifestationDAO.save(manif);
			manifestationDAO.saveFile();
			

			userDAO.save(cust);
			userDAO.saveFile();
		}

		commentDAO.save(comment);
		commentDAO.saveFile();
		
		return comment;
	}

	@Override
	public Comment addUniqueComment(CommentDTO dto) {
		//check if comment from this user already exists for this manifestation
		
		if (dto.getCustomer() != null && dto.getManifestation()!= null) {
			User tempUser = userDAO.findOne(dto.getCustomer());
			if (tempUser.getUserRole() == UserRole.CUSTOMER) {
				Customer cust = (Customer) tempUser;
				Boolean hasTicket = false;
				// User has to have a ticket for this manifestation
				for (Ticket t : cust.getTickets()) {
					if (t.getManifestation().getId().equals(dto.getManifestation()) && t.getTicketStatus() == TicketStatus.RESERVED) {
						hasTicket = true;
						break;
					}
				}
				if (hasTicket == false) {
					return null;
				}
				// user must not already have an accepted comment. If accepted comment exists, new one will not be added
				for (Comment com : cust.getComments()) {
					if (com.getManifestation().getId().equals(dto.getManifestation()) && (com.getApproved() == CommentStatus.ACCEPTED || com.getApproved() == CommentStatus.PENDING)) {
						return null;
					}
				}
				
				// Comment can only be added to the manifestation that is finished and is active
				Manifestation manif = manifestationDAO.findOne(dto.getManifestation());
				
				// manif must be active
				if (manif == null || manif.getStatus() == ManifestationStatus.INACTIVE) {
					return null;
				}
				
//				String datePattern = "yyyy-MM-dd";
//				SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
//				
//				String manifDate = sdf.format(manif.getDateOfOccurence().toLocalDate());
//				String currentDate = sdf.format(LocalDateTime.now());
//				
//				// if the current date is before the manifestations date of occurence then commenting is not allowed
//				if (currentDate.compareTo(manifDate)<=0) {
//					return null;
//				}
				
				LocalDate manifDate = manif.getDateOfOccurence().toLocalDate();
				LocalDate currentDate = LocalDate.now();
				
				if (currentDate.isAfter(manifDate)) {
					// rest of the checks are in the save method
					return this.save(dto);	
				}
				else {
					return null;
				}

			}
		}	
		return null;
	}

	@Override
	public Comment updateComment(CommentDTO dto) {
		
		
		
		
		return this.save(dto);
	}


}
