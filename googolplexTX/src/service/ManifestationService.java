package service;

import java.util.Collection;

import model.Comment;
import model.Manifestation;
import web.dto.ManifestationSearchDTO;

public interface ManifestationService extends GenericService<Manifestation, String> {

	/*
	 * TODO: Add specific methods for manifestation
	 */
	
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams);

	public Collection<Manifestation> findBySalesman(String salesman);
	
	public Collection<Comment> findAllCommentsFromManifestation(String key);
}
