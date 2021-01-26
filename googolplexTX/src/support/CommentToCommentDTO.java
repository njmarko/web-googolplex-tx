package support;

import java.util.Collection;
import java.util.stream.Collectors;

import model.Comment;
import web.dto.CommentDTO;

public class CommentToCommentDTO {

	public static CommentDTO convert(Comment comment) {
		CommentDTO retVal = new CommentDTO();

		retVal.setId(comment.getId());
		retVal.setCustomer(comment.getCustomer().getUsername());
		retVal.setManifestation(comment.getManifestation().getId());
		retVal.setApproved(comment.getApproved().name());
		retVal.setRating(comment.getRating());
		retVal.setText(comment.getText());

		return retVal;
	}

	public static Collection<CommentDTO> convert(Collection<Comment> allComments) {
		return allComments.stream().map(CommentToCommentDTO::convert).collect(Collectors.toList());
	}
}
