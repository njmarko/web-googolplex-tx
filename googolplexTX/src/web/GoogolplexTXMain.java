package web;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import model.Address;
import model.Comment;
import model.Customer;
import model.CustomerType;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.InMemoryRepository;
import service.implementation.ManifestationDao;
import support.JsonToFileAdapter;

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
		CustomerType ct1 = new CustomerType("Neki", 5.0, 232.0, false);
		Customer c1 = new Customer("micko123", "pass123", "namemica", "lepimica", Gender.MALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, 0, ct1);

		Address adrs = new Address("123", "Novi Sad", 123, "Cika Perina");
		Location loc = new Location(123.3, 444.3, adrs);
		Salesman sal = new Salesman("Pera", "pera123", "Perafirst", "peraLast", Gender.MALE, LocalDate.now(),
				UserRole.SALESMAN, false, false);
		ManifestationType mt = new ManifestationType("Threatre");
		Manifestation m1 = new Manifestation("1234", "Man1", 123, LocalDateTime.now(), 123.3,
				ManifestationStatus.ACTIVE, "poster", false, mt, sal, loc);
		Ticket t1 = new Ticket("1", LocalDateTime.now(), 242.42, "CstName", TicketType.REGULAR, TicketStatus.RESERVED,
				null, false, c1, m1);
		m1.getTickets().add(t1);
		c1.getTickets().add(t1);
		sal.getManifestation().add(m1);
		
		Comment comm1 = new Comment("1001", "Tekst", 3, m1, c1);
		m1.getComments().add(comm1);
		c1.getComments().add(comm1);
		
		/*
		 * Ignore the illegal reflective access operation warning for now
		 */

		Collection<Salesman> prodavci = new ArrayList<Salesman>();
		prodavci.add(sal);

		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(new ArrayList<Customer>().getClass(),
				JsonToFileAdapter.serializeSalesmanCollection);
		Gson customGson = gsonBuilder.create();
		String customJSON = customGson.toJson(prodavci);
		System.out.println(customJSON);

		manifestationService.save(m1);

		InMemoryRepository.loadData();
			
//		InMemoryRepository.save(t1);
//		InMemoryRepository.save(sal);
//		InMemoryRepository.save(c1);
//		InMemoryRepository.save(ct1);
//		InMemoryRepository.save(mt);
//		InMemoryRepository.save(comm1);
		InMemoryRepository.findOneCustomerType("Neki").setName("Svaki");
		
		InMemoryRepository.saveManifestations();
		InMemoryRepository.saveTickets();
		InMemoryRepository.saveUsers();
		InMemoryRepository.saveComments();
		InMemoryRepository.saveCustomerTypes();
		InMemoryRepository.saveManifestationTypes();
		// manifestationService.load();

		
		
		
		

		TestData td = new TestData();
		td.createTestData();
		
		
		

	}

}
