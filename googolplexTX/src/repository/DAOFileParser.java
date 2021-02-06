package repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import model.Comment;
import model.Customer;
import model.CustomerType;
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
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import support.JsonAdapter;

public class DAOFileParser {

	private UserDAO userDAO;
	private ManifestationDAO manifestationDAO;
	private TicketDAO ticketDAO;
	private CommentDAO commentDAO;
	private CustomerTypeDAO customerTypeDAO;
	private ManifestationTypeDAO manifestationTypeDAO;

	private Map<String, Class<?>> userCastMap = new TreeMap<String, Class<?>>();

	public DAOFileParser(UserDAO userDAO, ManifestationDAO manifestationDAO, TicketDAO ticketDAO, CommentDAO commentDAO,
			CustomerTypeDAO customerTypeDAO, ManifestationTypeDAO manifestationTypeDAO) {
		this();
		this.userDAO = userDAO;
		this.manifestationDAO = manifestationDAO;
		this.ticketDAO = ticketDAO;
		this.commentDAO = commentDAO;
		this.customerTypeDAO = customerTypeDAO;
		this.manifestationTypeDAO = manifestationTypeDAO;
	}

	public DAOFileParser() {
		super();
		userCastMap.put(UserRole.ADMIN.name(), User.class);
		userCastMap.put(UserRole.SALESMAN.name(), Salesman.class);
		userCastMap.put(UserRole.CUSTOMER.name(), Customer.class);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ManifestationDAO getManifestationDAO() {
		return manifestationDAO;
	}

	public void setManifestationDAO(ManifestationDAO manifestationDAO) {
		this.manifestationDAO = manifestationDAO;
	}

	public TicketDAO getTicketDAO() {
		return ticketDAO;
	}

	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	public CustomerTypeDAO getCustomerTypeDAO() {
		return customerTypeDAO;
	}

	public void setCustomerTypeDAO(CustomerTypeDAO customerTypeDAO) {
		this.customerTypeDAO = customerTypeDAO;
	}

	public ManifestationTypeDAO getManifestationTypeDAO() {
		return manifestationTypeDAO;
	}

	public void setManifestationTypeDAO(ManifestationTypeDAO manifestationTypeDAO) {
		this.manifestationTypeDAO = manifestationTypeDAO;
	}

	public void loadData() {
		try {
			this.createInitalFiles();
			
			this.loadCustomerTypes();
			this.loadManifestationTypes();
			this.loadUsers();
			this.loadManifestations();
			this.loadTickets();
			this.loadComments();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createInitalFiles() throws IOException {
		String directoryName = "data/";
	    String custTypesName = "data/customerTypes.json";
	    String manTpeName = "data/manifestationTypes.json";
	    String usersName = "data/users.json";
	    String manifestationName = "data/manifestations.json";
	    String ticketsName = "data/tickets.json";
	    String commentsName = "data/comments.json";
	    
		File directory = new File(directoryName);
	    File customerTypesFile = new File(custTypesName);
	    File manTpeFile = new File(manTpeName);
	    File usersFile = new File(usersName);
	    File manifestationFile = new File(manifestationName);
	    File ticketsFile = new File(ticketsName);
	    File commentsFile = new File(commentsName);
		
		if (! directory.exists()){
	        directory.mkdir();
	    }
		
		if (! customerTypesFile.exists()) {
			customerTypesFile.createNewFile();
			for (CustomerType c : this.createInitCustomerTypes()) {
				customerTypeDAO.save(c);
			}
			
			customerTypeDAO.saveFile();
		}
		
		if (! manTpeFile.exists()) {
			manTpeFile.createNewFile();
			for (ManifestationType mt : this.createInitManifestationTypes()) {
				manifestationTypeDAO.save(mt);
			}
			
			manifestationTypeDAO.saveFile();
		}
		
		if (! usersFile.exists()) {
			usersFile.createNewFile();
			
			for (User admin : this.createInitAdmins()) {
				userDAO.save(admin);
			}			
			userDAO.saveFile();
		}
		
		if (! manifestationFile.exists()) {
			manifestationFile.createNewFile();
			FileWriter writer = new FileWriter(manifestationFile);
			writer.append("{}");
			writer.flush();
			writer.close();
		}
		
		if (! ticketsFile.exists()) {
			ticketsFile.createNewFile();
			FileWriter writer = new FileWriter(ticketsFile);
			writer.append("{}");
			writer.flush();
			writer.close();
		}
		
		if (! commentsFile.exists()) {
			commentsFile.createNewFile();
			FileWriter writer = new FileWriter(commentsFile);
			writer.append("{}");
			writer.flush();
			writer.close();
		}
		
		
	}
	
	private User createInitAdmin() {
		User admin1 = new User("admin", "admin", "adminFirst", "adminLast", Gender.MALE, LocalDate.now(),
				UserRole.ADMIN);
		return admin1;
	}
	
	private Collection<User> createInitAdmins() {
		ArrayList<User> admins = new ArrayList<User>(); 
		admins.add( new User("admin", "admin", "adminFirst", "adminLast", Gender.MALE, LocalDate.now(),
				UserRole.ADMIN) );
		admins.add( new User("admin", "admin", "adminFirst", "adminLast", Gender.MALE, LocalDate.now(),
				UserRole.ADMIN));
		return admins;
	}
	
	private Collection<CustomerType> createInitCustomerTypes() {
		ArrayList<CustomerType> customerTypes = new ArrayList<CustomerType>(); 
		customerTypes.add( new CustomerType("Default", 0.0, 0.0, false) );
		customerTypes.add( new CustomerType("Bronze", 3.0, 1000.0, false));
		customerTypes.add( new CustomerType("Silver", 5.0, 2000.0, false));
		customerTypes.add( new CustomerType("Gold", 8.0, 3000.0, false));
		return customerTypes;
	}
	
	private Collection<ManifestationType> createInitManifestationTypes() {
		ArrayList<ManifestationType> manifestationTypes = new ArrayList<ManifestationType>(); 
		manifestationTypes.add( new ManifestationType("Threatre") );
		manifestationTypes.add( new ManifestationType("Cinema") );
		manifestationTypes.add( new ManifestationType("Festival") );
		return manifestationTypes;
	}

	private void loadCustomerTypes() throws IOException {
		Gson gson = this.getCustomerTypeJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/customerTypes.json"));

		Type mapType = new TypeToken<Map<String, CustomerType>>() {
		}.getType();
		Map<String, CustomerType> customerTypes = gson.fromJson(reader, mapType);
		customerTypeDAO.setCustomerTypes(customerTypes);

		System.out.println("[DBG] Ucitani customer typovi");
	}

	private void loadManifestationTypes() throws IOException {
		Gson gson = this.getManifestationTypeJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/manifestationTypes.json"));

		Type mapType = new TypeToken<Map<String, ManifestationType>>() {
		}.getType();
		Map<String, ManifestationType> manifestationTypes = gson.fromJson(reader, mapType);
		manifestationTypeDAO.setManifestationTypes(manifestationTypes);

		System.out.println("[DBG] Ucitani manifestation typovi");
	}

	private void loadUsers() throws IOException {
		Gson gson = this.getUserJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/users.json"));

		Type mapType = new TypeToken<Map<String, User>>() {
		}.getType();
		Map<String, User> users = gson.fromJson(reader, mapType);
		userDAO.setUsers(users);

		System.out.println("[DBG] Ucitani useri");
	}

	private void loadManifestations() throws IOException {
		Gson gson = this.getManifestationsJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/manifestations.json"));

		Type mapType = new TypeToken<Map<String, Manifestation>>() {
		}.getType();
		Map<String, Manifestation> manifestations = gson.fromJson(reader, mapType);
		manifestationDAO.setManifestations(manifestations);
	}

	private void loadTickets() throws IOException {
		Gson gson = this.getTicketsJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/tickets.json"));

		Type mapType = new TypeToken<Map<String, Ticket>>() {
		}.getType();
		Map<String, Ticket> tickets = gson.fromJson(reader, mapType);
		ticketDAO.setTickets(tickets);
	}

	private void loadComments() throws IOException {
		Gson gson = this.getCommentsJsonBuilder();

		Reader reader = Files.newBufferedReader(Paths.get("data/comments.json"));

		Type mapType = new TypeToken<Map<String, Comment>>() {
		}.getType();
		Map<String, Comment> comments = gson.fromJson(reader, mapType);
		commentDAO.setComments(comments);

		System.out.println("[DBG] Ucitani komentari");
	}

	//////////////////////////////
	// GSON BUILDERS //
	//////////////////////////////
	private Gson getUserJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Type usersType = new TypeToken<Map<String, User>>() {
		}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {
		}.getType();
		Type manifestationsType = new TypeToken<Collection<Manifestation>>() {
		}.getType();

		gsonBuilder.registerTypeAdapter(usersType, adapterUserMapFromJson);
		gsonBuilder.registerTypeAdapter(User.class, adapterUserFromJson);
		gsonBuilder.registerTypeAdapter(Salesman.class, adapterSalesmanFromJson);
		gsonBuilder.registerTypeAdapter(Customer.class, adapterCustomerFromJson);
		gsonBuilder.registerTypeAdapter(LocalDate.class, JsonAdapter.adapterLocalDateFromJson);
		gsonBuilder.registerTypeAdapter(LocalTime.class, JsonAdapter.adapterLocalTimeFromJson);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, JsonAdapter.adapterLocalDateTimeFromJson);

		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationIdFromJson);
		gsonBuilder.registerTypeAdapter(Ticket.class, adapterTicketIdFromJson);
		gsonBuilder.registerTypeAdapter(CustomerType.class, adapterCustomerTypeIdFromJson);
		gsonBuilder.registerTypeAdapter(Comment.class, adapterCommentIdFromJson);
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentIdCollectionFromJson);
		gsonBuilder.registerTypeAdapter(manifestationsType, adapterManifestationIdCollectionFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private Gson getManifestationsJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Type ticketsType = new TypeToken<Collection<Ticket>>() {
		}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {
		}.getType();

		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationFromJson);
		gsonBuilder.registerTypeAdapter(LocalDate.class, JsonAdapter.adapterLocalDateFromJson);
		gsonBuilder.registerTypeAdapter(LocalTime.class, JsonAdapter.adapterLocalTimeFromJson);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, JsonAdapter.adapterLocalDateTimeFromJson);

		gsonBuilder.registerTypeAdapter(ManifestationType.class, adapterManifestationTypeIdFromJson);
		gsonBuilder.registerTypeAdapter(Salesman.class, adapterSalesmanIdFromJson);
		gsonBuilder.registerTypeAdapter(Comment.class, adapterCommentIdFromJson);
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentIdCollectionFromJson);
		gsonBuilder.registerTypeAdapter(Ticket.class, adapterTicketIdFromJson);
		gsonBuilder.registerTypeAdapter(ticketsType, adapterTicketIdCollectionFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private Gson getTicketsJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(Ticket.class, adapterTicketFromJson);
		gsonBuilder.registerTypeAdapter(LocalDate.class, JsonAdapter.adapterLocalDateFromJson);
		gsonBuilder.registerTypeAdapter(LocalTime.class, JsonAdapter.adapterLocalTimeFromJson);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, JsonAdapter.adapterLocalDateTimeFromJson);
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationIdFromJson);
		gsonBuilder.registerTypeAdapter(Customer.class, adapterCustomerIdFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private Gson getCommentsJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		// Type manifestationsType = new TypeToken<Map<String, Manifestation>>()
		// {}.getType();

		// gsonBuilder.registerTypeAdapter(Salesman.class, adapterUserToUsername);
		gsonBuilder.registerTypeAdapter(Comment.class, adapterCommentFromJson);
		gsonBuilder.registerTypeAdapter(Customer.class, adapterCustomerIdFromJson);
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationIdFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private Gson getCustomerTypeJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(Comment.class, adapterCustomerTypeFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private Gson getManifestationTypeJsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(Comment.class, adapterManifestationTypeFromJson);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	//////////////////////////
	// ADAPTERS //
	//////////////////////////

	/**
	 * Adapter for Admin (Base User) Class Consumes basic User json
	 */
	private final JsonDeserializer<User> adapterUserFromJson = new JsonDeserializer<User>() {

		@Override
		public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
			LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
			UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);

			String id = jsonObject.get("username").getAsString();
			User user = userDAO.findOne(id);

			if (user == null) {
				return new User(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString(),
						jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), gender,
						birthDate, userRole, jsonObject.get("blocked").getAsBoolean(),
						jsonObject.get("deleted").getAsBoolean());
			} else {
				user.setPassword(jsonObject.get("password").getAsString());
				user.setFirstName(jsonObject.get("firstName").getAsString());
				user.setLastName(jsonObject.get("lastName").getAsString());
				user.setGender(gender);
				user.setBirthDate(birthDate);
				user.setUserRole(userRole);
				user.setBlocked(jsonObject.get("blocked").getAsBoolean());
				user.setDeleted(jsonObject.get("deleted").getAsBoolean());
				return user;
			}

		}

	};

	/**
	 * Get User by id Create new bare class and save or get from repository if
	 * exists with that id Consumes jsonPrimitive containing id
	 */
	@SuppressWarnings("unused")
	private final JsonDeserializer<User> adapterUserIdFromJson = new JsonDeserializer<User>() {

		@Override
		public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			User user = userDAO.findOne(id);

			if (user == null) {
				user = new User();
				user.setUsername(id);
				userDAO.save(user);
			}

			return user;

		}

	};

	/**
	 * Adapter for Salesman Class Consumes basic Salesman json
	 */
	private final JsonDeserializer<Salesman> adapterSalesmanFromJson = new JsonDeserializer<Salesman>() {

		@Override
		public Salesman deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
			LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
			UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);

			Type manifestationsType = new TypeToken<Collection<Manifestation>>() {
			}.getType();
			Collection<Manifestation> manifestations = context.deserialize(jsonObject.get("manifestations"),
					manifestationsType);

			String id = jsonObject.get("username").getAsString();
			Salesman salesman = (Salesman) userDAO.findOne(id);

			if (salesman == null) {
				return new Salesman(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString(),
						jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), gender,
						birthDate, userRole, jsonObject.get("blocked").getAsBoolean(),
						jsonObject.get("deleted").getAsBoolean(), manifestations);
			} else {
				salesman.setPassword(jsonObject.get("password").getAsString());
				salesman.setFirstName(jsonObject.get("firstName").getAsString());
				salesman.setLastName(jsonObject.get("lastName").getAsString());
				salesman.setGender(gender);
				salesman.setBirthDate(birthDate);
				salesman.setUserRole(userRole);
				salesman.setBlocked(jsonObject.get("blocked").getAsBoolean());
				salesman.setDeleted(jsonObject.get("deleted").getAsBoolean());
				salesman.setManifestation(manifestations);
				return salesman;
			}

		}

	};
	private final JsonDeserializer<Salesman> adapterSalesmanIdFromJson = new JsonDeserializer<Salesman>() {

		@Override
		public Salesman deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			Salesman salesman = (Salesman) userDAO.findOne(id);

			if (salesman == null) {
				salesman = new Salesman();
				salesman.setUsername(id);
				userDAO.save(salesman);
			}

			return salesman;

		}

	};

	/**
	 * Adapter for Customer Class Consumes basic Customer json
	 */
	private final JsonDeserializer<Customer> adapterCustomerFromJson = new JsonDeserializer<Customer>() {

		@Override
		public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
			LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
			UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);

			Type commentsType = new TypeToken<Collection<Comment>>() {
			}.getType();
			Type ticketsType = new TypeToken<Collection<Ticket>>() {
			}.getType();
			ArrayList<Comment> comments = context.deserialize(jsonObject.get("comments"), commentsType);
			ArrayList<Ticket> tickets = context.deserialize(jsonObject.get("tickets"), ticketsType);
			CustomerType customerType = context.deserialize(jsonObject.get("customerType"), CustomerType.class);

			String id = jsonObject.get("username").getAsString();
			Customer customer = (Customer) userDAO.findOne(id);

			if (customer == null) {
				return new Customer(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString(),
						jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), gender,
						birthDate, userRole, jsonObject.get("blocked").getAsBoolean(),
						jsonObject.get("deleted").getAsBoolean(), jsonObject.get("points").getAsDouble(), customerType, // Customer
																														// type
						comments, // Comments
						tickets // Tickets
				);
			} else {
				customer.setPassword(jsonObject.get("password").getAsString());
				customer.setFirstName(jsonObject.get("firstName").getAsString());
				customer.setLastName(jsonObject.get("lastName").getAsString());
				customer.setGender(gender);
				customer.setBirthDate(birthDate);
				customer.setUserRole(userRole);
				customer.setBlocked(jsonObject.get("blocked").getAsBoolean());
				customer.setDeleted(jsonObject.get("deleted").getAsBoolean());
				customer.setPoints(jsonObject.get("point").getAsDouble());
				customer.setCustomerType(customerType);
				customer.setComments(comments);
				customer.setTickets(tickets);
				return customer;
			}

		}

	};

	/**
	 * Get Customer by id Create new bare class and save or get from repository if
	 * exists with that id Consumes jsonPrimitive containing id
	 */
	private final JsonDeserializer<Customer> adapterCustomerIdFromJson = new JsonDeserializer<Customer>() {

		@Override
		public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			Customer customer = (Customer) userDAO.findOne(id);

			if (customer == null) {
				customer = new Customer();
				customer.setUsername(id);
				userDAO.save(customer);
			}

			return customer;

		}

	};

	/**
	 * Adapter for all User Classes Map into User, Salesman and Customer based on
	 * userRole attribute Consumes Users Json Array
	 */
	private final JsonDeserializer<Map<String, User>> adapterUserMapFromJson = new JsonDeserializer<Map<String, User>>() {
		// TODO: Check is this neccesery
		@Override
		public Map<String, User> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			Map<String, User> usersMap = new HashMap<String, User>();
			JsonObject usersObject = json.getAsJsonObject();

			for (Map.Entry<String, JsonElement> user : usersObject.entrySet()) {

				JsonObject jsonUser = user.getValue().getAsJsonObject();
				String type = jsonUser.get("userRole").getAsString();
				String username = user.getKey();
				Class<?> c = userCastMap.get(type);
				if (c == null)
					throw new RuntimeException("Unknow class: " + type);

				usersMap.put(username, context.deserialize(jsonUser, c));
			}

			return usersMap;

		}

	};

	// Manifestation
	/**
	 * Manifestation Adapter Generate Manifestation from basic manifestation Json
	 * Object
	 */
	private final JsonDeserializer<Manifestation> adapterManifestationFromJson = new JsonDeserializer<Manifestation>() {

		@Override
		public Manifestation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			LocalDateTime dateOfOccurence = context.deserialize(jsonObject.get("dateOfOccurence"), LocalDateTime.class);
			Location location = context.deserialize(jsonObject.get("location"), Location.class);
			ManifestationStatus manifestationStatus = context.deserialize(jsonObject.get("status"),
					ManifestationStatus.class);

			ManifestationType manifestationType = context.deserialize(jsonObject.get("manifestationType"),
					ManifestationType.class);
			Salesman salesman = context.deserialize(jsonObject.get("salesman"), Salesman.class);

			Type commentsType = new TypeToken<Collection<Comment>>() {
			}.getType();
			Type ticketsType = new TypeToken<Collection<Ticket>>() {
			}.getType();
			ArrayList<Comment> comments = context.deserialize(jsonObject.get("comments"), commentsType);
			ArrayList<Ticket> tickets = context.deserialize(jsonObject.get("tickets"), ticketsType);

			// Generate new Manifestation or supplement pre-created obj (used in links)
			String id = jsonObject.get("id").getAsString();
			Manifestation manifestation = manifestationDAO.findOne(id);
			if (manifestation == null) {
				return new Manifestation(jsonObject.get("id").getAsString(), jsonObject.get("name").getAsString(),
						jsonObject.get("availableSeats").getAsInt(), dateOfOccurence,
						jsonObject.get("regularPrice").getAsDouble(), manifestationStatus,
						jsonObject.get("poster").getAsString(), jsonObject.get("deleted").getAsBoolean(),
						manifestationType, // Manifesattion Type
						salesman, // Salesman
						location);
			} else {
				manifestation.setName(jsonObject.get("name").getAsString());
				manifestation.setAvailableSeats(jsonObject.get("availableSeats").getAsInt());
				manifestation.setDateOfOccurence(dateOfOccurence);
				manifestation.setRegularPrice(jsonObject.get("regularPrice").getAsDouble());
				manifestation.setStatus(manifestationStatus);
				manifestation.setPoster(jsonObject.get("poster").getAsString());
				manifestation.setDeleted(jsonObject.get("deleted").getAsBoolean());
				manifestation.setManifestationType(manifestationType);
				manifestation.setSalesman(salesman);
				manifestation.setLocation(location);
				manifestation.setComments(comments);
				manifestation.setTickets(tickets);
				return manifestation;
			}

		}

	};

	/**
	 * Get Manifestation by id Create new bare class and save or get from repository
	 * if exists with that id Consumes jsonPrimitive containing id
	 */
	private final JsonDeserializer<Manifestation> adapterManifestationIdFromJson = new JsonDeserializer<Manifestation>() {

		@Override
		public Manifestation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			Manifestation manifestation = manifestationDAO.findOne(id);

			if (manifestation == null) {
				manifestation = new Manifestation();
				manifestation.setId(id);
				manifestationDAO.save(manifestation);
			}

			return manifestation;

		}

	};

	/**
	 * Get Collections of Manifestation by id array Create new bare classes and save
	 * or get from repository if exists with that id Consumes jsonArray containing
	 * ids
	 */
	private final JsonDeserializer<Collection<Manifestation>> adapterManifestationIdCollectionFromJson = new JsonDeserializer<Collection<Manifestation>>() {

		@Override
		public Collection<Manifestation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonArray ids = json.getAsJsonArray();
			ArrayList<Manifestation> manifestations = new ArrayList<Manifestation>();

			for (JsonElement id : ids) {
				Manifestation manifestation = manifestationDAO.findOne(id.getAsString());
				if (manifestation == null) {
					manifestation = new Manifestation();
					manifestation.setId(id.getAsString());
					manifestationDAO.save(manifestation);
				}
				manifestations.add(manifestation);

			}

			return manifestations;

		}

	};

	// Ticket
	/**
	 * Ticket Adapter Generate Ticket from basic tickets Json Object
	 */
	private final JsonDeserializer<Ticket> adapterTicketFromJson = new JsonDeserializer<Ticket>() {

		@Override
		public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			LocalDateTime dateOfOccurence = context.deserialize(jsonObject.get("dateOfManifestation"),
					LocalDateTime.class);
			LocalDateTime cancelationDate = context.deserialize(jsonObject.get("cancelationDate"), LocalDateTime.class);
			TicketType ticketType = context.deserialize(jsonObject.get("ticketType"), TicketType.class);
			TicketStatus ticketStatus = context.deserialize(jsonObject.get("ticketStatus"), TicketStatus.class);
			Manifestation manifestation = context.deserialize(jsonObject.get("manifestation"), Manifestation.class);
			Customer customer = context.deserialize(jsonObject.get("customer"), Customer.class);

			String id = jsonObject.get("id").getAsString();
			Ticket ticket = ticketDAO.findOne(id);
			if (ticket == null) {
				return new Ticket(jsonObject.get("id").getAsString(), dateOfOccurence,
						jsonObject.get("price").getAsDouble(), ticketType, ticketStatus, cancelationDate,
						jsonObject.get("deleted").getAsBoolean(), customer, manifestation);
			} else {
				ticket.setDateOfManifestation(dateOfOccurence);
				ticket.setPrice(jsonObject.get("price").getAsDouble());
				ticket.setTicketType(ticketType);
				ticket.setTicketStatus(ticketStatus);
				ticket.setCancelationDate(cancelationDate);
				ticket.setDeleted(jsonObject.get("deleted").getAsBoolean());
				ticket.setCustomer(customer);
				ticket.setManifestation(manifestation);
				return ticket;
			}
		}

	};

	/**
	 * Get Ticket by id Create new bare class and save or get from repository if
	 * exists with that id Consumes jsonPrimitive containing id
	 */
	private final JsonDeserializer<Ticket> adapterTicketIdFromJson = new JsonDeserializer<Ticket>() {

		@Override
		public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			Ticket ticket = ticketDAO.findOne(id);
			if (ticket == null) {
				ticket = new Ticket();
				ticket.setId(id);
				ticketDAO.save(ticket);
			}

			return ticket;

		}

	};

	/**
	 * Get Collections of Tickets by id array Create new bare classes and save or
	 * get from repository if exists with that id Consumes jsonArray containing ids
	 */
	private final JsonDeserializer<Collection<Ticket>> adapterTicketIdCollectionFromJson = new JsonDeserializer<Collection<Ticket>>() {

		@Override
		public Collection<Ticket> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonArray ids = json.getAsJsonArray();
			ArrayList<Ticket> tickets = new ArrayList<Ticket>();

			for (JsonElement id : ids) {
				Ticket ticket = ticketDAO.findOne(id.getAsString());
				if (ticket == null) {
					ticket = new Ticket();
					ticket.setId(id.getAsString());
					ticketDAO.save(ticket);
				}
				tickets.add(ticket);

			}

			return tickets;

		}

	};

	// Comment
	/**
	 * Comment Adapter Generate Comment from basic comment Json Object
	 */
	private final JsonDeserializer<Comment> adapterCommentFromJson = new JsonDeserializer<Comment>() {

		@Override
		public Comment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			CommentStatus commentStatus = context.deserialize(jsonObject.get("approved"), CommentStatus.class);

			String id = jsonObject.get("id").getAsString();
			Comment comment = commentDAO.findOne(id);

			Customer customer = context.deserialize(jsonObject.get("customer"), Customer.class);
			Manifestation manifestation = context.deserialize(jsonObject.get("manifestation"), Manifestation.class);

			if (comment == null) {
				return new Comment(jsonObject.get("id").getAsString(), jsonObject.get("text").getAsString(),
						jsonObject.get("rating").getAsInt(), commentStatus, jsonObject.get("deleted").getAsBoolean(),
						manifestation, customer);
			} else {
				comment.setText(jsonObject.get("text").getAsString());
				comment.setRating(jsonObject.get("rating").getAsInt());
				comment.setApproved(commentStatus);
				comment.setDeleted(jsonObject.get("deleted").getAsBoolean());
				comment.setCustomer(customer);
				comment.setManifestation(manifestation);
				return comment;
			}
		}

	};

	/**
	 * Get Comment by id Create new bare class and save or get from repository if
	 * exists with that id Consumes jsonPrimitive containing id
	 */
	private final JsonDeserializer<Comment> adapterCommentIdFromJson = new JsonDeserializer<Comment>() {

		@Override
		public Comment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			Comment comment = commentDAO.findOne(id);
			if (comment == null) {
				comment = new Comment();
				comment.setId(id);
				commentDAO.save(comment);
			}

			return comment;

		}

	};

	/**
	 * Get Collections of Comments by id array Create new bare classes and save or
	 * get from repository if exists with that id Consumes jsonArray containing ids
	 */
	private final JsonDeserializer<Collection<Comment>> adapterCommentIdCollectionFromJson = new JsonDeserializer<Collection<Comment>>() {

		@Override
		public Collection<Comment> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonArray ids = json.getAsJsonArray();
			ArrayList<Comment> comments = new ArrayList<Comment>();

			for (JsonElement id : ids) {
				Comment comment = commentDAO.findOne(id.getAsString());
				if (comment == null) {
					comment = new Comment();
					comment.setId(id.getAsString());
					commentDAO.save(comment);
				}
				comments.add(comment);

			}

			return comments;

		}

	};

	/**
	 * ManifestationType Adapter Generate ManifestationType from basic Customer Type
	 * Json Object
	 */
	private final JsonDeserializer<ManifestationType> adapterManifestationTypeFromJson = new JsonDeserializer<ManifestationType>() {

		@Override
		public ManifestationType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			JsonObject jsonObject = json.getAsJsonObject();

			String id = jsonObject.get("id").getAsString();
			ManifestationType manifestationType = manifestationTypeDAO.findOne(id);

			if (manifestationType == null) {
				return new ManifestationType(jsonObject.get("name").getAsString(),
						jsonObject.get("deleted").getAsBoolean());
			} else {
				manifestationType.setDeleted(jsonObject.get("deleted").getAsBoolean());
				return manifestationType;
			}

		}

	}; // Manifesattion Type id
	/**
	 * ManifestationType Adapter Generate ManifestationType from id or find in DAO
	 */
	private final JsonDeserializer<ManifestationType> adapterManifestationTypeIdFromJson = new JsonDeserializer<ManifestationType>() {

		@Override
		public ManifestationType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			ManifestationType manifestationType = manifestationTypeDAO.findOne(id);
			if (manifestationType == null) {
				manifestationType = new ManifestationType();
				manifestationType.setName(id);
				manifestationTypeDAO.save(manifestationType);
			}

			return manifestationType;

		}

	};

	/**
	 * CustomerType Adapter Generate CustomerType from basic Customer Type Json
	 * Object
	 */
	private final JsonDeserializer<CustomerType> adapterCustomerTypeFromJson = new JsonDeserializer<CustomerType>() {

		@Override
		public CustomerType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			String id = jsonObject.get("id").getAsString();
			CustomerType customerType = customerTypeDAO.findOne(id);

			if (customerType == null) {
				return new CustomerType(jsonObject.get("name").getAsString(), jsonObject.get("discount").getAsDouble(),
						jsonObject.get("requiredPoints").getAsDouble(), jsonObject.get("deleted").getAsBoolean());
			} else {
				customerType.setDiscount(jsonObject.get("discount").getAsDouble());
				customerType.setRequiredPoints(jsonObject.get("requiredPoints").getAsDouble());
				customerType.setDeleted(jsonObject.get("deleted").getAsBoolean());
				return customerType;
			}

		}

	};
	// Customer Type id
	/**
	 * CustomerType Adapter Generate CustomerType from id or find in DAO
	 */
	private final JsonDeserializer<CustomerType> adapterCustomerTypeIdFromJson = new JsonDeserializer<CustomerType>() {

		@Override
		public CustomerType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			String id = json.getAsString();
			CustomerType customerType = customerTypeDAO.findOne(id);
			if (customerType == null) {
				customerType = new CustomerType();
				customerType.setName(id);
				customerTypeDAO.save(customerType);
			}

			return customerType;

		}

	};

}
