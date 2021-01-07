package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Manifestation;

public class ManifestationDAO implements GenericDAO<Manifestation, String> {

	private Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();

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

}
