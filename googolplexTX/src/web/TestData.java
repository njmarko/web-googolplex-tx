package web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Comment;
import model.Customer;
import model.CustomerType;
import model.Location;
import model.Manifestation;
import model.ManifestationType;
import model.Salesman;
import model.Ticket;
import model.User;
import model.enumerations.Gender;
import model.enumerations.ManifestationStatus;
import model.enumerations.TicketStatus;
import model.enumerations.TicketType;
import model.enumerations.UserRole;
import repository.CommentDAO;
import repository.CustomerTypeDAO;
import repository.ManifestationDAO;
import repository.ManifestationTypeDAO;
import repository.TicketDAO;
import repository.UserDAO;

public class TestData {

	public static void createTestData(UserDAO userDAO, ManifestationDAO manifestationDAO, TicketDAO ticketDAO, CommentDAO commentDAO, ManifestationTypeDAO manifestationTypeDAO, CustomerTypeDAO customerTypeDAO) {
	
		
		// CUSTOMER TYPES---------------------------------------------------
		CustomerType custType0 = new CustomerType("Peasant", 3.0, 0d, false);
		CustomerType custType1 = new CustomerType("Bronze", 3.0, 1000.0, false);
		CustomerType custType2 = new CustomerType("Silver", 5.0, 2000.0, false);
		CustomerType custType3 = new CustomerType("Gold", 8.0, 3000.0, false);
		CustomerType custType4 = new CustomerType("NoCustomersOfThisType", 10.0, 10000.0, false); // This type should
																									// have
																									// no customers
		CustomerType custType5 = new CustomerType("DeletedCustType", 10.0, 10000.0, true);

		// saving TODO add saving for customer types
		// InMemoryRepository.save

		// CUSTOMERS---------------------------------------------------
		Customer cust1 = new Customer("pera", "pera", "Pera", "Peric", Gender.MALE, LocalDate.now(), UserRole.CUSTOMER,
				false, false, 322, custType0);

		Customer cust2 = new Customer("mika", "mika", "Mika", "Mikic", Gender.MALE, LocalDate.now(), UserRole.CUSTOMER,
				false, false, 1320, custType1);

		Customer cust3 = new Customer("ana", "ana", "Ana", "Anicic", Gender.FEMALE, LocalDate.now(), UserRole.CUSTOMER,
				false, false, 2420, custType2);

		Customer cust4 = new Customer("marija", "marija", "Marija", "Marjanovic", Gender.FEMALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, 28, custType0);

		Customer cust5 = new Customer("jelena", "jelena", "Jelena", "Jelencic", Gender.FEMALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, -1, custType0);

		Customer cust6 = new Customer("branka", "branka", "Branka", "Brankovic", Gender.FEMALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, 5400, custType3);

		Customer cust7 = new Customer("steva", "steva", "Steva", "Stevic", Gender.MALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, 3000, custType3);

		Customer cust8 = new Customer("milica", "milica", "Milica", "Milicic", Gender.FEMALE, LocalDate.now(),
				UserRole.CUSTOMER, false, false, 3000.1, custType3);

		Customer cust9 = new Customer("deleted_cust", "deleted_cust", "Deleted_cust", "Deleted_cust", Gender.FEMALE,
				LocalDate.now(), UserRole.CUSTOMER, false, true, 3030.2, custType3);

		Customer cust10 = new Customer("blocked_cust", "deleted_cust", "Blocked_cust_First", "Blocked_cust_Last",
				Gender.FEMALE, LocalDate.now(), UserRole.CUSTOMER, true, false, 3400.3, custType3); // blocked



		// SALESPEOPLE---------------------------------------------------
		Salesman sal1 = new Salesman("prodavac1", "prodavac1", "Prodavac1First", "Prodavac1Last", Gender.MALE,
				LocalDate.now(), UserRole.SALESMAN, false, false);

		Salesman sal2 = new Salesman("prodavac2", "prodavac2", "Prodavac2First", "Prodavac2Last", Gender.MALE,
				LocalDate.now(), UserRole.SALESMAN, false, false);

		Salesman sal3 = new Salesman("prodavac3NoManifestations", "prodavac3NoManifestations", "Prodavac3First",
				"Prodavac3Last", Gender.FEMALE, LocalDate.now(), UserRole.SALESMAN, false, false);

		Salesman sal4 = new Salesman("prodavac4", "prodavac4", "Prodavac4First", "Prodavac4Last", Gender.FEMALE,
				LocalDate.now(), UserRole.SALESMAN, false, false); // This saleseman should not have any manifestations

		Salesman sal5 = new Salesman("deleted_sal", "deleted_sal", "deleted_sal_First", "deleted_sal_Last",
				Gender.FEMALE, LocalDate.now(), UserRole.SALESMAN, false, true); // Deleted

		Salesman sal6 = new Salesman("blocked_sal", "blocked_sal", "blocked_sal_First", "blocked_sal_Last",
				Gender.FEMALE, LocalDate.now(), UserRole.SALESMAN, true, false); // Blocked



		// MANIFESTATION TYPES---------------------------------------------------
		ManifestationType mt1 = new ManifestationType("Threatre"); // I have created combination of active and inactive
																	// manifestations for this
		ManifestationType mt2 = new ManifestationType("Cinema"); // I have created only inactive manifestations for this
																	// type
		ManifestationType mt3 = new ManifestationType("Festival"); // I have created only active manifestatiosn for this
																	// type
		ManifestationType mt4 = new ManifestationType("NoManifestationsType"); // No manifestations should be created
																				// for this type
		ManifestationType mt5 = new ManifestationType("DeletedManifestationType"); // Deleted Manifestation types

		// Saving
		// TODO implement saving of manifestation types in memory repository

		// MANIFESTATIONS WITH LOCATIONS AND ADDRESSES---------------------------

		Location loc1 = new Location(111.3, 1111.3,"1", "Novi Sad", 21000, "Cika Perina");
		Manifestation man1 = new Manifestation("1111", "Man1", 123, LocalDateTime.now(), 123.3,
				ManifestationStatus.ACTIVE, "poster", false, mt1, sal1, loc1);

		Location loc2 = new Location(222.3, 2222.3,"2", "Novi Sad", 21000, "Cika Perina"); // different number compared to adrs1
		Manifestation man2 = new Manifestation("2222", "Man2NegativeValsInactive", -3, LocalDateTime.now(), -1d, // Intentionally
																													// set
																													// this
				// to negative values
				ManifestationStatus.INACTIVE, "poster", false, mt2, sal1, loc2);

		
		Location loc3 = new Location(333.3, 3333.3,"33", "Novi Sad", 21000, "Bulevar Oslobodjenja");
		Manifestation man3 = new Manifestation("3333", "Man3Inactive", 5, LocalDateTime.now(), 11d,
				ManifestationStatus.INACTIVE, "poster", false, mt1, sal1, loc3);

		Location loc4 = new Location(444.3, 4444.3,"44", "Beograd", 11000, "Marka Kraljevica");
		Manifestation man4 = new Manifestation("4444", "Man4Deleted", 51, LocalDateTime.now(), 33.3d,
				ManifestationStatus.ACTIVE, "poster", true, mt1, sal2, loc4); // deleted

		Location loc5 = new Location(444.3, 4444.3, "44", "Beograd", 11000, "Marka Kraljevica");
		Manifestation man5 = new Manifestation("5555", "Man5SameLocationAndAddressAsDeleted", 51, LocalDateTime.now(),
				33.3d, ManifestationStatus.ACTIVE, "poster", false, mt1, sal2, loc5); // on the same location as deleted
																						// manifestation

		Location loc6 = new Location(444.3, 4444.3, "44", "Beograd", 11001, "Marka Kraljevica"); // same coordinates as previous // different zip code, everything else
		// is
		// same
		Manifestation man6 = new Manifestation("6666", "Man6SameLocationDifferentZip", 17, LocalDateTime.now(), 22.3d,
				ManifestationStatus.ACTIVE, "poster", false, mt1, sal2, loc5); // on the same location as deleted
																				// manifestation

		Location loc7 = new Location(777.3, 7777.3, "11a", "Prigrevica", 25263, "Apatinska");
		Manifestation man7 = new Manifestation("7777", "Man7Inactive", 1000, LocalDateTime.now(), 550d,
				ManifestationStatus.INACTIVE, "poster", false, mt2, sal3, loc7);

		Location loc8 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Apatinska"); // Only city is different in the address
		Manifestation man8 = new Manifestation("8888", "Man8InAddressOnlyCityDifferent", 15, LocalDateTime.now(), 333d,
				ManifestationStatus.ACTIVE, "poster", false, mt3, sal3, loc8);

		Location loc9 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Apatinska"); // Only city is different in the address
		Manifestation man9 = new Manifestation("9999", "Man9InAddressOnlyCityDifferent", 71, LocalDateTime.now(), 21d,
				ManifestationStatus.ACTIVE, "poster", false, mt3, sal3, loc9);

		Location loc10 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Kicoska"); // Only Street is different in the address
		Manifestation man10 = new Manifestation("10101010", "Man10InAddressOnlyCityDifferentDELETED", 332,
				LocalDateTime.now(), 0.01d, ManifestationStatus.ACTIVE, "poster", true, mt3, sal3, loc10); // active but
																											// deleted


		// TICKETS---------------------------------------------------

		// For customer 1
		Ticket t1 = new Ticket("1", LocalDateTime.now(), 242.42, TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust1, man1);

		Ticket t2 = new Ticket("2", LocalDateTime.now(), 242.42, TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust1, man1);

		Ticket t3 = new Ticket("3", LocalDateTime.now(), 484.84d,  TicketType.FAN_PIT,
				TicketStatus.RESERVED, null, false, cust1, man1);

		Ticket t4 = new Ticket("4", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.CANCELED, null, false, cust1, man1);

		Ticket t5 = new Ticket("5", LocalDateTime.now(), 969.68d,  TicketType.VIP,
				TicketStatus.RESERVED, null, true, cust1, man1); // deleted reserved ticket

		Ticket t6 = new Ticket("6", LocalDateTime.now(), 160d,  TicketType.VIP,
				TicketStatus.CANCELED, null, false, cust1, man4); // references a deleted manifestation and is also
																	// canceled

		Ticket t7 = new Ticket("7", LocalDateTime.now(), 160d,  TicketType.VIP,
				TicketStatus.RESERVED, null, false, cust1, man10); // references a deleted manifestation and is reserved

		// for customer 2 same as first 4 tickets for cust 1
		Ticket t8 = new Ticket("8", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust2, man1);

		Ticket t9 = new Ticket("9", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust2, man1);

		Ticket t10 = new Ticket("10", LocalDateTime.now(), 484.84d,  TicketType.FAN_PIT,
				TicketStatus.RESERVED, null, false, cust2, man1);

		Ticket t11 = new Ticket("11", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.CANCELED, null, false, cust2, man1);

		// for customer 3 same as first 4 tickets for cust 1 and 2
		Ticket t12 = new Ticket("12", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust3, man1);

		Ticket t13 = new Ticket("13", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust3, man1);

		Ticket t14 = new Ticket("14", LocalDateTime.now(), 484.84d,  TicketType.FAN_PIT,
				TicketStatus.RESERVED, null, false, cust3, man1);

		Ticket t15 = new Ticket("15", LocalDateTime.now(), 242.42,  TicketType.REGULAR,
				TicketStatus.CANCELED, null, false, cust3, man1);

		// for customer 4 different event from cust 1,2 and 3
		Ticket t16 = new Ticket("16", LocalDateTime.now(), 100d,  TicketType.REGULAR,
				TicketStatus.RESERVED, null, false, cust4, man5);

		Ticket t17 = new Ticket("17", LocalDateTime.now(), 400d,  TicketType.VIP,
				TicketStatus.RESERVED, null, false, cust4, man5);

		Ticket t18 = new Ticket("18", LocalDateTime.now(), 200d,  TicketType.FAN_PIT,
				TicketStatus.RESERVED, null, false, cust4, man5);

		Ticket t19 = new Ticket("19", LocalDateTime.now(), 400d,  TicketType.VIP,
				TicketStatus.CANCELED, null, false, cust4, man5);

		
		// saving
		userDAO.save(cust1);
		userDAO.save(cust2);
		userDAO.save(cust3);
		userDAO.save(cust4);
		userDAO.save(cust5);
		userDAO.save(cust6);
		userDAO.save(cust7);
		userDAO.save(cust8);
		userDAO.save(cust9);
		userDAO.save(cust10);
		
		
		// saving
		userDAO.save(sal1);
		userDAO.save(sal2);
		userDAO.save(sal3);
		userDAO.save(sal4);
		userDAO.save(sal5);
		userDAO.save(sal6);
		
		
		// Saving
		manifestationDAO.save(man1);
		manifestationDAO.save(man2);
		manifestationDAO.save(man3);
		manifestationDAO.save(man4);
		manifestationDAO.save(man5);
		manifestationDAO.save(man6);
		manifestationDAO.save(man7);
		manifestationDAO.save(man8);
		manifestationDAO.save(man9);
		manifestationDAO.save(man10);
		
		// Saving
		
		ticketDAO.save(t1);
		ticketDAO.save(t2);
		ticketDAO.save(t3);
		ticketDAO.save(t4);
		ticketDAO.save(t5);
		ticketDAO.save(t6);
		ticketDAO.save(t7);
		ticketDAO.save(t8);
		ticketDAO.save(t9);
		ticketDAO.save(t10);
		ticketDAO.save(t11);
		ticketDAO.save(t12);
		ticketDAO.save(t13);
		ticketDAO.save(t14);
		ticketDAO.save(t15);
		ticketDAO.save(t16);
		ticketDAO.save(t17);
		ticketDAO.save(t18);
		ticketDAO.save(t19);
		
		
		for (Manifestation m : manifestationDAO.getManifestations().values()) {
			Salesman u = m.getSalesman();
			u.getManifestation().add(m);
		}
		
		for (Ticket ticket : ticketDAO.getTickets().values()) {
			Customer customer = ticket.customer;
			customer.getTickets().add(ticket);
		}
		
		for (Comment comment : commentDAO.getComments().values()) {
			Customer customer = comment.getCustomer();
			Manifestation manifestation = comment.getManifestation();
			customer.getComments().add(comment);
			manifestation.getComments().add(comment);
		}
		
		
		
		

	}

}
