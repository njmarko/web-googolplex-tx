package web.dto;

import java.util.Collection;

import model.Location;

public class ManifestationDTO {
	private String id;
	private String name;
	private Integer availableSeats;
	private String dateOfOccurence;
	private Double regularPrice;
	private String status;
	private String poster;

	private String manifestationType;
	public String salesman;
	public Location location;
	public Collection<String> comments;
	public Collection<String> tickets;

	public ManifestationDTO() {
		super();
	}

	public ManifestationDTO(String id, String name, Integer availableSeats, String dateOfOccurence, Double regularPrice,
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

	public String getDateOfOccurence() {
		return dateOfOccurence;
	}

	public void setDateOfOccurence(String dateOfOccurence) {
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
