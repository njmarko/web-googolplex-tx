package web;

import static spark.Spark.*;
import static spark.Spark.staticFiles;



import java.io.File;
import java.io.IOException;

import javaxt.http.Response;
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
import spark.Request;
import web.controller.ManifestationControler;
import web.controller.TicketController;
import web.controller.UserController;
import web.dto.CommentDTO;


public class GoogolplexTXMain {

	
	public static void main(String[] args) throws IOException {
		// This is how port can be changed. Default is 4567
//		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		
		// aUtOwIrEd
		UserDAO userDAO = new UserDAO();
		ManifestationDAO manifestationDAO = new ManifestationDAO();
		TicketDAO ticketDAO = new TicketDAO();
		CommentDAO commentDAO = new CommentDAO();
		ManifestationTypeDAO manifestationTypeDAO = new ManifestationTypeDAO();
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO();
		
		UserServiceImpl userServiceImpl = new UserServiceImpl(userDAO, customerTypeDAO);
		ManifestationServiceImpl manifestationServiceImpl = new ManifestationServiceImpl(manifestationDAO, manifestationTypeDAO, userDAO, commentDAO);
		TicketServiceImpl ticketServiceImpl = new TicketServiceImpl(ticketDAO, userDAO, manifestationDAO, customerTypeDAO); 
		
		UserController userController = new UserController(userServiceImpl);
		ManifestationControler manifestationControler = new ManifestationControler(manifestationServiceImpl,userController);
		TicketController ticketController = new TicketController(ticketServiceImpl,userController);
			
		//DAOFileParser daoFileParser = new DAOFileParser(userDAO, manifestationDAO, ticketDAO, commentDAO, customerTypeDAO, manifestationTypeDAO);
		//daoFileParser.loadData();
		TestData.createTestData(userDAO, manifestationDAO, ticketDAO, commentDAO, manifestationTypeDAO, customerTypeDAO);
		
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
		
		// TODO add before methods to do some checks when requests arrive (such as if user is logged in etc)
		/**
		 * Method to separate all the routes into groups for better readability
		 * You can write the whole API here, and just reference appropriate attributes from 
		 * controllers. If you name your attributes appropriately, it increases readability further.
		 */
		
		path("/api",()->{
			path("/login",()->{
				post("",userController.login);
			});
			path("/register",()->{
				post("",userController.registerUser); // everyone can register customer, only admin can register salesman
			});
			path("/logout",()->{
				get("",userController.logout);
			});
			
			path("/upload",()->{
				post("",manifestationControler.uploadImage);
			});
			
			path("/manifestations",()->{				
				get("",manifestationControler.findAllManifestations);	
				post("", manifestationControler.saveOneManifestation);	// req salesman		
				
				path("/:idm",()->{					
					get("", manifestationControler.findOneManifestation);
									
					delete("", manifestationControler.deleteOneManifestation); // req admin
					patch("", manifestationControler.editOneManifestation); // req salesman or admin
					
					path("/tickets",()->{
//						before("*",UserController.authenticateUser); // all ticket paths require login

						get("", ticketController.findAllTicketsForManifestation); // req salesman					
						path("/:idt", ()->{
							get("", ticketController.findOneTicket); // TODO req admin or salesman
							delete("", ticketController.deleteOneTicket); // TODO req admin
//							put("", ticketController.editOneTicket); // TODO req admin or user who owns the ticket
						});
					});
					
					path("/comments",()->{
//						before("*",UserController.authenticateUser); // all ticket paths require login

						get("", manifestationControler.findAllCommentsFromManifestation); // req salesman	
						post("", manifestationControler.addOneComment);
						path("/:idc", ()->{
							get("", manifestationControler.findOneComment); // req salesman	
							delete("", manifestationControler.deleteManifestationComment);
						});

					});
					
					//  api/manifestation/:idm/reserve
					path("/reserve", ()->{
						post("", ticketController.reserveTicket); // req admin
					});
				});
			});
//			before("/users",UserController.authenticateUser); // all paths in manifestations are not allowed without login
			path("/users",()->{
				get("", userController.findAllUsers); // req admin
//				post("", userController.saveOneUser); // TODO this one is basically like register and should be removed
				
				path("/suspicious",()->{
					get("", userController.findAllSuspiciousCustomers);
				});
				
				path("/:idu",()->{
					get("", userController.findOneUser); // req admin
					delete("", userController.deleteOneUser); // req admin
//					put("", userController.editOneUser); // TODO req different things for different roles possibly
					patch("", userController.updateOneUser);
					path("/tickets",()->{
						get("", ticketController.findAllTicketsForUser); // TODO req admin					
						path("/:idt", ()->{
							get("", ticketController.findOneTicket); // TODO req admin or salesman
							delete("", ticketController.deleteOneTicket);
//							put("", ticketController.editOneTicket); // TODO req admin or user who owns the ticket
						});
					});		

					path("/manifestations",()->{
						get("", manifestationControler.findAllManifestationsForSalesman);
					});
					path("/manif-tickets",()->{
						get("", ticketController.findReserverTicketsForSalesmanManifestation);
					});
					path("/sold-to-users",()->{
						get("", userController.findUsersFromSalesmanTickets);
					});
					
					path("/change-password",()->{
						patch("", userController.changePassword);				
					});
					path("/block", ()->{
						patch("", userController.blockUser);
					});
					path("/unblock", ()->{
						patch("", userController.unblockUser);
					});
					
				});	
				
			});
			
			path("/manifestation-type",()->{
				get("",manifestationControler.findAllManifestationTypes);
				post("", manifestationControler.addOneManifestationType);
	
				path("/:idmt", ()->{
					get("", manifestationControler.findOneManifestationType);
					put("", manifestationControler.putOneManifestationType);
					delete("", manifestationControler.deleteOneManifestationType);
				});
			});
			
			path("/customer-type",()->{
				get("",userController.findAllCustomerTypes);
				post("", userController.addOneCustomerType);
				path("/:idct", ()->{
					get("", userController.findOneCustomerType);
					put("", userController.putOneCustomerType);
					delete("", userController.deleteOneCustomerType);
				});
			});
			
			path("/tickets",()->{
				get("",ticketController.findAllTickets); // TODO req admin
				path("/:idt",()->{
					path("/cancel",()->{
						patch("",ticketController.cancelOneTicket); // TODO req admin
					});				
				});
			});
			
			path("/comments",()->{
				path("/:idc",()->{
					patch("", manifestationControler.editOneComment);
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
