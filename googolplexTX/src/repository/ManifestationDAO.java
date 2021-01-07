package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Manifestation;

public class ManifestationDAO implements GenericDAO<Manifestation, String> {

	private Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();

	@Override
	public Collection<Manifestation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manifestation findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manifestation save(Manifestation saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manifestation delete(Manifestation key) {
		// TODO Auto-generated method stub
		return null;
	}

}
