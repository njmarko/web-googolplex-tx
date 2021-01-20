package service;

import java.util.Collection;

import model.CustomerType;
import model.User;
import web.dto.LoginDTO;
import web.dto.PasswordDTO;
import web.dto.RegisterDTO;
import web.dto.UserDTO;
import web.dto.UserSearchDTO;

public interface UserService extends GenericService<User, String> {

	public Collection<User> search(UserSearchDTO searchParams);

	public User registerUser(RegisterDTO registerData);
	
	public CustomerType determineCustomerType(Double points);
	
	public User login(LoginDTO loginData);
	
	public User update(UserDTO dto);
	
	public User changePassword(PasswordDTO dto);
	
	
}
