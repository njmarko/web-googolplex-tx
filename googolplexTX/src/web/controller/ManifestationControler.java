package web.controller;

import static spark.Spark.*;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import service.ManifestationService;
import service.implementation.ManifestationServiceImpl;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteImpl;
import support.JsonAdapter;
import web.dto.ManifestationDTO;
import web.dto.ManifestationSearchDTO;



public class ManifestationControler {

	private ManifestationService manifService;

	// TODO consider if empty constructor is needed
	
	public ManifestationControler(ManifestationService manifService) {
		super();
		this.manifService = manifService;
	}

//	public ManifestationControler() {
//		
//		/**
//		 * Regular way to write paths in controllers by using lambda functions
//		 * Tradeoff is that you have to write the full path
//		 */
//		path("/manifestations",()->{
//			get("",(req,res)->{
//				
//				res.type("application/json");
//				Collection<Manifestation> mans = manifService.findAll();
//				System.out.println(mans);
//				return new Gson().toJson(mans);	
//				
//			});	
//		});
//	}

	public final Route findAllManifestations = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// No login needed for this request.
			// TODO add DTO for search and filter parameters
			// TODO add pagination
			res.type("application/json");
				
			final Map<String, String> queryParams = new HashMap<>();
		    req.queryMap().toMap().forEach((k, v) -> {
		      queryParams.put(k, v[0]);
		    });
		    
		    Gson gson = new Gson();
	
			ManifestationSearchDTO searchParams = gson.fromJson(gson.toJson(queryParams), ManifestationSearchDTO.class);
			
			System.out.println("[DBG] searchParamsDTO" + searchParams);
		
			
//			Collection<Manifestation> foundEntities = manifService.findAll();
			Collection<Manifestation> foundEntities = manifService.search(searchParams);
			if (foundEntities==null) {
				halt(HttpStatus.NOT_FOUND_404,"No manifestations found");
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
			String id = req.params("idm");
			Manifestation foundEntity = manifService.findOne(id);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestation found");
			}
			// TODO Since it contains date consider using adapters. Replace with DTO if
			// needed
			return new Gson().toJson(foundEntity);
		}
	};

	public final Route saveOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			res.type("application/json");
			// TODO Add adapters so there are no warnings

			// TODO check if admin or salesman
			String body = req.body();
			// TODO replace with DTO if needed and use adapters to awoid warnings
			Manifestation manif = new Gson().fromJson(body, Manifestation.class);
			Manifestation savedEntity = manifService.save(manif);
			if (savedEntity == null) {
				halt(HttpStatus.BAD_REQUEST_400);
			}
			return new Gson().toJson(savedEntity);
			
////			Example with adapter
//			String body = req.body();
//			Gson g= FileToJsonAdapter.manifestationsSerializationFromFile();
//			System.out.println("Jel se pre buni");
//			Manifestation manif = g.fromJson(body, Manifestation.class);
//			System.out.println("Ili se posle buni");
//			Manifestation saved = manifService.save(manif);
//			System.out.println(saved);
//			return  JsonToFileAdapter.manifestationSeraialization().toJson(saved);
		}
	};

	public final Route deleteOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO check if admin

			// res.type("application/json");
			String id = req.params("idm");
			Manifestation deletedEntity = manifService.delete(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}
			System.out.println(deletedEntity);
//			return new Gson().toJson(deletedEntity); //for debuging with postman
			return HttpStatus.NO_CONTENT_204;
		}
	};

	public final Route editOneManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// TODO check if admin or salesman
			res.type("application/json");
			String idm = req.params("idm");
			String body = req.body();
			System.out.println(idm);

			// TODO consider using adapters to awoid warnings
			Manifestation newEntity = new Gson().fromJson(body, Manifestation.class);
			System.out.println(newEntity.getId());
			if (idm == null || newEntity == null || !idm.equals(newEntity.getId())) {
				halt(HttpStatus.BAD_REQUEST_400);
			}

			Manifestation savedEntity = manifService.save(newEntity);

			return new Gson().toJson(savedEntity);
		}
	};

}
