package web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import model.Ticket;
import service.TicketService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.http.matching.Halt;
import support.JsonAdapter;
import web.dto.ManifestationSearchDTO;
import web.dto.TicketSearchDTO;

import static spark.Spark.*;

public class TicketController {

	private TicketService ticketService;
	private Gson gson;
	private UserController userController;
	
	public TicketController(TicketService ticketService, UserController uCtrl) {
		super();
		this.ticketService = ticketService;
		this.gson = JsonAdapter.ticketsSeraialization();
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

			// ManifestationSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams),
			// ManifestationSearchDTO.class);

			// System.out.println("[DBG] searchParamsDTO" + searchParams);

			// Collection<Ticket> foundEntities = ticketService.search(searchParams);
			Collection<Ticket> foundEntities = ticketService.findAll();

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			// TODO consider using an adapter
			// TODO use DTO objects

			return new Gson().toJson(foundEntities);
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
			return new Gson().toJson(foundEntity);
		}
	};

	public final Route findAllTicketsForUser = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO login needed for this request.
			// TODO add DTO for search and filter parameters. Call search or find all
			// function depending on parameters
			// TODO add pagination
			res.type("application/json");

			final Map<String, String> queryParams = new HashMap<>();
			req.queryMap().toMap().forEach((k, v) -> {
				queryParams.put(k, v[0]);
			});
			String user = req.params("idu");
			System.out.println("User: " + user);

			TicketSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams), TicketSearchDTO.class);

			// System.out.println("[DBG] searchParamsDTO" + searchParams);

			Collection<Ticket> foundEntities = ticketService.searchByUser(user, searchParams);
			// Collection<Ticket> foundEntities = ticketService.findAll();

			if (foundEntities == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}

			// TODO consider using an adapter
			// TODO use DTO objects

			return JsonAdapter.ticketsSeraialization().toJson(foundEntities);
		}
	};

	public final Route deleteOneTicket = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);

			res.type("application/json");
			String id = req.params("idt");
			Ticket deletedEntity = ticketService.findOne(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "not found");
			}

			return HttpStatus.NO_CONTENT_204;
		}
	};
	
	public final Route findReserverTicketsForSalesmanManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination
			
			userController.authenticateSalesman.handle(req, res);
			
			res.type("application/json");
			String idu = req.params("idu");
		    
			
		
			Collection<Ticket> foundEntities = ticketService.findAllBySalesman(idu);
			if (foundEntities==null || foundEntities.isEmpty()) {
				halt(HttpStatus.NOT_FOUND_404,"No tickets found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return gson.toJson(foundEntities);
		}
	};

}
