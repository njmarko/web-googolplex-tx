package web.dto;

import java.util.Collection;

import model.Location;
import spark.utils.StringUtils;

public class ManifestationDTO {
	private String id;
	private String name;
	private Integer availableSeats;
	private Long dateOfOccurence;
	private Double regularPrice;
	private String status;
	private String poster;

	private String manifestationType;
	private String salesman;
	private Location location;
	private Collection<String> comments;
	private Collection<String> tickets;

	
	public String validate() {
		String err = null;		
		
		if (StringUtils.isEmpty(name)) {
			err = "You have to enter name";
		} else if (StringUtils.isEmpty(status)) {
			err = "You have to enter status";
		} else if (StringUtils.isEmpty(poster)) {
			err = "You have to enter poster";
		} else if (StringUtils.isEmpty(manifestationType)) {
			err = "You have to enter manifestationType";
		} else if (StringUtils.isEmpty(salesman)) {
			err = "You have to enter salesman";
		} else if (location == null) {
			err = "You have to enter location";
		} else if (StringUtils.isEmpty(location.getCity())) {
			err = "You have to enter city";
		} else if (location.getLatitude() == null) {
			err = "You have to enter latitude";
		} else if (location.getLongitude() == null) {
			err = "You have to enter longitude";
		} else if (StringUtils.isEmpty(location.getNumber())) {
			err = "You have to enter street number";
		} else if (StringUtils.isEmpty(location.getStreet())) {
			err = "You have to enter street";
		} else if (location.getZipCode() == null) {
			err = "You have to enter zipCode";
		} else if (availableSeats == null) {
			err = "You have to enter availableSeats";
		} else if (dateOfOccurence == null){
			err = "You have to enter dateOfOccurence";
		} else if (regularPrice == null){
			err = "You have to enter regularPrice";
		} else if (availableSeats <= 0) {
			err = "Manifestation mush have at least one available seat";
		} else if (regularPrice < 0) {
			err = "Manifestation mush have at least one available seat";
		}
		
		
		return err;
	}
	
	
	public ManifestationDTO() {
		super();
	}

	
	/**
	 * @param id
	 * @param name
	 * @param availableSeats
	 * @param dateOfOccurence
	 * @param regularPrice
	 * @param status
	 * @param poster
	 * @param manifestationType
	 * @param salesman
	 * @param location
	 * @param comments
	 * @param tickets
	 */
	public ManifestationDTO(String id, String name, Integer availableSeats, Long dateOfOccurence, Double regularPrice,
			String status, String poster, String manifestationType, String salesman, Location location,
			Collection<String> comments, Collection<String> tickets) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.dateOfOccurence = dateOfOccurence;
		this.regularPrice = regularPrice;
		this.status = status;
		this.poster = poster;
		this.manifestationType = manifestationType;
		this.salesman = salesman;
		this.location = location;
		this.comments = comments;
		this.tickets = tickets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Long getDateOfOccurence() {
		return dateOfOccurence;
	}

	public void setDateOfOccurence(Long dateOfOccurence) {
		this.dateOfOccurence = dateOfOccurence;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getManifestationType() {
		return manifestationType;
	}

	public void setManifestationType(String manifestationType) {
		this.manifestationType = manifestationType;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	public Collection<String> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<String> tickets) {
		this.tickets = tickets;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ManifestationDTO [id=" + id + ", name=" + name + ", availableSeats=" + availableSeats
				+ ", dateOfOccurence=" + dateOfOccurence + ", regularPrice=" + regularPrice + ", status=" + status
				+ ", poster=" + poster + ", manifestationType=" + manifestationType + ", salesman=" + salesman
				+ ", location=" + location + ", comments=" + comments + ", tickets=" + tickets + "]";
	}

}
