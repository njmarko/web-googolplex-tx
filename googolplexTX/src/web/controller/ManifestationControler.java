package web.controller;
import static spark.Spark.get;

import java.util.Collection;

import com.google.gson.Gson;

import model.Manifestation;
import service.ManifestationService;


public class ManifestationControler {

	
	public ManifestationControler(final ManifestationService manifestationService) {
		
		get("/manifestations",(req,res)->{
			
			res.type("application/json");
			Collection<Manifestation> mans = manifestationService.findAll();
			System.out.println(mans);
			return new Gson().toJson(mans);	
			
		});
		
	}
	
}
