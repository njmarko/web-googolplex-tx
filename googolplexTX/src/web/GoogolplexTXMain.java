package web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Address;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.UserRole;
import service.implementation.ManifestationDao;

public class GoogolplexTXMain {

	public static ManifestationDao manifestationService = new ManifestationDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World!");

		/*
		 * Simple test case for saving manifestation with nested objects Code should be
		 * adjusted so it only saves the id's of the collections within it. Gson can
		 * later convert these id's to customer objects after loading
		 */

		Address adrs = new Address(123, "Novi Sad", 123, "Cika Perina");
		Location loc = new Location(123.3, 444.3, adrs);
		Salesman sal = new Salesman("Pera", "pera123", "Perafirst", "peraLast", Gender.MALE, LocalDate.now(),
				UserRole.SALESMAN, false, false);
		ManifestationType mt = new ManifestationType("Threatre");
		Manifestation m1 = new Manifestation("1234", "Man1", 123, LocalDateTime.now(), 123.3,
				ManifestationStatus.ACTIVE, "poster", false, mt, sal, loc);
		
		

		/*
		 * Ignore the illegal reflective access operation warning for now
		 */
		manifestationService.save(m1);
		// manifestationService.load();

		System.out.println(manifestationService.findOne(m1.getId()));
	}

}
