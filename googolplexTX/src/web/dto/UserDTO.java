package web.dto;

import java.util.Collection;

public class UserDTO {
	// User
	private String username;
	private String firstName;
	private String lastName;
	private String gender;
	private Long birthDate;
	private String userRole;
	private Boolean blocked;
	private String jwt;

	// Salesman
	public Collection<String> manifestations;

	// Customer
	private Double points;
	private String customerType;

	private Collection<String> comments;
	private Collection<String> tickets;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String firstName, String lastName, String gender, Long birthDate, String userRole,
			Boolean blocked, String jwt, Collection<String> manifestations, Double points, String customerType,
			Collection<String> comments, Collection<String> tickets) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.userRole = userRole;
		this.blocked = blocked;
		this.jwt = jwt;
		this.manifestations = manifestations;
		this.points = points;
		this.customerType = customerType;
		this.comments = comments;
		this.tickets = tickets;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Collection<String> getManifestations() {
		return manifestations;
	}

	public void setManifestations(Collection<String> manifestations) {
		this.manifestations = manifestations;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", birthDate=" + birthDate + ", userRole=" + userRole + ", blocked=" + blocked + ", jwt="
				+ jwt + ", manifestations=" + manifestations + ", points=" + points + ", customerType=" + customerType
				+ ", comments=" + comments + ", tickets=" + tickets + "]";
	}

}
