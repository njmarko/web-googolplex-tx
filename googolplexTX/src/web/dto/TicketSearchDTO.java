package web.dto;

/**
 * @author Milos
 *
 */
public class TicketSearchDTO {
	// Search
	private String manifestationName;
	private Double minPrice;
	private Double maxPrice;
	private Long beginDate;
	private Long endDate;

	// Sort
	private Boolean ascending;
	private String sortCriteria;

	// Filter
	private String ticketType;
	private String ticketStatus;

	public TicketSearchDTO() {
		super();
	}

	/**
	 * @param manifestationName
	 * @param minPrice
	 * @param maxPrice
	 * @param beginDate
	 * @param endDate
	 * @param ascending
	 * @param sortCriteria
	 * @param ticketType
	 * @param ticketStatus
	 */
	public TicketSearchDTO(String manifestationName, Double minPrice, Double maxPrice, Long beginDate, Long endDate,
			Boolean ascending, String sortCriteria, String ticketType, String ticketStatus) {
		super();
		this.manifestationName = manifestationName;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.ascending = ascending;
		this.sortCriteria = sortCriteria;
		this.ticketType = ticketType;
		this.ticketStatus = ticketStatus;
	}

	public String getManifestationName() {
		return manifestationName;
	}

	public void setManifestationName(String manifestationName) {
		this.manifestationName = manifestationName;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Boolean getAscending() {
		return ascending;
	}

	public void setAscending(Boolean ascending) {
		this.ascending = ascending;
	}

	public String getSortCriteria() {
		return sortCriteria;
	}

	public void setSortCriteria(String sortCriteria) {
		this.sortCriteria = sortCriteria;
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

	@Override
	public String toString() {
		return "TicketSearchDTO [manifestationName=" + manifestationName + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", ascending=" + ascending + ", sortCriteria="
				+ sortCriteria + ", ticketType=" + ticketType + ", ticketStatus=" + ticketStatus + "]";
	}

}
