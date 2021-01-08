package web.controller;

import java.util.Collection;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import model.Ticket;
import service.TicketService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.http.matching.Halt;

import static spark.Spark.*;

public class TicketController {

	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}
	
	public final Route findAllTicketsForManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO login needed for this request.
			// TODO add DTO for search and filter parameters. Call search or find all function depending on parameters
			// TODO add pagination
			res.type("application/json");
			Collection<Ticket> foundEntities = ticketService.findAll();
			
			if (foundEntities==null) {
				halt(HttpStatus.NOT_FOUND_404);
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			
			return new Gson().toJson(foundEntities);
		}
	};
	
	public final Route findOneManifestation = new Route() {


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
	

	
	
}
