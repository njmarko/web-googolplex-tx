package service.implementation;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Manifestation;
import repository.GenericRepository;
import repository.ManifestationRepository;
import service.ManifestationService;

public class ManifestationDao implements ManifestationService {

	Map<String, Manifestation> manifestations = new ConcurrentHashMap<String, Manifestation>();

	/*
	 * TODO consider transfering collections to the repository class
	 */
	GenericRepository<Map<String,Manifestation>> repository = new ManifestationRepository();
	
	@Override
	public Collection<Manifestation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manifestation findOne(String id) {
		return manifestations.getOrDefault(id, null);
	}

	@Override
	public Manifestation save(Manifestation object) {
		// TODO Auto-generated method stub
		manifestations.put(object.getId(), object);			
		repository.save(manifestations);
		return object;
	}

	@Override
	public Manifestation delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manifestation update(Manifestation object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void load() {
		manifestations = repository.load();
	}

}
