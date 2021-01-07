package web.controller;
import static spark.Spark.get;

import java.net.HttpURLConnection;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import model.Manifestation;
import service.ManifestationService;
import service.implementation.ManifestationDao;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteImpl;
import support.FileToJsonAdapter;
import support.JsonToFileAdapter;

import static spark.Spark.path;


public class ManifestationControler {

	public static final ManifestationService manifService = new ManifestationDao();

	
	public ManifestationControler() {
		
		/**
		 * Regular way to write paths in controllers by using lambda functions
		 * Tradeoff is that you have to write the full path
		 */
		path("/manifestations",()->{
			get("",(req,res)->{
				
				res.type("application/json");
				Collection<Manifestation> mans = manifService.findAll();
				System.out.println(mans);
				return new Gson().toJson(mans);	
				
			});	
		});
	}
	
	/**
	 * Define a static attribute in this way so it can be used with paths in main
	 */
	public static final Route findAllManifestations = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			// No login needed for this request.
			res.type("application/json");
			Collection<Manifestation> foundEntities = manifService.findAll();
			System.out.println(foundEntities);
			return new Gson().toJson(foundEntities);	
		}
	};
	
	
	public static final Route findOneManifestation = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			res.type("application/json");
			String id = req.params("idm");
			Manifestation manif = manifService.findOne(id);
			System.out.println(manif);
			return new Gson().toJson(manif);
		}
	};
	
	public static final Route saveOneManifestation = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			res.type("application/json");
			// TODO Add adapters so there are no warnings

			
			String body = req.body();
			Manifestation manif = new Gson().fromJson(body, Manifestation.class);
			Manifestation saved = manifService.save(manif);
			return  new Gson().toJson(saved);
			
			
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
	
	
	public static final Route deleteOneManifestation = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			//TODO check if admin
			res.type("application/json");
			String id = req.params("idm");
			Manifestation deletedEntity = manifService.delete(id);
			if (deletedEntity == null) {
				return HttpStatus.NOT_FOUND_404;
			}
			System.out.println(deletedEntity);
//			return new Gson().toJson(deletedEntity); //for debuging with postman
			return HttpStatus.NO_CONTENT_204;
		}
	};
	
	public static final Route editOneManifestation = new Route() {
		
		@Override
		public Object handle(Request req, Response res) {
			res.type("application/json");
			String idm = req.params("idm");
			String body = req.body();
			System.out.println(idm);

			//TODO use one of these classes to return status codes
			int status = HttpServletResponse.SC_NOT_FOUND;
			int status2 = HttpURLConnection.HTTP_BAD_REQUEST;
			Manifestation newEntity = new Gson().fromJson(body, Manifestation.class);
			System.out.println(newEntity.getId());
			if (idm ==null || newEntity == null || !idm.equals(newEntity.getId())) {
				return HttpURLConnection.HTTP_BAD_REQUEST;
			}
						
			Manifestation saved = manifService.save(newEntity);
			
			System.out.println(newEntity);
			return new Gson().toJson(saved);
		}
	};
	
	
}
