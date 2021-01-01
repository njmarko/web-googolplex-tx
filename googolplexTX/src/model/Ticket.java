package model;

import java.time.LocalDateTime;

import model.enumerations.TicketStatus;
import model.enumerations.TicketType;

public class Ticket {
	private String id;
	private LocalDateTime dateOfManifestation;
	private Double price;
	private String customerName;
	private TicketType ticketType;
	private TicketStatus ticketStatus;
	private Boolean deleted;

	public Customer customer;
	public Manifestation manifestation;

	public Ticket() {
		super();
	}

	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, String customerName,
			TicketType ticketType, TicketStatus ticketStatus, Boolean deleted) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.customerName = customerName;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
		this.deleted = deleted;
	}

	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, String customerName,
			TicketType ticketType, TicketStatus ticketStatus, Boolean deleted, Customer customer,
			Manifestation manifestation) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.customerName = customerName;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
		this.deleted = deleted;
		this.customer = customer;
		this.manifestation = manifestation;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
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

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", dateOfManifestation=" + dateOfManifestation + ", price=" + price
				+ ", customerName=" + customerName + ", ticketType=" + ticketType + ", ticketStatus=" + ticketStatus
				+ ", deleted=" + deleted + ", customer=" + customer + ", manifestation=" + manifestation + "]";
	}

}
