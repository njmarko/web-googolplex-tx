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

import model.Comment;
import model.Manifestation;
import model.User;
import model.enumerations.UserRole;
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
	
	private Gson gManifAdapter;

	private UserController userController;
	
	// TODO consider if empty constructor is needed
	
	public ManifestationControler(ManifestationService manifService, UserController uCntr) {
		super();
		this.manifService = manifService;
		//this.g = JsonAdapter.manifestationSeraialization();
		this.gManifAdapter = JsonAdapter.manifestationSeraialization();
		this.userController = uCntr;
		
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
//				return g.toJson(mans);	
//				
//			});	
//		});
//	}

	public final Route findAllManifestations = new Route() {

		@Override
		public Object handle(Request req, Response res) {
			// No login needed for this request.
			// TODO add pagination
			res.type("application/json");
				
			final Map<String, String> queryParams = new HashMap<>();
		    req.queryMap().toMap().forEach((k, v) -> {
		      queryParams.put(k, v[0]);
		    });
		    
			ManifestationSearchDTO searchParams = gManifAdapter.fromJson(gManifAdapter.toJson(queryParams), ManifestationSearchDTO.class);
		    
			// TODO remove debug print message
			System.out.println("[DBG] searchParamsDTO" + searchParams);
		
			Collection<Manifestation> foundEntities = manifService.search(searchParams);
			if (foundEntities==null || foundEntities.isEmpty()) {
				halt(HttpStatus.NOT_FOUND_404,"No manifestations found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return gManifAdapter.toJson(foundEntities);
		}
	};

	public final Route findOneManifestation = new Route() {
		
		@Override
		public Object handle(Request req, Response res) throws Exception {
			// TODO Consider if user has to be logged in

			res.type("application/json");
			String id = req.params("idm");
			Manifestation foundEntity = manifService.findOne(id);
			if (foundEntity == null) {
				halt(HttpStatus.NOT_FOUND_404, "No manifestation found");
			}
			// TODO Since it contains date consider using adapters. Replace with DTO if
			// needed
			return gManifAdapter.toJson(foundEntity);
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
			Manifestation manif = gManifAdapter.fromJson(body, Manifestation.class);
			Manifestation savedEntity = manifService.save(manif);
			if (savedEntity == null) {
				halt(HttpStatus.BAD_REQUEST_400);
			}
			return gManifAdapter.toJson(savedEntity);
			
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
		public Object handle(Request req, Response res) throws Exception {
			userController.authenticateAdmin.handle(req, res);
			
			// res.type("application/json");
			String id = req.params("idm");
			Manifestation deletedEntity = manifService.delete(id);
			if (deletedEntity == null) {
				halt(HttpStatus.NOT_FOUND_404);
			}
			System.out.println(deletedEntity);
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
			Manifestation newEntity = gManifAdapter.fromJson(body, Manifestation.class);
			System.out.println(newEntity.getId());
			if (idm == null || newEntity == null || !idm.equals(newEntity.getId())) {
				halt(HttpStatus.BAD_REQUEST_400);
			}

			Manifestation savedEntity = manifService.save(newEntity);

			return gManifAdapter.toJson(savedEntity);
		}
	};
	
	public final Route findAllManifestationsForSalesman = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination
			
			userController.authenticateSalesman.handle(req, res);
			
			res.type("application/json");
			String idu = req.params("idu");
		    
			
		
			Collection<Manifestation> foundEntities = manifService.findBySalesman(idu);
			if (foundEntities==null || foundEntities.isEmpty()) {
				halt(HttpStatus.NOT_FOUND_404,"No manifestations found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return gManifAdapter.toJson(foundEntities);
		}
	};

	public final Route findAllCommentsFromManifestation = new Route() {

		@Override
		public Object handle(Request req, Response res) throws Exception {
			// No login needed for this request.
			// TODO add pagination
						
			res.type("application/json");
			String idm = req.params("idm");
		    
			
		
			Collection<Comment> foundEntities = manifService.findAllCommentsFromManifestation(idm);
			if (foundEntities==null) {
				halt(HttpStatus.NOT_FOUND_404,"No comments found");
			}
			
			// TODO consider using an adapter
			// TODO use DTO objects
			return JsonAdapter.commentsSerializationToFile().toJson(foundEntities);
		}
	};
	
}
