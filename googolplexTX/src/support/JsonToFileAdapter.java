package support;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import model.Comment;
import model.Customer;
import model.CustomerType;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;

public class JsonToFileAdapter {

//	private static Map<UserRole, Class<?>> userCastMap = new TreeMap<UserRole, Class<?>>();

	
//	private JsonToFileAdapter() {
//		userCastMap.put(UserRole.ADMIN, User.class);
//		userCastMap.put(UserRole.SALESMAN, Salesman.class);
//		userCastMap.put(UserRole.CUSTOMER, Customer.class);
//		System.out.println("Desava se JsonToFile");
//	}



	
	public static final JsonSerializer<Collection<Salesman>> serializeSalesmanCollection = new JsonSerializer<Collection<Salesman>>() {
		@Override
		public JsonElement serialize(Collection<Salesman> src, Type typeOfSrc, JsonSerializationContext context) {

			JsonObject jsonSalesman = new JsonObject();

			Collection<String> salesmanIds = new ArrayList<>(src.size());
			for (Salesman salesman : src) {
				salesmanIds.add("" + salesman.getUsername());
			}

			String salesmanUsernamesString = String.join(", ", salesmanIds);

			jsonSalesman.addProperty("Ids", salesmanUsernamesString);

			return jsonSalesman;
		}
	};

	public static final JsonSerializer<User> adapterUserToUsername = new JsonSerializer<User>() {
		@Override
		public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {

			JsonElement jsonElement = new JsonPrimitive(src.getUsername());

			return jsonElement;
		}
	};
	public static final JsonSerializer<Collection<User>> adapterUsersTypeToUsernamess= new JsonSerializer<Collection<User>>() {
		@Override
		public JsonElement serialize(Collection<User> src, Type typeOfSrc, JsonSerializationContext context) {

			JsonArray jsonUser = new JsonArray();

			for (User user : src) {
				jsonUser.add(new JsonPrimitive(user.getUsername()));
			}

			return jsonUser;
		}
	};
	

	public static final JsonSerializer<ManifestationType> adapterManifestationTypeToName = new JsonSerializer<ManifestationType>() {

		@Override
		public JsonElement serialize(ManifestationType src, Type typeOfSrc, JsonSerializationContext context) {
			JsonElement jsonElement = new JsonPrimitive(src.getName());
			return jsonElement;
		}

	};

	public static final JsonSerializer<CustomerType> adapterCustomerTypeToName = new JsonSerializer<CustomerType>() {

		@Override
		public JsonElement serialize(CustomerType src, Type typeOfSrc, JsonSerializationContext context) {
			JsonElement jsonElement = new JsonPrimitive(src.getName());
			return jsonElement;
		}
		
	};

	public static final JsonSerializer<Manifestation> adapterManifestationToId = new JsonSerializer<Manifestation>() {

		@Override
		public JsonElement serialize(Manifestation src, Type typeOfSrc, JsonSerializationContext context) {
			JsonElement jsonElement = new JsonPrimitive(src.getId());
			return jsonElement;
		}

	};
	public static final JsonSerializer<Collection<Manifestation>> adapterManifestationToIds= new JsonSerializer<Collection<Manifestation>>() {
		@Override
		public JsonElement serialize(Collection<Manifestation> src, Type typeOfSrc, JsonSerializationContext context) {

			JsonArray jsonManifestations = new JsonArray();

			for (Manifestation manifestation : src) {
				jsonManifestations.add(new JsonPrimitive(manifestation.getId()));
			}

			return jsonManifestations;
		}
	};

	public static final JsonSerializer<Ticket> adapterTicketToId = new JsonSerializer<Ticket>() {

		@Override
		public JsonElement serialize(Ticket src, Type typeOfSrc, JsonSerializationContext context) {
			JsonElement jsonElement = new JsonPrimitive(src.getId());
			return jsonElement;
		}

	};
	public static final JsonSerializer<Collection<Ticket>> adapterTicketsTypeToIds= new JsonSerializer<Collection<Ticket>>() {
		@Override
		public JsonElement serialize(Collection<Ticket> src, Type typeOfSrc, JsonSerializationContext context) {

			JsonArray jsonTickets = new JsonArray();

			for (Ticket ticket : src) {
				jsonTickets.add(new JsonPrimitive(ticket.getId()));
			}

			return jsonTickets;
		}
	};

