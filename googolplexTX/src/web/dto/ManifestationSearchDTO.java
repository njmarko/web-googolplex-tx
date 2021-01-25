package web.dto;

public class ManifestationSearchDTO {

	// Search
	private String name;
	private Long beginDate;
	private Long endDate;
	// Location searches for city OR state
	private String location;
	private Double minPrice;
	private Double maxPrice;

	// Sort
	private Boolean ascending;
	private String sortCriteria;

	// filter
	private String manifestationType;
	// Manifestation is not sold out
	private Boolean hasAvailableTickets;

	// active or inactive
	private String status;

	public ManifestationSearchDTO() {
		super();
	}

	/**
	 * @param name
	 * @param beginDate
	 * @param endDate
	 * @param location
	 * @param minPrice
	 * @param maxPrice
	 * @param ascending
	 * @param sortCriteria
	 * @param manifestationType
	 * @param hasAvailableTickets
	 * @param status
	 */
	public ManifestationSearchDTO(String name, Long beginDate, Long endDate, String location, Double minPrice,
			Double maxPrice, Boolean ascending, String sortCriteria, String manifestationType,
			Boolean hasAvailableTickets, String status) {
		super();
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.location = location;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.ascending = ascending;
		this.sortCriteria = sortCriteria;
		this.manifestationType = manifestationType;
		this.hasAvailableTickets = hasAvailableTickets;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getManifestationType() {
		return manifestationType;
	}

	public void setManifestationType(String manifestationType) {
		this.manifestationType = manifestationType;
	}

	public Boolean getHasAvailableTickets() {
		return hasAvailableTickets;
	}

	public void setHasAvailableTickets(Boolean hasAvailableTickets) {
		this.hasAvailableTickets = hasAvailableTickets;
	}

	@Override
	public String toString() {
		return "ManifestationSearchDTO [name=" + name + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", location=" + location + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", ascending="
				+ ascending + ", sortCriteria=" + sortCriteria + ", manifestationType=" + manifestationType
				+ ", hasAvailableTickets=" + hasAvailableTickets + ", status=" + status + "]";
	}

}
