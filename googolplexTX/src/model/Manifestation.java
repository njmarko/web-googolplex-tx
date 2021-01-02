package model;

import java.time.LocalDateTime;
import java.util.Collection;

import com.google.gson.annotations.Expose;

import model.enumerations.ManifestationStatus;

public class Manifestation {
	private String id;
	private String name;
	private Integer availableSeats;
	private LocalDateTime dateOfOccurence;
	private Double regularPrice;
	private ManifestationStatus status;
	/*
	 * It was Base64 type before, but i changed it to string that represents a path
	 */
	private String poster;
	private Boolean deleted;

	@Expose
	public Salesman salesman;
	public Location location;
	public Collection<Comment> comments;

	public Manifestation() {
		super();
	}

	public Manifestation(String id, String name, Integer availableSeats, LocalDateTime dateOfOccurence,
			Double regularPrice, ManifestationStatus status, String poster, Boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.dateOfOccurence = dateOfOccurence;
		this.regularPrice = regularPrice;
		this.status = status;
		this.poster = poster;
		this.deleted = deleted;
	}

	public Manifestation(String id, String name, Integer availableSeats, LocalDateTime dateOfOccurence,
			Double regularPrice, ManifestationStatus status, String poster, Boolean deleted, Salesman salesman,
			Location location) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.dateOfOccurence = dateOfOccurence;
		this.regularPrice = regularPrice;
		this.status = status;
		this.poster = poster;
		this.deleted = deleted;
		this.salesman = salesman;
		this.location = location;
	}

	public Manifestation(String id, String name, Integer availableSeats, LocalDateTime dateOfOccurence,
			Double regularPrice, ManifestationStatus status, String poster, Boolean deleted, Salesman salesman,
			Location location, Collection<Comment> comments) {
		super();
		this.id = id;
		this.name = name;
		this.availableSeats = availableSeats;
		this.dateOfOccurence = dateOfOccurence;
		this.regularPrice = regularPrice;
		this.status = status;
		this.poster = poster;
		this.deleted = deleted;
		this.salesman = salesman;
		this.location = location;
		this.comments = comments;
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

	public LocalDateTime getDateOfOccurence() {
		return dateOfOccurence;
	}

	public void setDateOfOccurence(LocalDateTime dateOfOccurence) {
		this.dateOfOccurence = dateOfOccurence;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public ManifestationStatus getStatus() {
		return status;
	}

	public void setStatus(ManifestationStatus status) {
		this.status = status;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Manifestation [id=" + id + ", name=" + name + ", availableSeats=" + availableSeats
				+ ", dateOfOccurence=" + dateOfOccurence + ", regularPrice=" + regularPrice + ", status=" + status
				+ ", poster=" + poster + ", deleted=" + deleted + ", salesman=" + salesman + ", location=" + location
				+ "]";
	}

}