	public static final JsonSerializer<Comment> adapterCommentToId = new JsonSerializer<Comment>() {

		@Override
		public JsonElement serialize(Comment src, Type typeOfSrc, JsonSerializationContext context) {
			JsonElement jsonElement = new JsonPrimitive(src.getId());
			return jsonElement;
		}

	};
	public static final JsonSerializer<Collection<Comment>> adapterCommentsTypeToIds= new JsonSerializer<Collection<Comment>>() {
		@Override
		public JsonElement serialize(Collection<Comment> src, Type typeOfSrc, JsonSerializationContext context) {

			JsonArray jsonComments = new JsonArray();

			for (Comment comment : src) {
				jsonComments.add(new JsonPrimitive(comment.getId()));
			}

			return jsonComments;
		}
	};

	
	
//	public static final JsonSerializer<Map<String, User>> adapterFullUserCast= new JsonSerializer<Map<String, User>>() {
//		
//
//		
//		@Override
//		public JsonElement serialize(Map<String, User> src, Type typeOfSrc, JsonSerializationContext context) {
//
//			if (src == null)
//	            return null;
//	        else {
//	            JsonArray jsonUsers = new JsonArray();
//	            for (User baseUser : src.values()) {
//	                Class<?> c = userCastMap.get(baseUser.getUserRole());
//	                if (c == null)
//	                    throw new RuntimeException("Unknow class: " + baseUser.getUserRole());
//	                jsonUsers.add(context.serialize(baseUser, c));
//
//	            }
//				return jsonUsers;
//	        }
//
//		}
//	};
	
	
	public static final JsonSerializer<LocalDate> adapterLocalDate = new JsonSerializer<LocalDate>() {
		@Override
		public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("year", src.getYear());
			jsonObject.addProperty("month", src.getMonthValue());
			jsonObject.addProperty("day", src.getDayOfMonth());
			
			return jsonObject;
		}

	};
	
	public static final JsonSerializer<LocalTime> adapterLocalTime = new JsonSerializer<LocalTime>() {
		@Override
		public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("hour", src.getHour());
			jsonObject.addProperty("minute", src.getMinute());
			jsonObject.addProperty("second", src.getSecond());
			
			return jsonObject;
		}

	};
	
	public static final JsonSerializer<LocalDateTime> adapterLocalDateTime = new JsonSerializer<LocalDateTime>() {
		@Override
		public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();

			jsonObject.add("date", context.serialize(src.toLocalDate(), (Class<LocalDate>) LocalDate.class));
			jsonObject.add("time", context.serialize(src.toLocalTime(), (Class<LocalTime>) LocalTime.class));
			
			return jsonObject;
		}

	};
	
	//////////// Builders ///////////


	public static Gson manifestationSeraialization() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Type ticketsType = new TypeToken<Collection<Ticket>>() {}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {}.getType();

		gsonBuilder.serializeNulls();
		gsonBuilder.registerTypeAdapter(ticketsType, adapterTicketsTypeToIds);
		gsonBuilder.registerTypeAdapter(Salesman.class, adapterUserToUsername);	
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentsTypeToIds);	
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate);
		gsonBuilder.registerTypeAdapter(LocalTime.class, adapterLocalTime);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, adapterLocalDateTime);
		gsonBuilder.registerTypeAdapter(ManifestationType.class, adapterManifestationTypeToName);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	public static Gson ticketsSeraialization() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.serializeNulls();
		gsonBuilder.registerTypeAdapter(Customer.class, adapterUserToUsername);
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationToId);
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate);
		gsonBuilder.registerTypeAdapter(LocalTime.class, adapterLocalTime);
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, adapterLocalDateTime);
		
		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	public static Gson userSerializationToFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

	//	Type usersType = new TypeToken<Map<String, User>>() {}.getType();
		Type ticketsType = new TypeToken<Collection<Ticket>>() {}.getType();
		Type manifestationsType = new TypeToken<Collection<Manifestation>>() {}.getType();
		Type commentsType = new TypeToken<Collection<Comment>>() {}.getType();


		gsonBuilder.serializeNulls();
	//	gsonBuilder.registerTypeAdapter(usersType, adapterFullUserCast);
		gsonBuilder.registerTypeAdapter(ticketsType, adapterTicketsTypeToIds);
		gsonBuilder.registerTypeAdapter(manifestationsType, adapterManifestationToIds);
		gsonBuilder.registerTypeAdapter(CustomerType.class, adapterCustomerTypeToName);
		gsonBuilder.registerTypeAdapter(commentsType, adapterCommentsTypeToIds);
		gsonBuilder.registerTypeAdapter(LocalDate.class, adapterLocalDate );

		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	public static Gson commentsSerializationToFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.serializeNulls();
		gsonBuilder.registerTypeAdapter(Customer.class, adapterUserToUsername);
		gsonBuilder.registerTypeAdapter(Manifestation.class, adapterManifestationToId);

		Gson customGson = gsonBuilder.create();
		return customGson;
	}


	public static Gson customerTypeSerializationToFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.serializeNulls();
		Gson customGson = gsonBuilder.create();
		return customGson;
	}
	
	public static Gson manifestationTypeSerializationToFile() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.serializeNulls();
		Gson customGson = gsonBuilder.create();
		return customGson;
	}
}
