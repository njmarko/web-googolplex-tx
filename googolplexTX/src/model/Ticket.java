package model;

import java.time.LocalDateTime;

import model.enumerations.TicketType;

public class Ticket {
	   private String id;
	   private LocalDateTime dateOfManifestation;
	   private double price;
	   private TicketType ticketType;
	   private boolean deleted;
	   
	   public Customer customer;
}
