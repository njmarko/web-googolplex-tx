package web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jetty.util.resource.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Address;
import model.Comment;
import model.Customer;
import model.CustomerType;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.InMemoryRepository;
import service.ManifestationService;
import service.UserService;
import service.implementation.ManifestationDao;
import support.JsonAdapterUtil;
import web.controller.ManifestationControler;
import web.controller.UserController;


import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import spark.Request;
import spark.Session;
import static spark.Spark.path;
import static spark.Spark.before;
import static spark.Spark.*;


public class GoogolplexTXMain {

	
	public static void main(String[] args) throws IOException {

		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		TestData.createTestData();
		
		
		/**
		 * Possible API mappings
		 * 
		 * First method of mapping has duplicate methods for comments and tickets
		 * It lacks clean mappings for different types of users {admin,salesman,customer}
		 * 
		 * api/manifestations
		 * api/manifestations/{idm}/
		 * api/manifestations/{idm}/tickets
		 * api/manifestations/{idm}/tickets/{idt}/
		 * api/manifestations/{idm}/comments/{idc}/
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
				
				
			});
			path("/users",()->{
				get("", UserController.findAllUsers);
				
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
