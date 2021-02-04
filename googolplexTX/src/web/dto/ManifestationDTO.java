package web.dto;

import java.util.Collection;

import model.Location;
import model.Manifestation;
import model.User;
import model.enumerations.ManifestationStatus;
import model.enumerations.UserRole;
import spark.utils.StringUtils;

public class ManifestationDTO {
	private String id;
	private String name;
	private Integer availableSeats;
	
	/**
	 * total seats are calculated as availableSeats + reservedTickets 
	 * in support class that converts Manifestation to ManifestationDTO
	 */
	private Integer totalSeats;
	private Long dateOfOccurence;
	private Double regularPrice;
	private String status;
	private String poster;

	private String manifestationType;
	private String salesman;
	private Location location;
	private Double averageRating;
	private Collection<String> comments;
	private Collection<String> tickets;

	public String validate() {
		String err = null;

		if (StringUtils.isEmpty(name)) {
			err = "You have to enter name";
		} else if (StringUtils.isEmpty(status)) {
			err = "You have to enter status";
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
		} else if (dateOfOccurence == null) {
			err = "You have to enter dateOfOccurence";
		} else if (regularPrice == null) {
			err = "You have to enter regularPrice";
		} else if (availableSeats < 0) {
			err = "Number of seats must not be negative";
		} else if (regularPrice < 0) {
			err = "Price must not be negative";
		}
		return err;
	}

	public String validate(User loggedIn, Manifestation existingManif) {
		String err = this.validate();
		if (err == null) {
			if (loggedIn.getUserRole() == UserRole.CUSTOMER) {
				err = "Customer can not edit manifestations";
			} else if (loggedIn.getUserRole() == UserRole.SALESMAN
					&& status.trim().compareToIgnoreCase(existingManif.getStatus().name()) != 0) {
				err = "Salesman can only create inactive manifestations and cannot edit their status!";
			}
		}
		return err;
	}

	public ManifestationDTO() {
		super();
	}



	public ManifestationDTO(String id, String name, Integer availableSeats, Integer totalSeats, Long dateOfOccurence,
			Double regularPrice, String status, String poster, String manifestationType, String salesman,
			Location location, Double averageRating, Collection<String> comments, Collection<String> tickets) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.totalSeats = totalSeats;
		this.dateOfOccurence = dateOfOccurence;
		this.regularPrice = regularPrice;
		this.status = status;
		this.poster = poster;
		this.manifestationType = manifestationType;
		this.salesman = salesman;
		this.location = location;
		this.averageRating = averageRating;
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

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
	
	

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	@Override
	public String toString() {
		return "ManifestationDTO [id=" + id + ", name=" + name + ", availableSeats=" + availableSeats + ", totalSeats="
				+ totalSeats + ", dateOfOccurence=" + dateOfOccurence + ", regularPrice=" + regularPrice + ", status="
				+ status + ", poster=" + poster + ", manifestationType=" + manifestationType + ", salesman=" + salesman
				+ ", location=" + location + ", averageRating=" + averageRating + ", comments=" + comments
				+ ", tickets=" + tickets + "]";
	}



}
