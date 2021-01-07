package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.ManifestationType;

public class ManifestationTypeDAO implements GenericDAO<ManifestationType, String> {

	private Map<String, ManifestationType> manifestationTypes = new ConcurrentHashMap<String, ManifestationType>();

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

}
