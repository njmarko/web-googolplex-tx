package model;

import java.time.LocalDate;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Gender gender;
	private LocalDate birthDate;
	private UserRole userRole;
	private Boolean blocked;
	private Boolean deleted;
	/*
	 * TODO add JSON Web token
	 */

	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate,
			UserRole userRole, Boolean blocked, Boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.userRole = userRole;
		this.blocked = blocked;
		this.deleted = deleted;
	}

	/**
	 * Constructor that sets blocked to FALSE and deleted to FALSE
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthDate
	 * @param userRole
	 */
	public User(String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate,
			UserRole userRole) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, false, false);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", birthDate=" + birthDate + ", userRole=" + userRole + ", blocked="
				+ blocked + ", deleted=" + deleted + "]";
	}

}
