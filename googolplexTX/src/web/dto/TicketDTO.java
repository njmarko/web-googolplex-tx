package web.dto;

public class TicketDTO {
	private String id;
	private String dateOfManifestation;
	private Double price;
	private String ticketType;
	private String ticketStatus;
	private String cancelationDate;
	private Boolean deleted;

	public String customer;
	public String manifestation;
	
	public TicketDTO() {
		super();
	}
	public TicketDTO(String id, String dateOfManifestation, Double price, String ticketType, String ticketStatus,
			String cancelationDate, Boolean deleted, String customer, String manifestation) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
		this.cancelationDate = cancelationDate;
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
	public String getDateOfManifestation() {
		return dateOfManifestation;
	}
	public void setDateOfManifestation(String dateOfManifestation) {
		this.dateOfManifestation = dateOfManifestation;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getCancelationDate() {
		return cancelationDate;
	}
	public void setCancelationDate(String cancelationDate) {
		this.cancelationDate = cancelationDate;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

}
