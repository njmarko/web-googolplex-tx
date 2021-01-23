package web.dto;

public class UserSearchDTO {

	// Search
	private String firstName;
	private String lastName;
	private String username;

	// Sort by firstname, lastname, username, points
	private Boolean ascending;
	private String sortCriteria;

	// Filter
	private String userRole; // enumeration in our code
	private String customerType; // class in our code

	public UserSearchDTO() {
		super();
	}

	/**
	 * @param firstName
	 * @param surname
	 * @param username
	 * @param ascending
	 * @param sortCriteria
	 * @param userRole
	 * @param customerType
	 */
	public UserSearchDTO(String firstName, String surname, String username, Boolean ascending, String sortCriteria,
			String userRole, String customerType) {
		super();
		this.firstName = firstName;
		this.lastName = surname;
		this.username = username;
		this.ascending = ascending;
		this.sortCriteria = sortCriteria;
		this.userRole = userRole;
		this.customerType = customerType;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return "UserSearchDTO [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", ascending=" + ascending + ", sortCriteria=" + sortCriteria + ", userRole=" + userRole
				+ ", customerType=" + customerType + "]";
	}

}
