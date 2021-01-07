package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.User;

public class UserDAO implements GenericDAO<User, String> {

	private Map<String, User> users = new ConcurrentHashMap<String, User>();

	@Override
	public Collection<User> findAll() {
		return users.values();
	}

	@Override
	public User findOne(String key) {
		return users.get(key);
	}

	@Override
	public User save(User user) {
		return users.put(user.getUsername(), user);
	}

	@Override
	public User delete(String key) {
		User user = users.get(key);
		if (user != null) {
			user.setDeleted(true);
		}
		return user;
	}

}
