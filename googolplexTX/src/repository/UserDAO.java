package repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.User;

public class UserDAO implements GenericDAO<User, String> {

	private Map<String, User> users = new ConcurrentHashMap<String, User>();

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User saved) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User delete(User key) {
		// TODO Auto-generated method stub
		return null;
	}

}
