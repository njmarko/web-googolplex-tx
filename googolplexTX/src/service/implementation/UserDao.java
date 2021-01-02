package service.implementation;

import java.util.Collection;

import model.User;
import repository.InMemoryRepository;
import service.UserService;

public class UserDao implements UserService {

	@Override
	public Collection<User> findAll() {
		return InMemoryRepository.findAllUsers();
	}

	@Override
	public User findOne(String key) {
		return InMemoryRepository.findOneUser(key);
	}

	@Override
	public User save(User entity) {
		return InMemoryRepository.save(entity);
	}

	@Override
	public User delete(String key) {
		return InMemoryRepository.deleteUser(key);
	}

	/**
	 * Update all but password
	 */
	@Override
	public User update(User entity) {
		User user = findOne(entity.getUsername());
		entity.setPassword(user.getPassword());
		return InMemoryRepository.save(entity);
	}

}
