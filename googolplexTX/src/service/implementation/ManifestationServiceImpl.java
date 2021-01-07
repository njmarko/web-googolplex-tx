package service.implementation;

import java.util.Collection;

import model.Manifestation;
import repository.ManifestationDAO;
import service.ManifestationService;

public class ManifestationServiceImpl implements ManifestationService {

	private ManifestationDAO manifestationDAO;
	// TODO: Add required DAOs

	public ManifestationServiceImpl(ManifestationDAO manifestationDAO) {
		super();
		this.manifestationDAO = manifestationDAO;
	}

	@Override
	public Collection<Manifestation> findAll() {
		return this.manifestationDAO.findAll();
	}

	@Override
	public Manifestation findOne(String key) {
		return this.manifestationDAO.findOne(key);
	}

	@Override
	public Manifestation save(Manifestation entity) {
		this.manifestationDAO.save(entity);
		return entity;
	}

	@Override
	public Manifestation delete(String key) {
		return this.manifestationDAO.delete(key);
	}

	@Override
	public Manifestation update(Manifestation entity) {
		return this.manifestationDAO.save(entity);
	}

}
