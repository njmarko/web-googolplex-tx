package web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Address;
import model.Customer;
import model.CustomerType;
import model.Location;
import model.Manifestation;
import model.Salesman;
import model.Ticket;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.InMemoryRepository;
import service.implementation.ManifestationDao;
import support.JsonAdapterUtil;

public class GoogolplexTXMain {

	public static ManifestationDao manifestationService = new ManifestationDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World!");

		/*
		 * Simple test case for saving manifestation with nested objects
		 * Code should be adjusted so it only saves the id's of the collections within it.
		 * Gson can later convert these id's to customer objects after loading
		 */
		
		Customer c1 = new Customer(0, new CustomerType("Neki", 5.0, 232.0, false));
		c1.setBirthDate(LocalDate.now());
		c1.setDeleted(false);
		c1.setFirstName("mico");
		c1.setLastName("vucina");
		c1.setGender(Gender.MALE);
		c1.setPassword("sifra2");
		c1.setUsername("mixy");
		
		
		Address adrs = new Address(123, "Novi Sad", 123, "Cika Perina");
		Location loc = new Location(123.3, 444.3, adrs);
		Salesman sal = new Salesman("Pera", "pera123", "Perafirst", "peraLast", Gender.MALE, LocalDate.now(),
				UserRole.SALESMAN, false);
		
		Salesman sal2 = new Salesman("Mika", "mika123", "MikaF", "MikaLast", Gender.MALE, LocalDate.now(),
				UserRole.SALESMAN, false);
		Manifestation m1 = new Manifestation("1234", "Man1", 123, LocalDateTime.now(), 123.3,
				ManifestationStatus.ACTIVE, "asdf", false, sal, loc);
		Ticket t1 = new Ticket("1", LocalDateTime.now(), 999.2, TicketType.REGULAR, false, c1);
		
		/*
		 * Ignore the illegal reflective access operation warning for now
		 */
		
		Collection<Salesman> prodavci = new ArrayList<Salesman>();
		prodavci.add(sal);
		prodavci.add(sal2);
		
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter(new ArrayList<Customer>().getClass(), JsonAdapterUtil.serializeSalesmanCollection);
		Gson customGson = gsonBuilder.create();  
		String customJSON = customGson.toJson(prodavci);  
		System.out.println(customJSON);
		
		
		
		
		
		
		manifestationService.save(m1);
		
	
		
		InMemoryRepository.saveManifestations();
		InMemoryRepository.save(t1);
		InMemoryRepository.saveTickets();
		//manifestationService.load();

		System.out.println(manifestationService.findOne(m1.getId()));
	}

}
