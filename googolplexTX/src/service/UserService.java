package service;

import java.util.Collection;

import model.CustomerType;
import model.ManifestationType;
import model.User;
import web.dto.CustomerTypeDTO;
import web.dto.LoginDTO;
import web.dto.PasswordDTO;
import web.dto.RegisterDTO;
import web.dto.SuspiciousSearchDTO;
import web.dto.UserDTO;
import web.dto.UserSearchDTO;

public interface UserService extends GenericService<User, String> {

	public Collection<User> search(UserSearchDTO searchParams);
	public Collection<User> findUsersThatBoughtFromSalesman(String salesman);

	public User registerUser(RegisterDTO registerData);
	
	public CustomerType determineCustomerType(Double points);

	
	public User login(LoginDTO loginData);
	
	public User update(UserDTO dto);
	
	public User changePassword(PasswordDTO dto);
	
	public Collection<CustomerType> findAllCustomerTypes();
	public CustomerType findOneCustomerType(String key);
	public CustomerType putOneCustomerType(String key, CustomerTypeDTO dto);
	public CustomerType postOneCustomerType(CustomerTypeDTO dto);
	public CustomerType deleteOneCustomerType(String key);

	public Collection<User> findAllSuspiciousCustomers(SuspiciousSearchDTO dto);
	
	public User blockUser(String key);
	public User unblockUser(String key);
}
