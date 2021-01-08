package web.controller;

import service.UserService;
import service.implementation.UserServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

import java.net.HttpURLConnection;
import java.util.Collection;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import model.User;

public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
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
	

	public final Route findAllUsers = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			// TODO Check if admin is logged in
			// TODO add DTO class for search parameters
			res.type("application/json");
			Collection<User> users = userService.findAll();
			if (users == null) {
				halt(HttpStatus.NOT_FOUND_404,"No users found");
			}
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
