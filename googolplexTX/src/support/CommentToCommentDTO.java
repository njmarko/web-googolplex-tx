package support;

import java.util.Collection;
import java.util.stream.Collectors;

import model.Comment;
import web.dto.CommentDTO;

public class CommentToCommentDTO {

	public static CommentDTO convert(Comment comment) {
		CommentDTO dto = new CommentDTO();

		dto.setId(comment.getId());
		dto.setManifestation(comment.getManifestation().getId());
		dto.setApproved(comment.getApproved().name());
		dto.setText(comment.getText());
		dto.setRating(comment.getRating());
		dto.setCustomer(comment.getCustomer().getUsername());

		return dto;
	}

	public static Collection<CommentDTO> convert(Collection<Comment> allComments) {
		return allComments.stream().map(CommentToCommentDTO::convert).collect(Collectors.toList());
	}

}
