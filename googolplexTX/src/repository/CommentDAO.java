package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Comment;

public class CommentDAO implements GenericDAO<Comment, String> {

	private Map<String, Comment> comments = new ConcurrentHashMap<String, Comment>();

	@Override
	public Collection<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment save(Comment saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment delete(Comment key) {
		// TODO Auto-generated method stub
		return null;
	}

}
