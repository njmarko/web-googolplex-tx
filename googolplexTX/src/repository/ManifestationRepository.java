package repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Manifestation;

public class ManifestationRepository implements GenericRepository<Map<String, Manifestation>> {

	/*
	 * TODO subject to change. It may need to be reworked so all the collections are in one class in order to serialize propertly.
	 * Will check after reading more about it
	 */
	
	@Override
	public Map<String, Manifestation> save(Map<String, Manifestation> object) {
		try {
			Gson gson = new Gson();
			FileWriter writer = new FileWriter("data/manifestations.json");
			gson.toJson(object, writer);
			writer.flush();
			writer.close();
			return object;
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Manifestation> load() {
		Gson gson = new Gson();
		Map<String, Manifestation> object = null;
		try {
			FileReader reader = new FileReader("data/manifestations.json");
			
			/*
			 * TODO Figure out how to load a collection with the correct types
			 */
			
			object = gson.fromJson(reader, ConcurrentHashMap.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
