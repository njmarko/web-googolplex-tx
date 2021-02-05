package model;

import java.time.LocalDateTime;

import model.enumerations.TicketStatus;
import model.enumerations.TicketType;

public class Ticket {
	private String id;
	private LocalDateTime dateOfManifestation;
	private Double price;
	private TicketType ticketType;
	private TicketStatus ticketStatus;
	private LocalDateTime cancelationDate;
	private Boolean deleted;

	public Customer customer;
	public Manifestation manifestation;

	public Ticket() {
		super();
	}

	/**
	 * @param id
	 * @param dateOfManifestation
	 * @param price
	 * @param ticketType
	 * @param ticketStatus
	 * @param cancelationDate
	 * @param deleted
	 */
	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, TicketType ticketType,
			TicketStatus ticketStatus, LocalDateTime cancelationDate, Boolean deleted) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
		this.cancelationDate = cancelationDate;
		this.deleted = deleted;
	}

	/**
	 * @param id
	 * @param dateOfManifestation
	 * @param price
	 * @param ticketType
	 * @param ticketStatus
	 * @param cancelationDate
	 * @param deleted
	 * @param customer
	 * @param manifestation
	 */
	public Ticket(String id, LocalDateTime dateOfManifestation, Double price, TicketType ticketType,
			TicketStatus ticketStatus, LocalDateTime cancelationDate, Boolean deleted, Customer customer,
			Manifestation manifestation) {
		this(id, dateOfManifestation, price, ticketType, ticketStatus, cancelationDate, deleted);
		this.customer = customer;
		this.manifestation = manifestation;
	}

	/**
	 * Constructor that sets deleted to FALSE, cancelationDate to NULL, ticketStatus
	 * to RESERVED. It takes customerName from customer, and dateOfManifestation
	 * from manifestation
	 * 
	 * @param id
	 * @param price
	 * @param ticketType
	 * @param customer
	 * @param manifestation
	 */
	public Ticket(String id, Double price, TicketType ticketType, Customer customer, Manifestation manifestation) {
		this(id, manifestation.getDateOfOccurence(), price, ticketType, TicketStatus.RESERVED, null, false, customer,
				manifestation);
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

	public LocalDateTime getCancelationDate() {
		return cancelationDate;
	}

	public void setCancelationDate(LocalDateTime cancelationDate) {
		this.cancelationDate = cancelationDate;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", dateOfManifestation=" + dateOfManifestation + ", price=" + price
				+ ", ticketType=" + ticketType + ", ticketStatus=" + ticketStatus + ", cancelationDate="
				+ cancelationDate + ", deleted=" + deleted + ", customer=" + customer.getUsername() + ", manifestation="
				+ manifestation.getId() + "]";
	}

}
