package web;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.patch;
import static spark.Spark.delete;
import static spark.Spark.path;
import static spark.Spark.staticFiles;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import web.controller.ManifestationControler;
import web.controller.UserController;


public class GoogolplexTXMain {

	
	public static void main(String[] args) throws IOException {

		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 

		TestData.createTestData();
		
		
		/**
		 * Possible API mappings
		 * 
		 * [THIS ONE WAS CHOSEN. IMPLEMENT IT THIS WAY]
		 * First method of mapping has duplicate methods for comments and tickets
		 * It lacks clean mappings for different types of users {admin,salesman,customer}
		 * 
		 * api/manifestations
		 * api/manifestations/{idm}/
		 * api/manifestations/{idm}/tickets
		 * api/manifestations/{idm}/tickets/{idt}/
		 * api/manifestations/{idm}/comments/{idc}/  {this will target the same function as the api in users that gets comment by id
		 * api/users
		 * api/users/{idu}/
		 * api/users/{idu}/tickets
		 * api/users/{idu}/tickets/{idt}/
		 * api/users/{idu}/comments
		 * api/users/{idu}/comments/{id}
		 * 
		 * Second method with controllers for every independant entity. 
		 * One controller for users instead of one for admin,customer and salesman
		 * It still needs to solve comments for certain event or customer
		 * Search parameters can be send as path parameters in get request
		 * 
		 * api/manifestations
		 * api/manifestations/{idm}/
		 * api/users
		 * api/users/{idu}/
		 * api/tickets
		 * api/tickets/{idt}/
		 * api/comments
		 * api/comments/{idc}/
		 * api/customer-type
		 * api/customer-type/{key}/
		 * api/manifestation-type
		 * api/manifestation-type/{key}/
		 */
		
		
		/**
		 * Method to separate all the routes into groups for better readability
		 * You can write the whole API here, and just reference appropriate attributes from 
		 * controllers. If you name your attributes appropriately, it increases readability further.
		 */
		path("/api",()->{
			path("/manifestations",()->{
				get("",ManifestationControler.findAllManifestations);		
				post("", ManifestationControler.saveOneManifestation);
				path("/:idm",()->{
					get("", ManifestationControler.findOneManifestation);
					delete("", ManifestationControler.deleteOneManifestation);
					put("", ManifestationControler.editOneManifestation);
					path("/tickets",()->{
//						get("", ManifestationControler.findAllTickets);
						
					});
				});
			});
			path("/users",()->{
				get("", UserController.findAllUsers);
				post("", UserController.saveOneUser);
				path("/:idu",()->{
					get("", UserController.findOneUser);
				});
				
			});
			
		});


		
		/**
		 * Second method where controllers are constructed. 
		 * With this method you have to write a full path for every method
		 */
//		new ManifestationControler();
//		new UserController();
		
	}

}
