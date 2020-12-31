package model;

import java.time.LocalDateTime;

import model.enumerations.TicketType;

public class Ticket {
	private String id;
	private LocalDateTime dateOfManifestation;
	private Double price;
	private TicketType ticketType;
	private Boolean deleted;

	public Customer customer;

	public Ticket() {
		super();
	}

	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, TicketType ticketType, Boolean deleted) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.ticketType = ticketType;
		this.deleted = deleted;
	}

	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, TicketType ticketType, Boolean deleted,
			Customer customer) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.ticketType = ticketType;
		this.deleted = deleted;
		this.customer = customer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDateOfManifestation() {
		return dateOfManifestation;
	}

	public void setDateOfManifestation(LocalDateTime dateOfManifestation) {
		this.dateOfManifestation = dateOfManifestation;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", dateOfManifestation=" + dateOfManifestation + ", price=" + price
				+ ", ticketType=" + ticketType + ", deleted=" + deleted + "]";
	}

}
