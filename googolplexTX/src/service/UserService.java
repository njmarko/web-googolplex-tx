package service;

import java.util.Collection;

import model.User;
import web.dto.UserSearchDTO;

public interface UserService extends GenericService<User, String> {

	public Collection<User> search(UserSearchDTO searchParams);

}
