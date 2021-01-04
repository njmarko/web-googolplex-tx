package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import model.Comment;
import model.CustomerType;
import model.Manifestation;
import model.ManifestationType;
import model.Ticket;
import model.User;
import support.FileToJsonAdapter;
import support.JsonToFileAdapter;

public class InMemoryRepository {

	public static Map<String, User> users = new ConcurrentHashMap<String, User>();
	private static Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();
	private static Map<String, Ticket> tickets = new ConcurrentHashMap<String, Ticket>();
	private static Map<String, Comment> comments = new ConcurrentHashMap<String, Comment>();
	private static Map<String, CustomerType> customerTypes = new ConcurrentHashMap<String, CustomerType>();
	private static Map<String, ManifestationType> manifestationTypes = new ConcurrentHashMap<String, ManifestationType>();
	
	
	
	/**
	 * Load all data from the files
	 * @return true if all files were successfully loaded
	 */
	public static Boolean loadData() {
		try {
			loadCustomerTypes();
			loadManifestationTypes();
			loadBareUsers();
			loadBareManifestations();
			loadBareTickets();
			loadBareComments();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private static Boolean loadCustomerTypes() throws IOException {
		Gson gson = FileToJsonAdapter.customerTypeSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/customerTypes.json"));
	    
		Type mapType = new TypeToken<Map<String, CustomerType>>() {}.getType();
	    InMemoryRepository.customerTypes = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitani customer typovi");
	    
		return true;
	}
	
	private static Boolean loadManifestationTypes() throws IOException {
		Gson gson = FileToJsonAdapter.manifestationTypeSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/manifestationTypes.json"));
	    
		Type mapType = new TypeToken<Map<String, ManifestationType>>() {}.getType();
	    InMemoryRepository.manifestationTypes = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitani manifestation typovi");
	    
		return true;
	}
	
	private static Boolean loadBareUsers() throws IOException {
		Gson gson = FileToJsonAdapter.usersSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/users.json"));
	    
		Type mapType = new TypeToken<Map<String, User>>() {}.getType();
	    InMemoryRepository.users = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitani useri");
	    
		return true;
		
	}
	
	private static Boolean loadBareManifestations() throws IOException {
		Gson gson = FileToJsonAdapter.manifestationsSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/manifestations.json"));
	    
		Type mapType = new TypeToken<Map<String, Manifestation>>() {}.getType();
	    InMemoryRepository.manifestations = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitane manifestacije");
	    
		return true;
		
	}

	
	private static Boolean loadBareTickets() throws IOException {
		Gson gson = FileToJsonAdapter.ticketsSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/tickets.json"));
	    
		Type mapType = new TypeToken<Map<String, Ticket>>() {}.getType();
	    InMemoryRepository.tickets = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitani tiketi");
	    
		return true;
		
	}
	

	private static Boolean loadBareComments() throws IOException {
		Gson gson = FileToJsonAdapter.commentsSerializationFromFile();
		
	    Reader reader = Files.newBufferedReader(Paths.get("data/comments.json"));
	    
		Type mapType = new TypeToken<Map<String, Comment>>() {}.getType();
	    InMemoryRepository.comments = gson.fromJson(reader, mapType);
		
	    System.out.println("[DBG] Ucitani komentari");
	    
		return true;
		
	}
	
	
	//USER METHODS------------------------------------------------------------------------------------
	public static Collection<User> findAllUsers() {
		return users.values();
	}

	public static Map<String, User> setUsers(Map<String, User> users) {
		InMemoryRepository.users = users;
		return users;
	}
	
	public static Map<String, User> saveUsers(){


		System.out.println("[LOG] Users saving...");
		try {
			Gson gson = JsonToFileAdapter.userSerializationToFile();

			FileWriter writer = new FileWriter("data/users.json");
			gson.toJson(users, writer);
			writer.flush();
			writer.close();
			return users;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		System.out.println("[LOG] Manifestation saving");
		try {
			Gson gson = JsonToFileAdapter.manifestationSeraialization();

			FileWriter writer = new FileWriter("data/manifestations.json");
			gson.toJson(manifestations, writer);
			writer.flush();
			writer.close();
			return manifestations;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		System.out.println("[LOG] Tickets saving...");
		try {
			Gson gson = JsonToFileAdapter.ticketsSeraialization();

			FileWriter writer = new FileWriter("data/tickets.json");
			gson.toJson(tickets, writer);
			writer.flush();
			writer.close();
			return tickets;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		System.out.println("[LOG] Comments saving");
		try {
			Gson gson = JsonToFileAdapter.commentsSerializationToFile();

			FileWriter writer = new FileWriter("data/comments.json");
			gson.toJson(InMemoryRepository.comments, writer);
			writer.flush();
			writer.close();
			return InMemoryRepository.comments;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return InMemoryRepository.comments;
	}
	
	public static Comment findOneComment(String key) {
		return comments.get(key);
	}
	
	
	public static Comment save(Comment comment) {
		InMemoryRepository.comments.put(comment.getId(), comment);
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
	
	//CUSTOMER TYPE METHODS------------------------------------------------------------------------------------
		public static Collection<CustomerType> findAllCustomerTypes(){
		return customerTypes.values();
	}
	
	public static Map<String, CustomerType> setCustomerType(Map<String, CustomerType> customerTypes) {
		InMemoryRepository.customerTypes = customerTypes;
		return customerTypes;
	}
	
	public static Map<String, CustomerType> saveCustomerTypes(){
		System.out.println("[LOG] CustomerType saving");
		try {
			Gson gson = JsonToFileAdapter.customerTypeSerializationToFile();

			FileWriter writer = new FileWriter("data/customerTypes.json");
			gson.toJson(InMemoryRepository.customerTypes, writer);
			writer.flush();
			writer.close();
			return customerTypes;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customerTypes;
	}
	
	public static CustomerType findOneCustomerType(String key) {
		return customerTypes.get(key);
	}
	
	
	public static CustomerType save(CustomerType customerType) {
		InMemoryRepository.customerTypes.put(customerType.getName(), customerType);
		return customerType;
	}
	
	public static CustomerType deleteCustomerType(String key) {
		CustomerType deleted = InMemoryRepository.customerTypes.get(key);
		if(deleted != null) {
			deleted.setDeleted(true);
			//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
		}
		return deleted;
	}
	
	//MANIFESTATION TYPE METHODS------------------------------------------------------------------------------------
	public static Collection<ManifestationType> findAllManifestationTypes(){
	return InMemoryRepository.manifestationTypes.values();
}

public static Map<String, ManifestationType> setManifestationType(Map<String, ManifestationType> manifestationTypes) {
	InMemoryRepository.manifestationTypes = manifestationTypes;
	return manifestationTypes;
}

public static Map<String, ManifestationType> saveManifestationTypes(){
	System.out.println("[LOG] ManifestationType saving");
	try {
		Gson gson = JsonToFileAdapter.manifestationTypeSerializationToFile();

		FileWriter writer = new FileWriter("data/manifestationTypes.json");
		gson.toJson(InMemoryRepository.manifestationTypes, writer);
		writer.flush();
		writer.close();
		return InMemoryRepository.manifestationTypes;
	} catch (JsonIOException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return InMemoryRepository.manifestationTypes;
}

public static ManifestationType findOneManifestationType(String key) {
	return InMemoryRepository.manifestationTypes.get(key);
}


public static ManifestationType save(ManifestationType manifestationType) {
	InMemoryRepository.manifestationTypes.put(manifestationType.getName(), manifestationType);
	return manifestationType;
}

public static ManifestationType deleteManifestationType(String key) {
	ManifestationType deleted = InMemoryRepository.manifestationTypes.get(key);
	if(deleted != null) {
		deleted.setDeleted(true);
		//TODO Consider deleting related objects like Location, Address, Tickets etc Cascade deletion
	}
	return deleted;
}
}
