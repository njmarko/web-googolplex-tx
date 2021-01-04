package support;

import java.lang.reflect.Type;
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
import com.google.gson.JsonPrimitive;
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
import repository.InMemoryRepository;

public class FileToJsonAdapter {
	
	private static Map<String, Class<?>> userCastMap = new TreeMap<String, Class<?>>();

	static {
		userCastMap.put(UserRole.ADMIN.name(), User.class);
		userCastMap.put(UserRole.SALESMAN.name(), Salesman.class);
		userCastMap.put(UserRole.CUSTOMER.name(), Customer.class);
	}

	
	/**
	 * Adapter for Admin (Base User) Class
	 * Consumes basic User json
	 */
	public static final JsonDeserializer<User> adapterUser = new JsonDeserializer<User>() {

		@Override
		public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
	        LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
	        UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);
	        
	        String id = jsonObject.get("username").getAsString();
	        User user = InMemoryRepository.findOneUser(id);

	        if (user == null) {
				return new User(
						jsonObject.get("username").getAsString(), 
						jsonObject.get("password").getAsString(), 
						jsonObject.get("firstName").getAsString(), 
						jsonObject.get("lastName").getAsString(), 
						gender, 
						birthDate, 
						userRole, 
						jsonObject.get("blocked").getAsBoolean(), 
						jsonObject.get("deleted").getAsBoolean()
					);
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
	 * Get User by id
	 * Create new bare class and save or get from repository if exists with that id
	 * Consumes jsonPrimitive containing id
	 */
	public static final JsonDeserializer<User> adapterUserId = new JsonDeserializer<User>() {

		@Override
		public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        User user = InMemoryRepository.findOneUser(id);

	        if (user == null) {
	        	user = new User();
	        	user.setUsername(id);
	        	InMemoryRepository.save(user);
	        }
	        
			return user;
			
		}

	};
	
	
	/**
	 * Adapter for Salesman Class
	 * Consumes basic Salesman json
	 */
	public static final JsonDeserializer<Salesman> adapterBasicSalesman = new JsonDeserializer<Salesman>() {

		@Override
		public Salesman deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
	        LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
	        UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);

			Type manifestationsType = new TypeToken<Collection<Manifestation>>() {}.getType();
	        Collection<Manifestation> manifestations = context.deserialize(jsonObject.get("manifestations"), manifestationsType);
	        
	        String id = jsonObject.get("username").getAsString();
	        Salesman salesman = (Salesman) InMemoryRepository.findOneUser(id);

