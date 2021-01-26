package service;

import java.util.Collection;

import model.Comment;
import model.Manifestation;
import model.ManifestationType;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;

public interface ManifestationService extends GenericService<Manifestation, String> {

	/*
	 * TODO: Add specific methods for manifestation
	 */
	
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams);

	public Collection<Manifestation> findBySalesman(String salesman, ManifestationSearchDTO searchParams);
	
	public Collection<ManifestationType> findAllManifestationTypes();
	public ManifestationType deleteOneManifestationType(String key);
	
	public Manifestation save(ManifestationDTO dto);
	
	public Collection<Comment> findAllCommentsFromManifestation(String key);

	public Collection<Comment> findAllApprovedCommentsForManif(String key);
	
	public Comment findOneComment(String key);
	public Comment deleteComment(String key);
}
