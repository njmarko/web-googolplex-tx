package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.Manifestation;
import support.JsonAdapter;

public class ManifestationDAO implements GenericDAO<Manifestation, String> {

	private Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();

	public Map<String, Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(Map<String, Manifestation> manifestations) {
		this.manifestations = manifestations;
	}

	@Override
	public Collection<Manifestation> findAll() {
		return manifestations.values();
	}

	@Override
	public Manifestation findOne(String key) {
		return manifestations.get(key);
	}

	@Override
	public Manifestation save(Manifestation saved) {
		return manifestations.put(saved.getId(), saved);
	}

	@Override
	public Manifestation delete(String key) {
		Manifestation manifestation = manifestations.get(key);
		if (manifestation != null) {
			manifestation.setDeleted(true);
		}
		return manifestation;	}

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] Manifestation saving");
		try {
			Gson gson = JsonAdapter.manifestationSeraialization();

			FileWriter writer = new FileWriter("data/manifestations.json");
			gson.toJson(manifestations, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