	        if (salesman == null) {
				return new Salesman(
						jsonObject.get("username").getAsString(), 
						jsonObject.get("password").getAsString(), 
						jsonObject.get("firstName").getAsString(), 
						jsonObject.get("lastName").getAsString(), 
						gender, 
						birthDate, 
						userRole, 
						jsonObject.get("blocked").getAsBoolean(), 
						jsonObject.get("deleted").getAsBoolean(),
						manifestations
						);
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
	public static final JsonDeserializer<Salesman> adapterSalesmanId = new JsonDeserializer<Salesman>() {

		@Override
		public Salesman deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        Salesman salesman = (Salesman) InMemoryRepository.findOneUser(id);

	        if (salesman == null) {
	        	salesman = new Salesman();
	        	salesman.setUsername(id);
	        	InMemoryRepository.save(salesman);
	        }
	        
			return salesman;
			
		}

	};
	
	
	/**
	 * Adapter for Customer Class
	 * Consumes basic Customer json
	 */
	public static final JsonDeserializer<Customer> adapterBasicCustomer = new JsonDeserializer<Customer>() {

		@Override
		public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        Gender gender = context.deserialize(jsonObject.get("gender"), Gender.class);
	        LocalDate birthDate = context.deserialize(jsonObject.get("birthDate"), LocalDate.class);
	        UserRole userRole = context.deserialize(jsonObject.get("userRole"), UserRole.class);
	        
			Type commentsType = new TypeToken<Collection<Comment>>() {}.getType();
			Type ticketsType = new TypeToken<Collection<Ticket>>() {}.getType();
	        ArrayList<Comment> comments = context.deserialize(jsonObject.get("comments"), commentsType);
	        ArrayList<Ticket> tickets = context.deserialize(jsonObject.get("tickets"), ticketsType);
	        CustomerType customerType = context.deserialize(jsonObject.get("customerType"), CustomerType.class);

	        String id = jsonObject.get("username").getAsString();
	        Customer customer = (Customer) InMemoryRepository.findOneUser(id);

	        if (customer == null) {
			return new Customer(
					jsonObject.get("username").getAsString(), 
					jsonObject.get("password").getAsString(), 
					jsonObject.get("firstName").getAsString(), 
					jsonObject.get("lastName").getAsString(), 
					gender, 
					birthDate, 
					userRole, 
					jsonObject.get("blocked").getAsBoolean(), 
					jsonObject.get("deleted").getAsBoolean(),
					jsonObject.get("points").getAsDouble(),
					customerType,	// Customer type
					comments,	//Comments
					tickets		//Tickets
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
	 * Get Customer by id
	 * Create new bare class and save or get from repository if exists with that id
	 * Consumes jsonPrimitive containing id
	 */
	public static final JsonDeserializer<Customer> adapterCustomerId = new JsonDeserializer<Customer>() {

		@Override
		public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        Customer customer = (Customer) InMemoryRepository.findOneUser(id);

	        if (customer == null) {
	        	customer = new Customer();
	        	customer.setUsername(id);
	        	InMemoryRepository.save(customer);
	        }
	        
			return customer;
			
		}

	};
	
	
	/**
	 * Adapter for all User Classes
	 * Map into User, Salesman and Customer based on userRole attribute
	 * Consumes Users Json Array
	 */
	public static final JsonDeserializer<Map<String, User>> adapterUserMap = new JsonDeserializer<Map<String, User>>() {

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

	            usersMap.put(username ,context.deserialize(jsonUser, c));
	        }
	        
			return usersMap;		
			
		}

	};
	
