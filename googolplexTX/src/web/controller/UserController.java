package web.controller;

import service.UserService;
import service.implementation.UserServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

import java.net.HttpURLConnection;
import java.util.Collection;

import com.google.gson.Gson;

import model.Manifestation;
import model.User;

public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this();
		this.userService = userService;
	}

	public UserController() {
		
		
		/**
		 * Another way to write a function that will be called.
		 * This way requires you to write a full path, and has
		 * more code than lambda function.
		 */
		get("/users", new Route() {			
			@Override
			public Object handle(Request arg0, Response arg1) throws Exception {
				
				return "Ovo vraca usere";
			}		
		});
	}
	
	/**
	 * Define a static attribute in this way so it can be used with paths in main
	 */
	public final Route findAllUsers = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			// TODO Check if logged in
			res.type("application/json");
			Collection<User> users = userService.findAll();
			System.out.println(users);
			return new Gson().toJson(users);	
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
				return HttpURLConnection.HTTP_BAD_REQUEST;
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
			String body = req.body();
			User user = new Gson().fromJson(body, User.class);
			User savedEntity = userService.save(user);
			if (savedEntity == null) {
				return HttpURLConnection.HTTP_BAD_REQUEST;
			}
			return new Gson().toJson(savedEntity);	
		}
	};
	
	
	
}
