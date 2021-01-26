package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.Comment;
import support.JsonAdapter;

public class CommentDAO implements GenericDAO<Comment, String> {

	private Map<String, Comment> comments = new ConcurrentHashMap<String, Comment>();

	public Map<String, Comment> getComments() {
		return comments;
	}

	
	public String findNextId() {
		if (comments != null && comments.size() > 0) {		
			Long highest = comments.keySet().stream().map(Long::valueOf).sorted(Comparator.reverseOrder()).findFirst().get();
			Long id = highest + 1 ;
			return id.toString();
		}else {
			return "1";
		}
	}
	
	public void setComments(Map<String, Comment> comments) {
		this.comments = comments;
	}

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

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] Comments saving");
		try {
			Gson gson = JsonAdapter.commentsSerializationToFile();

			FileWriter writer = new FileWriter("data/comments.json");
			gson.toJson(comments, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
