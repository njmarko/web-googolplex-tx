package service;

import java.util.Collection;

import model.Comment;
import model.Manifestation;
import model.ManifestationType;
import web.dto.CommentDTO;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;
import web.dto.ManifestationTypeDTO;

public interface ManifestationService extends GenericService<Manifestation, String> {

	/*
	 * TODO: Add specific methods for manifestation
	 */
	
	public Collection<Manifestation> search(ManifestationSearchDTO searchParams);

	public Collection<Manifestation> findBySalesman(String salesman, ManifestationSearchDTO searchParams);
	
	public Collection<ManifestationType> findAllManifestationTypes();
	public ManifestationType findOneManifestationType(String key);
	public ManifestationType putOneManifestationType(String key, ManifestationTypeDTO dto);
	public ManifestationType postOneManifestationType(ManifestationTypeDTO dto);
	public ManifestationType deleteOneManifestationType(String key);
	
	public Manifestation save(ManifestationDTO dto);
	
	public Collection<Comment> findAllCommentsFromManifestation(String key);

	public Collection<Comment> findAllApprovedCommentsForManif(String key);
	
	public Comment findOneComment(String key);
	
	public Comment deleteComment(String key);
	
	public Comment save(CommentDTO dto);
	
	public Comment addUniqueComment(CommentDTO dto);
}
