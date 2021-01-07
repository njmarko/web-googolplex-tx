package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import model.User;
import support.JsonToFileAdapter;

public class UserDAO implements GenericDAO<User, String> {

	private Map<String, User> users = new ConcurrentHashMap<String, User>();

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

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

	@Override
	public Boolean saveFile() {
		System.out.println("[LOG] Users saving...");
		try {
			Gson gson = JsonToFileAdapter.userSerializationToFile();

			FileWriter writer = new FileWriter("data/users.json");
			gson.toJson(users, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
