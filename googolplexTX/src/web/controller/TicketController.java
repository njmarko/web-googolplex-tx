package web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import model.Ticket;
import model.User;
import model.enumerations.UserRole;
import service.TicketService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.http.matching.Halt;
import support.JsonAdapter;
import support.TicketToTicketDTO;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;
import web.dto.ReservationDTO;
import web.dto.TicketDTO;
import web.dto.TicketSearchDTO;
import web.dto.UserSearchDTO;

import static spark.Spark.*;

public class TicketController {

	private TicketService ticketService;
	private Gson gson;
	private UserController userController;
	
	public TicketController(TicketService ticketService, UserController uCtrl) {
		super();
		this.ticketService = ticketService;
//		this.gson = JsonAdapter.ticketsSeraialization();
		this.gson = new Gson();
		this.userController = uCtrl;
	}

	public final Route findAllTicketsForManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO login needed for this request.
			// TODO add DTO for search and filter parameters. Call search or find all
			// function depending on parameters
			// TODO add pagination
			res.type("application/json");
			String idm = req.params("idm");

			// ManifestationSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams),
			// ManifestationSearchDTO.class);

			// System.out.println("[DBG] searchParamsDTO" + searchParams);

			// Collection<Ticket> foundEntities = ticketService.search(searchParams);
			
			// TODO returns deleted??
			Collection<Ticket> foundEntities = ticketService.findAllByManifestation(idm);

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			// TODO consider using an adapter
			// TODO use DTO objects

			
			return gson.toJson(TicketToTicketDTO.convert(foundEntities));
		}
	};

	public final Route findOneTicket = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO Consider if user has to be logged in

			res.type("application/json");
			String id = req.params("idt");
			Ticket foundEntity = ticketService.findOne(id);
			if (foundEntity == null) {
				// TODO replace with HALT function to handle error codes
				halt(HttpStatus.NOT_FOUND_404, "not found");
			}
			// TODO Since it contains date consider using adapters.
			// TODO Replace with DTO if needed
			return new Gson().toJson(TicketToTicketDTO.convert(foundEntity));
		}
	};

	public final Route findAllTicketsForUser = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO add DTO for search and filter parameters. Call search or find all
			// function depending on parameters
			// TODO add pagination
			res.type("application/json");
			User loggedIn = userController.getAuthedUser(req);
			System.out.println(loggedIn);
			userController.authenticateUser.handle(req, res);

			if (loggedIn.getUserRole() != UserRole.CUSTOMER && loggedIn.getUserRole() != UserRole.ADMIN) {
				halt(HttpStatus.FORBIDDEN_403, "Only customer or admin can view customer tickets");
			}
			
			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});
			String user = req.params("idu");
			System.out.println("User: " + user);

			TicketSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams), TicketSearchDTO.class);

			Collection<Ticket> foundEntities = ticketService.search(user, searchParams);

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return gson.toJson(TicketToTicketDTO.convert(foundEntities));
		}
	};
	
	
	public final Route findAllTickets = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO add DTO for search and filter parameters. Call search or find all
			// function depending on parameters
			// TODO add pagination
			res.type("application/json");
			User loggedIn = userController.getAuthedUser(req);
			System.out.println(loggedIn);
			userController.authenticateAdmin.handle(req, res);
			
			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});

			TicketSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams), TicketSearchDTO.class);

			Collection<Ticket> foundEntities = ticketService.search(null, searchParams);

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return gson.toJson(TicketToTicketDTO.convert(foundEntities));
		}
	};

	public final Route deleteOneTicket = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);

			res.type("application/json");
			String id = req.params("idt");
			Ticket deletedEntity = ticketService.delete(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "not found");
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};
	
	public final Route cancelOneTicket = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateUser.handle(req, res);
			User user = userController.getAuthedUser(req);

			
			res.type("application/json");
			String id = req.params("idt");
			Ticket entity = ticketService.findOne(id);
			if (entity == null) {
				halt(HttpStatus.NOT_FOUND_404, "not found");
			}
			if (!entity.getCustomer().getUsername().equals(user.getUsername())) {
				halt(HttpStatus.BAD_REQUEST_400, "Only customer can cancel this manifestation");
			}

			try {
				entity = ticketService.cancelTicket(id);
			} catch (IllegalArgumentException e) {
				halt(HttpStatus.BAD_REQUEST_400, e.getMessage());
			}
			
			
			return gson.toJson(TicketToTicketDTO.convert(entity));
		}
	};
	
	public final Route findReserverTicketsForSalesmanManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination
			
			userController.authenticateSalesmanOrAdmin.handle(req, res);
			
			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});
			System.out.println(gson.toJson(queryParams));
			TicketSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams), TicketSearchDTO.class);
			
			System.err.println(searchParams);
			
			res.type("application/json");
			String idu = req.params("idu");
		    
			User user = userController.getAuthedUser(req);
			if (user.getUserRole() == UserRole.SALESMAN && user.getUsername().compareTo(idu)!= 0) {
				halt(HttpStatus.BAD_REQUEST_400, "Salesman can only view tickets for his own manifestations");
			}
		
			Collection<Ticket> foundEntities = ticketService.findAllBySalesman(idu, searchParams);
			if (foundEntities==null) {
				halt(HttpStatus.NOT_FOUND_404,"No tickets found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return gson.toJson(TicketToTicketDTO.convert(foundEntities));
		}
	};
	
	public final Route reserveTicket = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			res.type("application/json");
			
			userController.authenticateUser.handle(req, res);
			User loggedIn = userController.getAuthedUser(req);

			if (loggedIn.getUserRole() != UserRole.CUSTOMER) {
				halt(HttpStatus.FORBIDDEN_403, "Only customer or admin can view customer tickets");
			}
			
			String body = req.body();
			ReservationDTO reservationParams = gson.fromJson(body, ReservationDTO.class);
			
			String manifestation = req.params("idm");
			reservationParams.setManifestation(manifestation);
			
			Collection<Ticket> entities = null;
			try {
				entities = ticketService.reserve(reservationParams);
			} catch (IllegalArgumentException e) {
				halt(HttpStatus.BAD_REQUEST_400, e.getMessage());
			}

			if (entities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			return gson.toJson(TicketToTicketDTO.convert(entities));
		}
	};
	

}
