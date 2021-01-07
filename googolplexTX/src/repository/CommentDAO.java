package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Comment;

public class CommentDAO implements GenericDAO<Comment, String> {

	private Map<String, Comment> comments = new ConcurrentHashMap<String, Comment>();

	@Override
	public Collection<Comment> findAll() {
		return comments.values();
	}

	@Override
	public Comment findOne(String key) {
		return comments.get(key);
	}

	@Override
	public Comment save(Comment comment) {
		return comments.put(comment.getId(), comment);

	}

	@Override
	public Comment delete(String key) {
		Comment comment = comments.get(key);
		if (comment != null) {
			comment.setDeleted(true);
		}
		return comment;
	}

}
