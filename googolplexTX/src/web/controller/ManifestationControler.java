package web.controller;
import static spark.Spark.get;

import java.util.Collection;

import com.google.gson.Gson;

import model.Manifestation;
import service.ManifestationService;
import service.implementation.ManifestationDao;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteImpl;
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
			res.type("application/json");
			Collection<Manifestation> mans = manifService.findAll();
			System.out.println(mans);
			return new Gson().toJson(mans);	
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
	
	
}
