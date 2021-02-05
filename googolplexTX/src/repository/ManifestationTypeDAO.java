package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.ManifestationType;
import support.JsonAdapter;

public class ManifestationTypeDAO implements GenericDAO<ManifestationType, String> {

	private Map<String, ManifestationType> manifestationTypes = new ConcurrentHashMap<String, ManifestationType>();

	public Map<String, ManifestationType> getManifestationTypes() {
		return manifestationTypes;
	}

	public void setManifestationTypes(Map<String, ManifestationType> manifestationTypes) {
		this.manifestationTypes = manifestationTypes;
	}

	@Override
	public Collection<ManifestationType> findAll() {
		return manifestationTypes.values();
	}

	@Override
	public ManifestationType findOne(String key) {
		return manifestationTypes.get(key);
	}

	@Override
	public ManifestationType save(ManifestationType manifestationType) {
		return manifestationTypes.put(manifestationType.getName(), manifestationType);
	}

	@Override
	public ManifestationType delete(String key) {
		ManifestationType manifestationType = manifestationTypes.get(key);
		if (manifestationType != null) {
			manifestationType.setDeleted(true);
		}
		return manifestationType;
	}

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] ManifestationType saving");
		try {
			Gson gson = JsonAdapter.manifestationTypeSerializationToFile();

			FileWriter writer = new FileWriter("data/manifestationTypes.json");
			gson.toJson(manifestationTypes, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
