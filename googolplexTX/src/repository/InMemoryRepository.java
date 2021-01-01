package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Comment;
import model.Manifestation;
import model.Ticket;
import model.User;

public class InMemoryRepository {

	private static Map<String, User> users = new ConcurrentHashMap<String, User>();
	private static Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();
	private static Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();
	private static Map<String, Comment> comments = new ConcurrentHashMap<String, Comment>();

	
	
	
	/**
	 * Load all data from the files
	 * @return true if all files were successfully loaded
	 */
	public static Boolean loadData() {
		//TODO implement loading of data
		return false;
	}
	
	
	
	//USER METHODS------------------------------------------------------------------------------------
	public static Map<String, User> findAllUsers() {
		return users;
	}

	public static Map<String, User> setUsers(Map<String, User> users) {
		InMemoryRepository.users = users;
		return users;
	}
	
	public static Map<String, User> saveUsers(){
		//TODO logic for saving in the file
		
//		try {
//			Gson gson = new Gson();
//			FileWriter writer = new FileWriter("data/manifestations.json");
//			gson.toJson(object, writer);
//			writer.flush();
//			writer.close();
//			return object;
//		} catch (JsonIOException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		
		
		return users;
	}
	
	public static User findOneUser(String key) {
		return users.get(key);
	}
	
	
	
	/**
	 * Save an user to the users map.
	 * Username is a natural key for the user and is used in the map
	 * @param user that will be saved
	 * @return user that was saved
	 */
	public static User save(User user) {
		users.put(user.getUsername(), user);
		
		//TODO Do the saving in the file
		
		return user;
	}
	
	public static User deleteUser(String key) {
		User deleted = users.get(key);
		if(deleted != null) {
			deleted.setDeleted(true);
			//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
		}
		return deleted;
	}
	

	
	//MANIFESTATION METHODS------------------------------------------------------------------------------------
	public static Collection<Manifestation> findAllManifestations() {
		return manifestations.values();
	}

	public static Map<String, Manifestation> setManifestations(Map<String, Manifestation> manifestations) {
		InMemoryRepository.manifestations = manifestations;
		return manifestations;
	}
	
	public static Manifestation findOneManifestation(String key) {
		return manifestations.get(key);
	}
	
	
	public static Manifestation save(Manifestation manif) {
		manifestations.put(manif.getId(), manif);
		
		//TODO Do the saving in the file
		
		return manif;
	}
	
	public static Manifestation deleteManifestation(String key) {
		Manifestation deleted = manifestations.get(key);
		if(deleted != null) {
			deleted.setDeleted(true);
			//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
		}
		return deleted;
	}
	
	public static Map<String, Manifestation> saveManifestations(){
		//TODO logic for saving in the file
		
//		try {
//			Gson gson = new Gson();
//			FileWriter writer = new FileWriter("data/manifestations.json");
//			gson.toJson(object, writer);
//			writer.flush();
//			writer.close();
//			return object;
//		} catch (JsonIOException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
		
		
		return manifestations;
	}

	
	//TICKET METHODS------------------------------------------------------------------------------------
	public static Collection<Ticket> findAllTickets() {
		return tickets.values();
	}

	public static Map<String, Ticket> setTickets(Map<String, Ticket> tickets) {
		InMemoryRepository.tickets = tickets;
		return tickets;
	}
	
	public static Map<String, Ticket> saveTickets(){
		//TODO logic for saving in the file
		return tickets;
	}
	
	public static Ticket findOneTicket(String key) {
		return tickets.get(key);
	}
	
	
	public static Ticket save(Ticket ticket) {
		tickets.put(ticket.getId(), ticket);
		
		//TODO Do the saving in the file
		
		return ticket;
	}
	
	public static Ticket deleteTicket(String key) {
		Ticket deleted = tickets.get(key);
		if(deleted != null) {
			deleted.setDeleted(true);
			//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
		}
		return deleted;
	}

	//COMMENT METHODS------------------------------------------------------------------------------------
	public static Map<String, Comment> findAllComments() {
		return comments;
	}

	public static Map<String, Comment> setComments(Map<String, Comment> comments) {
		InMemoryRepository.comments = comments;
		return comments;
	}
	
	public static Map<String, Comment> saveComments(){
		//TODO logic for saving in the file
		return comments;
	}
	
	public static Comment findOneComment(String key) {
		return comments.get(key);
	}
	
	
	public static Comment save(Comment comment) {
		//TODO add this code when the model is merged because now there is no id in comment
		//tickets.put(comment.getId(), comment);
		
		//TODO Do the saving in the file
		
		return comment;
	}
	
	public static Comment deleteComment(String key) {
		Comment deleted = comments.get(key);
		if(deleted != null) {
			deleted.setDeleted(true);
			//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
		}
		return deleted;
	}
	

	
	
}
