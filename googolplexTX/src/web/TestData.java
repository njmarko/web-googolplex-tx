package web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Address;
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

public class TestData {

	public void createTestData() {

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
		//InMemoryRepository.save
		

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
		
		//saving
		InMemoryRepository.save(cust1);
		InMemoryRepository.save(cust2);
		InMemoryRepository.save(cust3);
		InMemoryRepository.save(cust4);
		InMemoryRepository.save(cust5);
		InMemoryRepository.save(cust6);
		InMemoryRepository.save(cust7);
		InMemoryRepository.save(cust8);
		InMemoryRepository.save(cust9);
		InMemoryRepository.save(cust10);

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
		
		//saving
		InMemoryRepository.save(sal1);
		InMemoryRepository.save(sal2);
		InMemoryRepository.save(sal3);
		InMemoryRepository.save(sal4);
		InMemoryRepository.save(sal5);
		InMemoryRepository.save(sal6);

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
		Address adrs1 = new Address(1, "Novi Sad", 21000, "Cika Perina");
		Location loc1 = new Location(111.3, 1111.3, adrs1);
		Manifestation man1 = new Manifestation("1111", "Man1", 123, LocalDateTime.now(), 123.3,
				ManifestationStatus.ACTIVE, "poster", false, mt1, sal1, loc1);

		Address adrs2 = new Address(2, "Novi Sad", 21000, "Cika Perina"); // different number compared to adrs1
		Location loc2 = new Location(222.3, 2222.3, adrs2);
		Manifestation man2 = new Manifestation("2222", "Man2NegativeValsInactive", -3, LocalDateTime.now(), -1d, // Intentionally
																													// set
																													// this
				// to negative values
				ManifestationStatus.INACTIVE, "poster", false, mt2, sal1, loc2);

		Address adrs3 = new Address(33, "Novi Sad", 21000, "Bulevar Oslobodjenja");
		Location loc3 = new Location(333.3, 3333.3, adrs3);
		Manifestation man3 = new Manifestation("3333", "Man3Inactive", 5, LocalDateTime.now(), 11d,
				ManifestationStatus.INACTIVE, "poster", false, mt1, sal1, loc3);

		Address adrs4 = new Address(44, "Beograd", 11000, "Marka Kraljevica");
		Location loc4 = new Location(444.3, 4444.3, adrs4);
		Manifestation man4 = new Manifestation("4444", "Man4Deleted", 51, LocalDateTime.now(), 33.3d,
				ManifestationStatus.ACTIVE, "poster", true, mt1, sal2, loc4); // deleted

		Address adrs5 = new Address(44, "Beograd", 11000, "Marka Kraljevica");
		Location loc5 = new Location(444.3, 4444.3, adrs5);
		Manifestation man5 = new Manifestation("5555", "Man5SameLocationAndAddressAsDeleted", 51, LocalDateTime.now(),
				33.3d, ManifestationStatus.ACTIVE, "poster", false, mt1, sal2, loc5); // on the same location as deleted
																						// manifestation

		Address adrs6 = new Address(44, "Beograd", 11001, "Marka Kraljevica"); // different zip code, everything else is
																				// same
		Location loc6 = new Location(444.3, 4444.3, adrs6); // same coordinates as previous
		Manifestation man6 = new Manifestation("6666", "Man6SameLocationDifferentZip", 17, LocalDateTime.now(), 22.3d,
				ManifestationStatus.ACTIVE, "poster", false, mt1, sal2, loc5); // on the same location as deleted
																				// manifestation

		Address adrs7 = new Address(11, "Prigrevica", 25263, "Apatinska");
		Location loc7 = new Location(777.3, 7777.3, adrs7);
		Manifestation man7 = new Manifestation("7777", "Man7Inactive", 1000, LocalDateTime.now(), 550d,
				ManifestationStatus.INACTIVE, "poster", false, mt2, sal3, loc7);

		Address adrs8 = new Address(11, "Slepcevic", 25263, "Apatinska"); // Only city is different in the address
		Location loc8 = new Location(777.3, 7777.3, adrs8);
		Manifestation man8 = new Manifestation("8888", "Man8InAddressOnlyCityDifferent", 15, LocalDateTime.now(), 333d,
				ManifestationStatus.ACTIVE, "poster", false, mt3, sal3, loc8);

		Address adrs9 = new Address(11, "Slepcevic", 25263, "Apatinska"); // Only city is different in the address
		Location loc9 = new Location(777.3, 7777.3, adrs9);
		Manifestation man9 = new Manifestation("9999", "Man9InAddressOnlyCityDifferent", 71, LocalDateTime.now(), 21d,
				ManifestationStatus.ACTIVE, "poster", false, mt3, sal3, loc9);

		Address adrs10 = new Address(11, "Slepcevic", 25263, "Kicoska"); // Only Street is different in the address
		Location loc10 = new Location(777.3, 7777.3, adrs10);
		Manifestation man10 = new Manifestation("10101010", "Man10InAddressOnlyCityDifferentDELETED", 332,
				LocalDateTime.now(), 0.01d, ManifestationStatus.ACTIVE, "poster", true, mt3, sal3, loc10); // active but
																											// deleted
		// Saving
		InMemoryRepository.save(man1);
		InMemoryRepository.save(man2);
		InMemoryRepository.save(man3);
		InMemoryRepository.save(man4);
		InMemoryRepository.save(man5);
		InMemoryRepository.save(man6);
		InMemoryRepository.save(man7);
		InMemoryRepository.save(man8);
		InMemoryRepository.save(man9);
		InMemoryRepository.save(man10);
		
		// TICKETS---------------------------------------------------
		
		// For customer 1
		Ticket t1 = new Ticket("1", LocalDateTime.now(), 242.42, cust1.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust1, man1);

		Ticket t2 = new Ticket("2", LocalDateTime.now(), 242.42, cust1.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust1, man1);

		Ticket t3 = new Ticket("3", LocalDateTime.now(), 484.84d, cust1.getFirstName(), TicketType.FAN_PIT,
				TicketStatus.RESERVED, false, cust1, man1);

		Ticket t4 = new Ticket("4", LocalDateTime.now(), 242.42, cust1.getFirstName(), TicketType.REGULAR,
				TicketStatus.CANCELED, false, cust1, man1);
		
		Ticket t5 = new Ticket("5", LocalDateTime.now(), 969.68d, cust1.getFirstName(), TicketType.VIP,
				TicketStatus.RESERVED, true, cust1, man1); // deleted reserved ticket
		
		Ticket t6 = new Ticket("6", LocalDateTime.now(), 160d, cust1.getFirstName(), TicketType.VIP,
				TicketStatus.CANCELED, false, cust1, man4); // references a deleted manifestation and is also canceled
		
		Ticket t7 = new Ticket("7", LocalDateTime.now(), 160d, cust1.getFirstName(), TicketType.VIP,
				TicketStatus.RESERVED, false, cust1, man10); // references a deleted manifestation and is reserved
		
		// for customer 2 same as first 4 tickets for cust 1
		Ticket t8 = new Ticket("8", LocalDateTime.now(), 242.42, cust2.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust2, man1);

		Ticket t9 = new Ticket("9", LocalDateTime.now(), 242.42, cust2.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust2, man1);

		Ticket t10 = new Ticket("10", LocalDateTime.now(), 484.84d, cust2.getFirstName(), TicketType.FAN_PIT,
				TicketStatus.RESERVED, false, cust2, man1);
		
		Ticket t11 = new Ticket("11", LocalDateTime.now(), 242.42, cust2.getFirstName(), TicketType.REGULAR,
				TicketStatus.CANCELED, false, cust2, man1);
		
		// for customer 3 same as first 4 tickets for cust 1 and 2
		Ticket t12 = new Ticket("12", LocalDateTime.now(), 242.42, cust3.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust3, man1);

		Ticket t13 = new Ticket("13", LocalDateTime.now(), 242.42, cust3.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust3, man1);

		Ticket t14 = new Ticket("14", LocalDateTime.now(), 484.84d, cust3.getFirstName(), TicketType.FAN_PIT,
				TicketStatus.RESERVED, false, cust3, man1);
		
		Ticket t15 = new Ticket("15", LocalDateTime.now(), 242.42, cust3.getFirstName(), TicketType.REGULAR,
				TicketStatus.CANCELED, false, cust3, man1);
		
		// for customer 4 different event from cust 1,2 and 3
		Ticket t16 = new Ticket("16", LocalDateTime.now(), 100d, cust4.getFirstName(), TicketType.REGULAR,
				TicketStatus.RESERVED, false, cust4, man5);

		Ticket t17 = new Ticket("17", LocalDateTime.now(), 400d, cust4.getFirstName(), TicketType.VIP,
				TicketStatus.RESERVED, false, cust4, man5);

		Ticket t18 = new Ticket("18", LocalDateTime.now(), 200d, cust4.getFirstName(), TicketType.FAN_PIT,
				TicketStatus.RESERVED, false, cust4, man5);
		
		Ticket t19 = new Ticket("19", LocalDateTime.now(), 400d, cust4.getFirstName(), TicketType.VIP,
				TicketStatus.CANCELED, false, cust4, man5);
		
		// Saving
		InMemoryRepository.save(t1);
		InMemoryRepository.save(t2);
		InMemoryRepository.save(t3);
		InMemoryRepository.save(t4);
		InMemoryRepository.save(t5);
		InMemoryRepository.save(t6);
		InMemoryRepository.save(t7);
		InMemoryRepository.save(t8);
		InMemoryRepository.save(t9);
		InMemoryRepository.save(t10);
		InMemoryRepository.save(t11);
		InMemoryRepository.save(t12);
		InMemoryRepository.save(t13);
		InMemoryRepository.save(t14);
		InMemoryRepository.save(t15);
		InMemoryRepository.save(t16);
		InMemoryRepository.save(t17);
		InMemoryRepository.save(t18);
		InMemoryRepository.save(t19);
		
	}

}
