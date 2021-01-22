package web.dto;

public class TicketDTO {
	private String id;
	private Long dateOfManifestation;
	private Double price;
	private String ticketType;
	private String ticketStatus;
	private Long cancelationDate;
	private Boolean deleted;

	public String customer;
	public String manifestation;
	
	public TicketDTO() {
		super();
	}
	public TicketDTO(String id, Long dateOfManifestation, Double price, String ticketType, String ticketStatus,
			Long cancelationDate, Boolean deleted, String customer, String manifestation) {
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
	public Long getDateOfManifestation() {
		return dateOfManifestation;
	}
	public void setDateOfManifestation(Long dateOfManifestation) {
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
	public Long getCancelationDate() {
		return cancelationDate;
	}
	public void setCancelationDate(Long cancelationDate) {
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
