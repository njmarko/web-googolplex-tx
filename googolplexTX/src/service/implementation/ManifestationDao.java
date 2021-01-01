package service.implementation;

import java.util.Collection;

import model.Manifestation;
import repository.InMemoryRepository;
import service.ManifestationService;

public class ManifestationDao implements ManifestationService {

	@Override
	public Collection<Manifestation> findAll() {
		return InMemoryRepository.findAllManifestations();
	}

	@Override
	public Manifestation findOne(String key) {
		return InMemoryRepository.findOneManifestation(key);
	}

	@Override
	public Manifestation save(Manifestation entity) {
		InMemoryRepository.save(entity);
		return entity;
	}

	@Override
	public Manifestation delete(String id) {
		return InMemoryRepository.deleteManifestation(id);
	}

	@Override
	public Manifestation update(Manifestation object) {
		return InMemoryRepository.save(object);
	}

}
