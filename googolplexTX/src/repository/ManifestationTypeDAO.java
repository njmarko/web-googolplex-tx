package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.ManifestationType;

public class ManifestationTypeDAO implements GenericDAO<ManifestationType, String> {

	private Map<String, ManifestationType> manifestationTypes = new ConcurrentHashMap<String, ManifestationType>();

	@Override
	public Collection<ManifestationType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManifestationType findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManifestationType save(ManifestationType saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManifestationType delete(ManifestationType key) {
		// TODO Auto-generated method stub
		return null;
	}

}
