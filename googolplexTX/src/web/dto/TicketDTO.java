package web.dto;

public class TicketDTO {
	private String id;
	private Long dateOfManifestation;
	private Double price;
	private String ticketType;
	private String ticketStatus;
	private Long cancelationDate;

	private String poster;
	private String customer;
	private String manifestation;
	private String manifestationName;
	private String cutomerFullName;

	public TicketDTO() {
		super();
	}

	/**
	 * @param id
	 * @param dateOfManifestation
	 * @param price
	 * @param ticketType
	 * @param ticketStatus
	 * @param cancelationDate
	 * @param poster
	 * @param customer
	 * @param manifestation
	 * @param manifestationName
	 * @param cutomerFullName
	 */
	public TicketDTO(String id, Long dateOfManifestation, Double price, String ticketType, String ticketStatus,
			Long cancelationDate, String poster, String customer, String manifestation, String manifestationName,
			String cutomerFullName) {
		super();
		this.id = id;
		this.dateOfManifestation = dateOfManifestation;
		this.price = price;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
		this.cancelationDate = cancelationDate;
		this.poster = poster;
		this.customer = customer;
		this.manifestation = manifestation;
		this.manifestationName = manifestationName;
		this.cutomerFullName = cutomerFullName;
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

	public String getManifestationName() {
		return manifestationName;
	}

	public void setManifestationName(String manifestationName) {
		this.manifestationName = manifestationName;
	}

	public String getCutomerFullName() {
		return cutomerFullName;
	}

	public void setCutomerFullName(String cutomerFullName) {
		this.cutomerFullName = cutomerFullName;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public String toString() {
		return "TicketDTO [id=" + id + ", dateOfManifestation=" + dateOfManifestation + ", price=" + price
				+ ", ticketType=" + ticketType + ", ticketStatus=" + ticketStatus + ", cancelationDate="
				+ cancelationDate + ", poster=" + poster + ", customer=" + customer + ", manifestation=" + manifestation
				+ ", manifestationName=" + manifestationName + ", cutomerFullName=" + cutomerFullName + "]";
	}

}
