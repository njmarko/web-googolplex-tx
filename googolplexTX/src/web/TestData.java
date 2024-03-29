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
import model.enumerations.CommentStatus;
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

	public static void createTestData(UserDAO userDAO, ManifestationDAO manifestationDAO, TicketDAO ticketDAO,
			CommentDAO commentDAO, ManifestationTypeDAO manifestationTypeDAO, CustomerTypeDAO customerTypeDAO) {

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

		// ADMINS---------------------------------------------------
		User admin1 = new User("admin", "admin", "adminFirst", "adminLast", Gender.MALE, LocalDate.now(),
				UserRole.ADMIN);

		User admin2 = new User("root", "root", "rootFirst", "rootLast", Gender.FEMALE, LocalDate.now(), UserRole.ADMIN);

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
		ManifestationType mt5 = new ManifestationType("DeletedManifestationType", true); // Deleted Manifestation types

		// Saving
		// TODO implement saving of manifestation types in memory repository

		// MANIFESTATIONS WITH LOCATIONS AND ADDRESSES---------------------------

		Location loc1 = new Location(111.3, 1111.3, "1", "Novi Sad", 21000, "Cika Perina");
		Manifestation man1 = new Manifestation("1111", "Man1", 123, LocalDateTime.of(2018, 12, 22, 5, 13), 123.3,
				ManifestationStatus.ACTIVE, "manif-1111.jpg", false, mt1, sal1, loc1);

		Location loc2 = new Location(222.3, 2222.3, "2", "Novi Sad", 21000, "Cika Perina"); // different number compared
																							// to adrs1
		Manifestation man2 = new Manifestation("2222", "Man2NegativeValsInactive", -3, LocalDateTime.of(2001, 12, 22, 5, 13), -1d, // Intentionally
																													// set
																													// this
				// to negative values
				ManifestationStatus.INACTIVE, "manif-2222.jpg", false, mt2, sal1, loc2);

		Location loc3 = new Location(333.3, 3333.3, "33", "Novi Sad", 21000, "Bulevar Oslobodjenja");
		Manifestation man3 = new Manifestation("3333", "Man3Inactive", 5, LocalDateTime.of(2011, 3, 22, 3, 54), 11d,
				ManifestationStatus.INACTIVE, "manif-3333.jpg", false, mt1, sal1, loc3);

		Location loc4 = new Location(444.3, 4444.3, "44", "Beograd", 11000, "Marka Kraljevica");
		Manifestation man4 = new Manifestation("4444", "Man4Deleted", 51, LocalDateTime.now(), 33.3d,
				ManifestationStatus.ACTIVE, "manif-4444.jpg", true, mt1, sal2, loc4); // deleted

		Location loc5 = new Location(444.3, 4444.3, "44", "Beograd", 11000, "Marka Kraljevica");
		Manifestation man5 = new Manifestation("5555", "Man5SameLocationAndAddressAsDeleted", 51, LocalDateTime.of(2012, 12, 3, 13, 22),
				33.3d, ManifestationStatus.ACTIVE, "manif-5555.jpg", false, mt1, sal2, loc5); // on the same location as deleted
																						// manifestation

		Location loc6 = new Location(444.3, 4444.3, "44", "Beograd", 11001, "Marka Kraljevica"); // same coordinates as
																									// previous //
																									// different zip
																									// code, everything
																									// else
		// is
		// same
		Manifestation man6 = new Manifestation("6666", "Man6SameLocationDifferentZip", 17, LocalDateTime.now().plusDays(20), 22.3d,
				ManifestationStatus.ACTIVE, "manif-6666.jpg", false, mt1, sal2, loc5); // on the same location as deleted
																				// manifestation

		Location loc7 = new Location(777.3, 7777.3, "11a", "Prigrevica", 25263, "Apatinska");
		Manifestation man7 = new Manifestation("7777", "Man7Inactive", 1000, LocalDateTime.now(), 550d,
				ManifestationStatus.INACTIVE, "manif-7777.jpg", false, mt2, sal3, loc7);

		Location loc8 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Apatinska"); // Only city is different
																								// in the address
		Manifestation man8 = new Manifestation("8888", "Man8InAddressOnlyCityDifferent", 15, LocalDateTime.now(), 333d,
				ManifestationStatus.ACTIVE, "manif-8888.jpg", false, mt3, sal3, loc8);

		Location loc9 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Apatinska"); // Only city is different
																								// in the address
		Manifestation man9 = new Manifestation("9999", "Man9InAddressOnlyCityDifferent", 71, LocalDateTime.now(), 21d,
				ManifestationStatus.ACTIVE, "manif-9999.jpg", false, mt3, sal3, loc9);

		Location loc10 = new Location(777.3, 7777.3, "11a", "Slepcevic", 25263, "Kicoska"); // Only Street is different
																							// in the address
		Manifestation man10 = new Manifestation("10101010", "Man10InAddressOnlyCityDifferentDELETED", 332,
				LocalDateTime.now().plusDays(10), 0.01d, ManifestationStatus.ACTIVE, "manif-10101010.jpg", true, mt3, sal3, loc10); // active but
																											// deleted

		// TICKETS---------------------------------------------------

		// For customer 1
		Ticket t1 = new Ticket("1", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null, false,
				cust1, man1);

		Ticket t2 = new Ticket("2", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null, false,
				cust1, man1);

		Ticket t3 = new Ticket("3", man1.getDateOfOccurence(), 484.84d, TicketType.FAN_PIT, TicketStatus.RESERVED, null,
				false, cust1, man1);

		Ticket t4 = new Ticket("4", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.CANCELED, LocalDateTime.parse("2020-01-10T00:00:00"), false,
				cust1, man1);

		Ticket t5 = new Ticket("5", man1.getDateOfOccurence(), 969.68d, TicketType.VIP, TicketStatus.RESERVED, null, true,
				cust1, man1); // deleted reserved ticket

		Ticket t6 = new Ticket("6", man4.getDateOfOccurence(), 160d, TicketType.VIP, TicketStatus.CANCELED, LocalDateTime.now().minusDays(10), false,
				cust1, man4); // references a deleted manifestation and is also
								// canceled

		Ticket t7 = new Ticket("7", man10.getDateOfOccurence(), 160d, TicketType.VIP, TicketStatus.RESERVED, null, false,
				cust1, man10); // references a deleted manifestation and is reserved

		// for customer 2 same as first 4 tickets for cust 1
		Ticket t8 = new Ticket("8", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null, false,
				cust2, man1);

		Ticket t9 = new Ticket("9", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null, false,
				cust2, man1);

		Ticket t10 = new Ticket("10", man1.getDateOfOccurence(), 484.84d, TicketType.FAN_PIT, TicketStatus.RESERVED, null,
				false, cust2, man1);

		Ticket t11 = new Ticket("11", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.CANCELED, LocalDateTime.now().minusDays(6),
				false, cust2, man1);

		// for customer 3 same as first 4 tickets for cust 1 and 2
		Ticket t12 = new Ticket("12", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null,
				false, cust3, man1);

		Ticket t13 = new Ticket("13", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.RESERVED, null,
				false, cust3, man1);

		Ticket t14 = new Ticket("14", man1.getDateOfOccurence(), 484.84d, TicketType.FAN_PIT, TicketStatus.RESERVED, null,
				false, cust3, man1);

		Ticket t15 = new Ticket("15", man1.getDateOfOccurence(), 242.42, TicketType.REGULAR, TicketStatus.CANCELED, LocalDateTime.now().minusDays(8),
				false, cust3, man1);

		// for customer 4 different event from cust 1,2 and 3
		Ticket t16 = new Ticket("16", man5.getDateOfOccurence(), 100d, TicketType.REGULAR, TicketStatus.RESERVED, null, false,
				cust4, man5);

		Ticket t17 = new Ticket("17", man5.getDateOfOccurence(), 400d, TicketType.VIP, TicketStatus.RESERVED, null, false,
				cust4, man5);

		Ticket t18 = new Ticket("18", man5.getDateOfOccurence(), 200d, TicketType.FAN_PIT, TicketStatus.RESERVED, null, false,
				cust4, man5);

		Ticket t19 = new Ticket("19", man5.getDateOfOccurence(), 400d, TicketType.VIP, TicketStatus.CANCELED, LocalDateTime.now().minusDays(10), false,
				cust4, man5);
		
		Ticket t20 = new Ticket("20", LocalDateTime.of(2001, 12, 22, 5, 13), 400d, TicketType.VIP, TicketStatus.RESERVED, null, false,
				cust4, man1);
		
		Ticket t21 = new Ticket("21", LocalDateTime.of(1992, 3, 13, 4, 22), 415d, TicketType.VIP, TicketStatus.RESERVED, null, false,
				cust4, man2);
		
		Ticket t22 = new Ticket("22", LocalDateTime.of(1995, 4, 12, 5, 11), 252.32, TicketType.REGULAR, TicketStatus.RESERVED, null,
				false, cust3, man5);


		// saving
		userDAO.save(admin1);
		userDAO.save(admin2);

		// Comments
		Comment c1 = new Comment("1", "Predobarr film", 5, CommentStatus.ACCEPTED, false, man1, cust1);
		Comment c2 = new Comment("2", "Jadnooo", 2, CommentStatus.ACCEPTED, false, man5, cust4);
		Comment c3 = new Comment("3", "Okej", 3, CommentStatus.ACCEPTED, false, man1, cust3);
		Comment c4 = new Comment("4", "Nije jos prihvacen (vidi ga samo admin i customer prodavac1)", 3, CommentStatus.PENDING, false, man1, cust1);
		Comment c5 = new Comment("5", "Obrisan", 3, CommentStatus.ACCEPTED, true, man1, cust1);
		Comment c6 = new Comment("6", "Odbijen (vidi ga samo admin i salesman prodavac1)", 3, CommentStatus.REJECTED, false, man1, cust1);
		Comment c7 = new Comment("7", "Nije jos prihvacen (vidi ga samo admin i salesman prodavac1)", 3, CommentStatus.PENDING, false, man1, cust3);
		Comment c8 = new Comment("8", "Odbijen (vidi ga samo admin i salesman prodavac1)", 3, CommentStatus.REJECTED, false, man1, cust3);
		Comment c9 = new Comment("7", "Nije jos prihvacen (vidi ga samo admin i salesman prodavac2)", 3, CommentStatus.PENDING, false, man5, cust3);
		
		
		// CustomerTypes
		customerTypeDAO.save(custType0);
		customerTypeDAO.save(custType1);
		customerTypeDAO.save(custType2);
		customerTypeDAO.save(custType3);
		customerTypeDAO.save(custType4);
		customerTypeDAO.save(custType5);

		// Manifestatoin Types
		manifestationTypeDAO.save(mt1);
		manifestationTypeDAO.save(mt2);
		manifestationTypeDAO.save(mt3);
		manifestationTypeDAO.save(mt4);
		manifestationTypeDAO.save(mt5);
		
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
		ticketDAO.save(t20);
		ticketDAO.save(t21);
		ticketDAO.save(t22);

		commentDAO.save(c1);
		commentDAO.save(c2);
		commentDAO.save(c3);
		commentDAO.save(c4);
		commentDAO.save(c5);
		commentDAO.save(c6);
		commentDAO.save(c7);
		commentDAO.save(c8);
		commentDAO.save(c9);
		
		
		
		for (Manifestation m : manifestationDAO.getManifestations().values()) {
			Salesman u = m.getSalesman();
			u.getManifestation().add(m);
		}

		for (Ticket ticket : ticketDAO.getTickets().values()) {
			Customer customer = ticket.customer;
			customer.getTickets().add(ticket);
			
			Manifestation manifestation = ticket.getManifestation();
			manifestation.getTickets().add(ticket);
			
		}

		for (Comment comment : commentDAO.getComments().values()) {
			Customer customer = comment.getCustomer();
			Manifestation manifestation = comment.getManifestation();
			customer.getComments().add(comment);
			manifestation.getComments().add(comment);
		}

	}

}
