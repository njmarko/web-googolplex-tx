package support;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import model.Customer;
import model.Salesman;
import model.User;

public class JsonAdapterUtil {

	private JsonAdapterUtil() {
	}

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

	private static <T> Gson buildGsonAdapter(Type type, JsonSerializer<T> adapter) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(type, adapter);
		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	private static Gson buildGsonAdapterCollection(Collection<Type> types, Collection<Object> adapters) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		Iterator<Type> it1 = types.iterator();
		Iterator<Object> it2 = adapters.iterator();

		while (it1.hasNext() && it2.hasNext()) {
			System.out.println("test");
			gsonBuilder.registerTypeAdapter(it1.next(), it2.next());
		}

		gsonBuilder.registerTypeAdapter((Type) types.toArray()[0], adapters.toArray()[0]);
		Gson customGson = gsonBuilder.create();
		return customGson;
	}

	public static Gson manifestationSeraialization() {
		return buildGsonAdapter(Salesman.class, adapterUserToUsername);
	}

	public static Gson ticketsSeraialization() {
		Collection<Type> types = new ArrayList<Type>();
		Collection<Object> adapters = new ArrayList<>();

		types.add(Customer.class);
		adapters.add((Object) adapterUserToUsername);

		types.add(Salesman.class);
		adapters.add((Object) adapterUserToUsername);

		// return buildGsonAdapter(Customer.class, adapterUserToUsername);

		return buildGsonAdapterCollection(types, adapters);
	}

}
