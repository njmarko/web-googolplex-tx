package service.implementation;

import java.util.Collection;

import model.User;
import repository.UserDAO;
import service.UserService;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;
	
	public UserServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	@Override
	public Collection<User> findAll() {
		return this.userDAO.findAll();
	}

	@Override
	public User findOne(String key) {
		return this.userDAO.findOne(key);
	}

	@Override
	public User save(User entity) {
		return this.userDAO.save(entity);
	}

	@Override
	public User delete(String key) {
		return this.userDAO.delete(key);
	}

	/**
	 * Update all but password
	 */
	@Override
	public User update(User entity) {
		User user = findOne(entity.getUsername());
		entity.setPassword(user.getPassword());
		return this.userDAO.save(entity);
	}

}
