package web.controller;

import service.UserService;
import service.implementation.UserServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.utils.StringUtils;
import support.CustTypeToCustTypeDTO;
import support.DateConverter;
import support.JsonAdapter;
import support.UserToUserDTO;
import spark.Filter;
import web.dto.CustomerTypeDTO;
import web.dto.LoginDTO;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;
import web.dto.PasswordDTO;
import web.dto.RegisterDTO;
import web.dto.SuspiciousSearchDTO;
import web.dto.UserDTO;
import web.dto.UserSearchDTO;

import static spark.Spark.*;

import java.net.HttpURLConnection;
import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import model.CustomerType;
import model.Manifestation;
import model.User;
import model.enumerations.UserRole;

public class UserController {

	private UserService userService;
	private Gson g;
	private Key key;

	public UserController(UserService uService) {
		super();
		this.userService = uService;
		this.g = new Gson();
		this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}

//	public UserController() {
//		
//		
//		/**
//		 * Another way to write a function that will be called.
//		 * This way requires you to write a full path, and has
//		 * more code than lambda function.
//		 */
//		get("/users", new Route() {			
//			@Override
//			public Object handle(Request arg0, Response arg1) throws Exception {
//				
//				return "Ovo vraca usere";
//			}		
//		});
//	}

	public final Route registerUser = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			res.type("application/json");
			String body = req.body();
			RegisterDTO registerData = g.fromJson(body, RegisterDTO.class);

			// if registerData userRole is null, service will create a customer by default
			// admin can create only customer and salesman, but not other admins

			String err = null;


			User loggedInUser = getAuthedUser(req);

			if (loggedInUser == null) {
				err = registerData.validate(null);
			} else {
				// only admin can register new salesman. Logged in customer can register another account 
				// but it can only be a customer account
				authenticateUser.handle(req, res);
				err = registerData.validate(loggedInUser.getUserRole());
			}
			if (!StringUtils.isEmpty(err)) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			User registered = userService.registerUser(registerData);
			if (registered == null) {
				halt(HttpStatus.BAD_REQUEST_400, "The username is already taken or you data is invalid");
			}
			// TODO Consider if you want to return UserDTO after registering right away

