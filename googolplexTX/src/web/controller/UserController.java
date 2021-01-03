package web.controller;

import service.UserService;
import service.implementation.UserDao;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

import java.util.Collection;

import com.google.gson.Gson;

import model.Manifestation;
import model.User;

public class UserController {

	public static final UserService userService = new UserDao();
	
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
	public static final Route findAllUsers = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			res.type("application/json");
			Collection<User> users = userService.findAll();
			System.out.println(users);
			return new Gson().toJson(users);	
		}
	};
	
	
}
