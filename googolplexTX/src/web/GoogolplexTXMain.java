package web;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import repository.CommentDAO;
import repository.CustomerTypeDAO;
import repository.DAOFileParser;
import repository.ManifestationDAO;
import repository.ManifestationTypeDAO;
import repository.TicketDAO;
import repository.UserDAO;
import service.implementation.ManifestationServiceImpl;
import service.implementation.TicketServiceImpl;
import service.implementation.UserServiceImpl;
import web.controller.ManifestationControler;
import web.controller.UserController;


public class GoogolplexTXMain {

	
	public static void main(String[] args) throws IOException {

		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 

		
		// aUtOwIrEd
		UserDAO userDAO = new UserDAO();
		ManifestationDAO manifestationDAO = new ManifestationDAO();
		TicketDAO ticketDAO = new TicketDAO();
		CommentDAO commentDAO = new CommentDAO();
		ManifestationTypeDAO manifestationTypeDAO = new ManifestationTypeDAO();
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO();
		
		UserServiceImpl userServiceImpl = new UserServiceImpl(userDAO);
		ManifestationServiceImpl manifestationServiceImpl = new ManifestationServiceImpl(manifestationDAO);
		TicketServiceImpl ticketServiceImpl = new TicketServiceImpl(ticketDAO); 
		
		ManifestationControler manifestationControler = new ManifestationControler(manifestationServiceImpl);
		UserController userController = new UserController(userServiceImpl);
			
		DAOFileParser daoFileParser = new DAOFileParser(userDAO, manifestationDAO, ticketDAO, commentDAO, customerTypeDAO, manifestationTypeDAO);
		daoFileParser.loadData();
		//TestData.createTestData(userDAO, manifestationDAO, ticketDAO, commentDAO, manifestationTypeDAO, customerTypeDAO);
		
		userDAO.saveFile();
		manifestationDAO.saveFile();
		ticketDAO.saveFile();
		commentDAO.saveFile();
		manifestationTypeDAO.saveFile();
		customerTypeDAO.saveFile();
		
		
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
				get("",manifestationControler.findAllManifestations);
				get("/:idm", manifestationControler.findOneManifestation);
				
			});
			path("/users",()->{
				get("", userController.findAllUsers);
				
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
