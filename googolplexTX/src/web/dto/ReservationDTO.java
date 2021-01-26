package web.dto;

import model.enumerations.TicketType;

public class ReservationDTO {

	private String customer;
	private String manifestation;
	private TicketType ticketType;
	private Integer quantity;

	public ReservationDTO() {
		super();
	}

	public ReservationDTO(String customer, String manifestation, TicketType ticketType, Integer quantity) {
		super();
		this.customer = customer;
		this.manifestation = manifestation;
		this.ticketType = ticketType;
		this.quantity = quantity;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getManifestation() {
		return manifestation;
	}

	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ReservationDTO [customer=" + customer + ", manifestation=" + manifestation + ", ticketType="
				+ ticketType + ", quantity=" + quantity + "]";
	}

}
