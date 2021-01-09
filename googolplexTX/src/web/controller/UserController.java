package web.controller;

import service.UserService;
import service.implementation.UserServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;
import spark.Filter;
import web.dto.LoginDTO;
import web.dto.ManifestationSearchDTO;
import web.dto.RegisterDTO;
import web.dto.UserSearchDTO;

import static spark.Spark.*;

import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import model.User;

public class UserController {

	private UserService userService;
	private Gson g;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
		this.g = new Gson();
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
	
	/**
	 * Checks if user is logged in.
	 * It is called in the before method for all the paths that require the user to be logged in 
	 */
	public static final Filter authenticate = new Filter() {

		@Override
		public void handle(Request req, Response res) throws Exception {
			User user = req.session().attribute("user");
			if (user == null) {
				halt(HttpStatus.UNAUTHORIZED_401, "You must be logged in.");
			}	
		}
		
	};
	
	
	public final Route registerUser = new Route() {
		
		@Override
		public Object handle(Request req, Response res) throws Exception {
			
			String body = req.body();
			RegisterDTO registerData = g.fromJson(body, RegisterDTO.class);
			
			// if registerData userRole is null, service will create a customer by default
			// admin can create only customer and salesman, but not other admins
			
			String err = null;
			User loggedInUser = req.session().attribute("user");
			if (loggedInUser == null) {
				err = registerData.validate(null);
			}else {
				err = registerData.validate(loggedInUser.getUserRole());
			}
			if (!StringUtils.isEmpty(err)) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}
			
			User registered = userService.registerUser(registerData);
			if (registered == null) {
				halt(HttpStatus.BAD_REQUEST_400,"The username is already taken");
			}
			// TODO Consider if you want to return UserDTO after registering right away
			
			return  "User was sucessfully registered";
		}
	};
	
	
	public final Route login = new Route() {
		
		@Override
		public Object handle(Request req, Response res) throws Exception {
			
			String body = req.body();
			LoginDTO loginData = g.fromJson(body, LoginDTO.class);
			
			// if registerData userRole is null, service will create a customer by default
			// admin can create only customer and salesman, but not other admins
			
			String err = null;
			User loggedInUser = req.session().attribute("user");
			if (loggedInUser == null) {
				err = loginData.validate();
			}else {
				halt(HttpStatus.OK_200, "Already logged in");
			}
			if (!StringUtils.isEmpty(err)) {
				halt(HttpStatus.BAD_REQUEST_400, err);
			}
			
			User user = userService.login(loginData);
			if (user == null) {
				halt(HttpStatus.BAD_REQUEST_400,"The username or password is wrong");
			}
			
			req.session(true).attribute("user",user);
			
			// TODO Consider if you want to return UserDTO after logging in right away
			
			return  "User was sucessfully registered";
		}
	};
	
	
	public final Route findAllUsers = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			// TODO Check if admin is logged in. 
			// TODO add DTO class for search parameters
			res.type("application/json");
			
			final Map<String, String> queryParams = new HashMap<>();
		    req.queryMap().toMap().forEach((k, v) -> {
		      queryParams.put(k, v[0]);
		    });		    
		    UserSearchDTO searchParams = g.fromJson(g.toJson(queryParams), UserSearchDTO.class);
			
		    // TODO remove debug print message
		    System.out.println("[DBG] searchParamsDTO" + searchParams);
		    
			Collection<User> users = userService.search(searchParams);
			if (users == null) {
				halt(HttpStatus.NOT_FOUND_404,"No users found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return g.toJson(users);	
		}
	};
	
	public final Route saveOneUser = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			//TODO check for user priveledge (Admin can add Salesman, anyone can register as customer)
			res.type("application/json");
			String body = req.body();
			User user = new Gson().fromJson(body, User.class);
			User savedEntity = userService.save(user);
			if (savedEntity == null) {
				halt(HttpURLConnection.HTTP_BAD_REQUEST);
			}
			return new Gson().toJson(savedEntity);	
		}
	};
	
	public final Route findOneUser = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			//TODO check if logged in
			
			res.type("application/json");
			String idu = req.params("idu");
			User foundEntity = userService.findOne(idu);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404,"No users found");
			}
//			String body = req.body();
//			System.out.println(body);
//			User user = new Gson().fromJson(body, User.class);
//			User savedEntity = userService.save(user);
//			if (savedEntity == null) {
//				halt(HttpURLConnection.HTTP_BAD_REQUEST);
//			}
//			return new Gson().toJson(savedEntity);	
			return new Gson().toJson(foundEntity);	
		}
	};
	
	

	
	
	
}