			return "User was sucessfully registered";
		}
	};

	public final Route login = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			res.type("application/json");
			String body = req.body();
			System.out.println(body);
			LoginDTO loginData = g.fromJson(body, LoginDTO.class);

			// check if user is logged in

			User loggedIn = getAuthedUser(req);
			if (loggedIn != null) {
				halt(HttpStatus.OK_200, "User " + loggedIn.getUsername() + " is already logged in");
			}

			String err = loginData.validate();
			;
			if (!StringUtils.isEmpty(err)) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			User user = userService.login(loginData);
			System.out.println(user);
			if (user == null) {
				halt(HttpStatus.BAD_REQUEST_400, "The username or password is wrong");
			} else if (user.getBlocked()) {
				halt(HttpStatus.FORBIDDEN_403, "Your account is blocked.");
			}

			String jwt = Jwts.builder().setSubject(user.getUsername())
					.setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 356L)).setIssuedAt(new Date())
					.signWith(key).compact();

			UserDTO retVal = UserToUserDTO.convert(user);
			retVal.setJwt(jwt);

			res.status(HttpStatus.OK_200);

			return g.toJson(retVal);
		}
	};

	public final Route changePassword = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			authenticateUser.handle(req, res);
			res.type("application/json");

			User authUser = getAuthedUser(req);
			String idu = req.params("idu");

			String body = req.body();
			PasswordDTO passwordData = g.fromJson(body, PasswordDTO.class);
			passwordData.setUsername(idu); // Set username manually from the path

			String err = passwordData.validate();

			if (err != null) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			if (authUser.getUsername().compareTo(idu) != 0) {
				// TODO: check if admin
				halt(HttpStatus.FORBIDDEN_403, "You can only change your own password");
			}

			User user = userService.changePassword(passwordData);
			if (user == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Wrong password");
			}

			res.status(HttpStatus.OK_200);
			return g.toJson(UserToUserDTO.convert(user));

		}
	};

	public final Route logout = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			// if registerData userRole is null, service will create a customer by default
			// admin can create only customer and salesman, but not other admins
			res.type("application/json");
			Session session = req.session(true);

			User loggedInUser = session.attribute("user");
			if (loggedInUser != null) {
				session.invalidate();
			}
			return "User succesfully logged out";
		}
	};

	public final Route findAllUsers = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			res.type("application/json");
			authenticateAdmin.handle(req, res);
			
			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				if (!v[0].trim().isBlank()) {
					queryParams.put(k, v[0]);
				}			
			});

			UserSearchDTO searchParams = g.fromJson(g.toJson(queryParams), UserSearchDTO.class);

			Collection<User> users = userService.search(searchParams);
			if (users == null) {
				halt(HttpStatus.NOT_FOUND_404, "No users found");
			}

			Collection<UserDTO> usersDTO = UserToUserDTO.convert(users);

			return g.toJson(UserToUserDTO.convert(users));
		}
	};

	public final Route updateOneUser = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO check for user priveledge (Admin can add Salesman, anyone can register
			// as customer)
			// TODO remove this method because register replaced it
			res.type("application/json");
			String body = req.body();
			UserDTO user = new Gson().fromJson(body, UserDTO.class);

			String err = user.validate();
			;
			if (!StringUtils.isEmpty(err)) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}

			User savedEntity = userService.update(user);
			if (savedEntity == null) {
				halt(HttpURLConnection.HTTP_BAD_REQUEST);
			}
			return g.toJson(UserToUserDTO.convert(savedEntity));
		}
	};

	public final Route findOneUser = new Route() {

		@Override
		public Object handle(Request req, Response res) {

			res.type("application/json");
			String idu = req.params("idu");
			User foundEntity = userService.findOne(idu);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No users found");
			}

			return new Gson().toJson(UserToUserDTO.convert(foundEntity));
		}
	};

	public final Route deleteOneUser = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			authenticateAdmin.handle(req, res);

			// res.type("application/json");
			String id = req.params("idu");
			User deletedEntity = userService.delete(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}
			
			if (deletedEntity.getUserRole() == UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "You cannot block ADMIN");
			}
			
			return HttpStatus.NO_CONTENT_204;
		}
	};

	public final Route findUsersFromSalesmanTickets = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO add pagination

			authenticateSalesmanOrAdmin.handle(req, res);

			res.type("application/json");
			String idu = req.params("idu");

			Collection<User> foundEntities = userService.findUsersThatBoughtFromSalesman(idu);
			if (foundEntities == null) {
				foundEntities = new ArrayList<User>();
			}

			return g.toJson(UserToUserDTO.convert(foundEntities));
		}
	};

	public final Route findAllCustomerTypes = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");
			authenticateUser.handle(req, res);

			Collection<CustomerType> foundEntities = userService.findAllCustomerTypes();
			if (foundEntities == null) {
				foundEntities = new ArrayList<CustomerType>();
			}

			// TODO consider using an adapter
			// TODO use DTO objects
			return g.toJson(CustTypeToCustTypeDTO.convert(foundEntities));
		}
	};
	
	
	public final Route findOneCustomerType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			res.type("application/json");

			String id = req.params("idct");

			CustomerType foundEntities = userService.findOneCustomerType(id);
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "Not found customer type.");
			}

			return g.toJson(CustTypeToCustTypeDTO.convert(foundEntities));
		}
	};

	
	public final Route addOneCustomerType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			authenticateAdmin.handle(req, res);
			
			res.type("application/json");

			String body = req.body();

			CustomerTypeDTO newEntity = g.fromJson(body, CustomerTypeDTO.class);
			
			CustomerType foundEntities = userService.postOneCustomerType(newEntity);
			
			if (foundEntities == null) {
				halt(HttpStatus.BAD_REQUEST_400, "Name Already exists.");
			}

			return g.toJson(CustTypeToCustTypeDTO.convert(foundEntities));
		}
	};
	
	public final Route putOneCustomerType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			authenticateAdmin.handle(req, res);
			
			res.type("application/json");

			String id = req.params("idct");
			String body = req.body();

			CustomerTypeDTO newEntity = g.fromJson(body, CustomerTypeDTO.class);

			
			
			CustomerType foundEntities = userService.putOneCustomerType(id, newEntity);
			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404, "Name already exists or old-Name is invalid.");
			}

			return g.toJson(CustTypeToCustTypeDTO.convert(foundEntities));
		}
	};
	
	public final Route deleteOneCustomerType = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			authenticateAdmin.handle(req, res);

			// res.type("application/json");
			String id = req.params("idct");
			CustomerType deletedEntity = userService.deleteOneCustomerType(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};
	
	/**
	 * Checks if user is logged in (This can be any type of user {CUSTOMER,
	 * SALESMAN, ADMIN). It can be called in the before method for all the paths
	 * that require the user to be logged in
	 */
	public final Filter authenticateUser = new Filter() {

		@Override
		public void handle(Request req, Response res) throws Exception {

			User user = getAuthedUser(req);

			if (user == null) {
				halt(HttpStatus.UNAUTHORIZED_401, "You must be logged in.");
			} else if (user.getBlocked()) {
				halt(HttpStatus.UNAUTHORIZED_401, "Your account is blocked.");
			} else if (user.getDeleted()) {
				halt(HttpStatus.UNAUTHORIZED_401, "Your account no longer exists.");
			}

		}
	};

	/**
	 * Checks if user is logged in and check if his role is SALESMAN or ADMIN
	 */
	public final Filter authenticateSalesmanOrAdmin = new Filter() {
		@Override
		public void handle(Request req, Response res) throws Exception {
			authenticateUser.handle(req, res);

			User user = getAuthedUser(req);
			if (user == null) {
				halt(HttpStatus.UNAUTHORIZED_401, "You must be logged in.");
			}

			if (user.getUserRole() != UserRole.SALESMAN && user.getUserRole() != UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "This action is not allowed for your role.");
			}

		}
	};

	/**
	 * Checks if user is logged in and check if his role is ADMIN
	 */
	public final Filter authenticateAdmin = new Filter() {
		@Override
		public void handle(Request req, Response res) throws Exception {
			authenticateUser.handle(req, res);

			User user = getAuthedUser(req);
			if (user == null) {
				halt(HttpStatus.UNAUTHORIZED_401, "You must be logged in.");
			}

			if (user.getUserRole() != UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "This action is not allowed for your role.");
			}

		}
	};

	/**
	 * Checks if user is logged in and check if his role is SALESMAN
	 */
	public final Filter authenticateSalesmanOnly = new Filter() {
		@Override
		public void handle(Request req, Response res) throws Exception {
			authenticateUser.handle(req, res);

			User user = getAuthedUser(req);
			if (user == null) {
				halt(HttpStatus.UNAUTHORIZED_401, "You must be logged in.");
			}

			if (user.getUserRole() != UserRole.SALESMAN) {
				halt(HttpStatus.FORBIDDEN_403, "This action is not allowed for your role.");
			}

		}
	};

	public User getAuthedUser(Request req) {
		String auth = req.headers("Authorization");
		if ((auth != null) && (auth.contains("Bearer "))) {
			String incommingJwt = auth.substring(auth.indexOf("Bearer ") + 7);
			try {
				Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(incommingJwt);
				User user = userService.findOne(claims.getBody().getSubject());
				return user;

			} catch (Exception e) {
				// TODO We are currently not throwing this error anywhere, so consider if we
				// should add it in somewhere: halt(HttpStatus.BAD_REQUEST_400, "Invalid JWT
				// token");
				return null;
			}
		}

		return null;

	}
	
	
	public final Route findAllSuspiciousCustomers = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			res.type("application/json");
			
			authenticateAdmin.handle(req, res);
			
			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				if (!v[0].trim().isBlank()) {
					queryParams.put(k, v[0]);
				}			
			});
			
			System.out.println(queryParams.get("frequiency"));
			
			SuspiciousSearchDTO suspiciousDTO = g.fromJson(new  Gson().toJson(queryParams), SuspiciousSearchDTO.class);
		
			Collection<User> users = userService.findAllSuspiciousCustomers(suspiciousDTO);
			if (users == null) {
				halt(HttpStatus.NOT_FOUND_404, "No users found");
			}


			return g.toJson(UserToUserDTO.convert(users));
		}
	};
	
	public final Route blockUser = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			authenticateAdmin.handle(req, res);

			
			res.type("application/json");
			String idu = req.params("idu");
			User foundEntity = userService.blockUser(idu);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No users found");
			}
			if (foundEntity.getUserRole() == UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "You cannot block ADMIN");
			}
			

			return new Gson().toJson(UserToUserDTO.convert(foundEntity));
		}
	};
	

	
	public final Route unblockUser = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {

			authenticateAdmin.handle(req, res);

			
			res.type("application/json");
			String idu = req.params("idu");
			User foundEntity = userService.unblockUser(idu);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No users found");
			}
			if (foundEntity.getUserRole() == UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "You cannot block ADMIN");
			}

			return new Gson().toJson(UserToUserDTO.convert(foundEntity));
		}
	};
	
}
