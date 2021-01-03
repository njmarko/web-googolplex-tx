package web.controller;

import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

public class UserController {

	
	public UserController() {
		
		get("/users", new Route() {			
			@Override
			public Object handle(Request arg0, Response arg1) throws Exception {
				
				return "Ovo vraca usere";
			}		
		});
	}
	
}