	// Manifestation
	/**
	 * Manifestation Adapter
	 * Generate Manifestation from basic manifestation Json Object
	 */
	public static final JsonDeserializer<Manifestation> adapterBareManifestation = new JsonDeserializer<Manifestation>() {

		@Override
		public Manifestation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        LocalDateTime dateOfOccurence = context.deserialize(jsonObject.get("dateOfOccurence"), LocalDateTime.class);
	        Location location = context.deserialize(jsonObject.get("location"), Location.class);
	        ManifestationStatus manifestationStatus = context.deserialize(jsonObject.get("status"), ManifestationStatus.class);  
	        
	        ManifestationType manifestationType = context.deserialize(jsonObject.get("manifestationType"), ManifestationType.class);
	        Salesman salesman = context.deserialize(jsonObject.get("salesman"), Salesman.class);
	        
	        // Generate new Manifestation or supplement pre-created obj (used in links)
	        String id = jsonObject.get("id").getAsString();
	        Manifestation manifestation = InMemoryRepository.findOneManifestation(id);
	        if (manifestation == null) {
				return new Manifestation(
						jsonObject.get("id").getAsString(), 
						jsonObject.get("name").getAsString(), 
						jsonObject.get("availableSeats").getAsInt(), 
						dateOfOccurence, 
						jsonObject.get("regularPrice").getAsDouble(), 
						manifestationStatus, 
						jsonObject.get("poster").getAsString(), 
						jsonObject.get("deleted").getAsBoolean(), 
						manifestationType, 	// Manifesattion Type
						salesman, 	// Salesman 
						location);
	        } else {
	        	manifestation.setName(jsonObject.get("name").getAsString());
	        	manifestation.setAvailableSeats(jsonObject.get("availableSeats").getAsInt());
	        	manifestation.setDateOfOccurence(dateOfOccurence);
	        	manifestation.setRegularPrice(jsonObject.get("regularPrice").getAsDouble());
	        	manifestation.setPoster(jsonObject.get("poster").getAsString());
	        	manifestation.setDeleted(jsonObject.get("deleted").getAsBoolean());
	        	manifestation.setManifestationType(manifestationType);
	        	manifestation.setSalesman(salesman);
	        	manifestation.setLocation(location);
	        	return manifestation;
	        }
			
		}

	};
	
	/**
	 * Get Manifestation by id
	 * Create new bare class and save or get from repository if exists with that id
	 * Consumes jsonPrimitive containing id
	 */
	public static final JsonDeserializer<Manifestation> adapterManifestationId = new JsonDeserializer<Manifestation>() {

		@Override
		public Manifestation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        Manifestation manifestation = InMemoryRepository.findOneManifestation(id);

	        if (manifestation == null) {
	        	manifestation = new Manifestation();
	        	manifestation.setId(id);
	        	InMemoryRepository.save(manifestation);
	        }
	        
			return manifestation;
			
		}

	};
	
	/**
	 * Get Collections of Manifestation by id array
	 * Create new bare classes and save or get from repository if exists with that id
	 * Consumes jsonArray containing ids
	 */
	public static final JsonDeserializer<Collection<Manifestation>> adapterManifestationIdCollection = new JsonDeserializer<Collection<Manifestation>>() {

		@Override
		public Collection<Manifestation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonArray ids = json.getAsJsonArray();
	        ArrayList<Manifestation> manifestations = new ArrayList<Manifestation>();
	        
	        for (JsonElement id : ids) {
	        	Manifestation manifestation = InMemoryRepository.findOneManifestation(id.getAsString());
		        if (manifestation == null) {
		        	manifestation = new Manifestation();
		        	manifestation.setId(id.getAsString());
		        	InMemoryRepository.save(manifestation);
		        }
		        manifestations.add(manifestation);
		        
	        }
	        
			return manifestations;
			
		}

	};
	
	// Ticket
	/**
	 * Ticket Adapter
	 * Generate Ticket from basic tickets Json Object
	 */
	public static final JsonDeserializer<Ticket> adapterBareTicket = new JsonDeserializer<Ticket>() {

		@Override
		public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        LocalDateTime dateOfOccurence = context.deserialize(jsonObject.get("dateOfManifestation"), LocalDateTime.class);
	        LocalDateTime cancelationDate = context.deserialize(jsonObject.get("cancelationDate"), LocalDateTime.class);
	        TicketType ticketType = context.deserialize(jsonObject.get("ticketType"), TicketType.class);  
	        TicketStatus ticketStatus = context.deserialize(jsonObject.get("ticketStatus"), TicketStatus.class);  
	        Manifestation manifestation = context.deserialize(jsonObject.get("manifestation"), Manifestation.class);
	        Customer customer = context.deserialize(jsonObject.get("customer"), Customer.class);

	        
	        String id = jsonObject.get("id").getAsString();
	        Ticket ticket = InMemoryRepository.findOneTicket(id);
	        if (ticket == null) {
				return new Ticket(
						jsonObject.get("id").getAsString(), 
						dateOfOccurence, 
						jsonObject.get("price").getAsDouble(), 
						jsonObject.get("customerName").getAsString(), 
						ticketType, 
						ticketStatus, 
						cancelationDate, 
						jsonObject.get("deleted").getAsBoolean(),
						customer,
						manifestation);
	        } else {
	        	ticket.setDateOfManifestation(dateOfOccurence);
	        	ticket.setPrice(jsonObject.get("price").getAsDouble());
	        	ticket.setCustomerName(id);
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
	 * Get Ticket by id
	 * Create new bare class and save or get from repository if exists with that id
	 * Consumes jsonPrimitive containing id
	 */
	public static final JsonDeserializer<Ticket> adapterTicketId = new JsonDeserializer<Ticket>() {

		@Override
		public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        Ticket ticket = InMemoryRepository.findOneTicket(id);
	        if (ticket == null) {
	        	ticket = new Ticket();
	        	ticket.setId(id);
	        	InMemoryRepository.save(ticket);
	        }
	        
			return ticket;
			
		}

	};
	
	/**
	 * Get Collections of Tickets by id array
	 * Create new bare classes and save or get from repository if exists with that id
	 * Consumes jsonArray containing ids
	 */
	public static final JsonDeserializer<Collection<Ticket>> adapterTicketIdCollection = new JsonDeserializer<Collection<Ticket>>() {

		@Override
		public Collection<Ticket> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonArray ids = json.getAsJsonArray();
	        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	        
	        for (JsonElement id : ids) {
	        	Ticket ticket = InMemoryRepository.findOneTicket(id.getAsString());
		        if (ticket == null) {
		        	ticket = new Ticket();
		        	ticket.setId(id.getAsString());
		        	InMemoryRepository.save(ticket);
		        }
		        tickets.add(ticket);
		        
	        }
	        
			return tickets;
			
		}

	};
	
	
	// Comment
	/**
	 * Comment Adapter
	 * Generate Comment from basic comment Json Object
	 */
	public static final JsonDeserializer<Comment> commentBareTicket = new JsonDeserializer<Comment>() {

		@Override
		public Comment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
	        
	        CommentStatus commentStatus = context.deserialize(jsonObject.get("approved"), CommentStatus.class);  

	        String id = jsonObject.get("id").getAsString();
	        Comment comment = InMemoryRepository.findOneComment(id);

	        Customer customer = context.deserialize(jsonObject.get("customer"), Customer.class);
	        Manifestation manifestation = context.deserialize(jsonObject.get("manifestation"), Manifestation.class);
	        
	        if (comment == null) {
				return new Comment(
						jsonObject.get("id").getAsString(), 
						jsonObject.get("text").getAsString(), 
						jsonObject.get("rating").getAsInt(), 
						commentStatus, 
						jsonObject.get("deleted").getAsBoolean(),
						manifestation,
						customer);
	        } else {
	        	comment.setText(id);
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
	 * Get Comment by id
	 * Create new bare class and save or get from repository if exists with that id
	 * Consumes jsonPrimitive containing id
	 */
	public static final JsonDeserializer<Comment> adapterCommentId = new JsonDeserializer<Comment>() {

		@Override
		public Comment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        Comment comment = InMemoryRepository.findOneComment(id);
	        if (comment == null) {
	        	comment = new Comment();
	        	comment.setId(id);
	        	InMemoryRepository.save(comment);
	        }
	        
			return comment;
			
		}

	};
	
	/**
	 * Get Collections of Comments by id array
	 * Create new bare classes and save or get from repository if exists with that id
	 * Consumes jsonArray containing ids
	 */
	public static final JsonDeserializer<Collection<Comment>> adapterCommentIdCollection = new JsonDeserializer<Collection<Comment>>() {

		@Override
		public Collection<Comment> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonArray ids = json.getAsJsonArray();
	        ArrayList<Comment> comments = new ArrayList<Comment>();
	        
	        for (JsonElement id : ids) {
	        	Comment comment = InMemoryRepository.findOneComment(id.getAsString());
		        if (comment == null) {
		        	comment = new Comment();
		        	comment.setId(id.getAsString());
		        	InMemoryRepository.save(comment);
		        }
		        comments.add(comment);
		        
	        }
	        
			return comments;
			
		}

	};
	
	
	// Manifesattion Type id
	/**
	 * ManifestationType Adapter
	 * Generate ManifestationType from basic Customer Type Json Object
	 */
	public static final JsonDeserializer<ManifestationType> adapterManifestationTypeId = new JsonDeserializer<ManifestationType>() {

		@Override
		public ManifestationType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        ManifestationType manifestationType = InMemoryRepository.findOneManifestationType(id);
	        if (manifestationType == null) {
	        	manifestationType = new ManifestationType();
	        	manifestationType.setName(id);
	        	InMemoryRepository.save(manifestationType);
	        }
	        
			return manifestationType;
			
		}

	};	
	
	// Customer Type id
	/**
	 * CustomerType Adapter
	 * Generate CustomerType from basic Customer Type Json Object
	 */
	public static final JsonDeserializer<CustomerType> adapterCustomerTypeId = new JsonDeserializer<CustomerType>() {

		@Override
		public CustomerType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        String id = json.getAsString();
	        CustomerType customerType = InMemoryRepository.findOneCustomerType(id);
	        if (customerType == null) {
	        	customerType = new CustomerType();
	        	customerType.setName(id);
	        	InMemoryRepository.save(customerType);
	        }
	        
			return customerType;
			
		}

	};
	
	// Date Time
	/**
	 * LocalTime Adapter
	 * Used to avoid warnings
	 */
	public static final JsonDeserializer<LocalDate> adapterLocalDate = new JsonDeserializer<LocalDate>() {

		@Override
		public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
				
	        
	        return LocalDate.of(
	                jsonObject.get("year").getAsInt(),
	                jsonObject.get("month").getAsInt(),
	                jsonObject.get("day").getAsInt()
	                );   
	        
		}

	};
	
	/**
	 * LocalTime Adapter
	 * Used to avoid warnings
	 */
	public static final JsonDeserializer<LocalTime> adapterLocalTime = new JsonDeserializer<LocalTime>() {

		@Override
		public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
				
	        
	        return LocalTime.of(
	                jsonObject.get("hour").getAsInt(),
	                jsonObject.get("minute").getAsInt(),
	                jsonObject.get("second").getAsInt()
	                );   
	        
		}

	};
	
	/**
	 * LocalDateTime Adapter
	 * Used to avoid warnings
	 */
	public static final JsonDeserializer<LocalDateTime> adapterLocalDateTime = new JsonDeserializer<LocalDateTime>() {

		@Override
		public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
	        JsonObject jsonObject = json.getAsJsonObject();
				
	        LocalDate date = context.deserialize(jsonObject.get("date"), LocalDate.class);
	        LocalTime time = context.deserialize(jsonObject.get("time"), LocalTime.class);

	        
	        return LocalDateTime.of(date,time);   
	        
		}

	};
	
	
	public static Gson usersSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Type usersType = new TypeToken<Map<String, User>>() {}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {}.getType();
		Type manifestationsType = new TypeToken<Collection<Manifestation>>() {}.getType();

		gsonBuilder.registerTypeAdapter(usersType, adapterUserMap);
		gsonBuilder.registerTypeAdapter(User.class, adapterUser);
		gsonBuilder.registerTypeAdapter(Salesman.class, adapterBasicSalesman);
		gsonBuilder.registerTypeAdapter(Customer.class, adapterBasicCustomer);
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate);
		gsonBuilder.registerTypeAdapter(LocalTime.class, adapterLocalTime);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, adapterLocalDateTime);
		
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationId);
		gsonBuilder.registerTypeAdapter(Ticket.class, adapterTicketId);
		gsonBuilder.registerTypeAdapter(CustomerType.class, adapterCustomerTypeId);
		gsonBuilder.registerTypeAdapter(Comment.class, adapterCommentId);
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentIdCollection);
		gsonBuilder.registerTypeAdapter(manifestationsType, adapterManifestationIdCollection);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	
	public static Gson manifestationsSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Type ticketsType = new TypeToken<Collection<Ticket>>() {}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {}.getType();

		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterBareManifestation);	
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate);
		gsonBuilder.registerTypeAdapter(LocalTime.class, adapterLocalTime);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, adapterLocalDateTime);
		
		gsonBuilder.registerTypeAdapter(ManifestationType.class, adapterManifestationTypeId);
		gsonBuilder.registerTypeAdapter(Salesman.class, adapterSalesmanId);
		gsonBuilder.registerTypeAdapter(Comment.class, adapterCommentId);
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentIdCollection);
		gsonBuilder.registerTypeAdapter(Ticket.class, adapterTicketId);
		gsonBuilder.registerTypeAdapter(ticketsType, adapterTicketIdCollection);

		
		

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	public static Gson ticketsSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();


		gsonBuilder.registerTypeAdapter(Ticket.class, adapterBareTicket);	
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate);
		gsonBuilder.registerTypeAdapter(LocalTime.class, adapterLocalTime);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, adapterLocalDateTime);
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationId);	
		gsonBuilder.registerTypeAdapter(Customer.class, adapterCustomerId);	

		

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	public static Gson commentsSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		//Type manifestationsType = new TypeToken<Map<String, Manifestation>>() {}.getType();

		//gsonBuilder.registerTypeAdapter(Salesman.class, adapterUserToUsername);	
		gsonBuilder.registerTypeAdapter(Comment.class, commentBareTicket);	
		gsonBuilder.registerTypeAdapter(Customer.class, adapterCustomerId);	
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationId);	

		

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	public static Gson customerTypeSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	public static Gson manifestationTypeSerializationFromFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Gson customGson = gsonBuilder.create();
		return customGson;
	}
}
