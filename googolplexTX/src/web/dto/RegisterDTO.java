package web.dto;

import model.enumerations.UserRole;
import spark.utils.SparkUtils;
import spark.utils.StringUtils;

public class RegisterDTO {

	private String username;
	private String password1;
	private String password2;
	private String firstName;
	private String lastName;
	private String gender;
	private Long birthDate;
	// Only admin should be able to set user role. It should be checked in the REST
	// if logged in user is admin
	private String userRole;
	// points and customer type will be set automatically so there is no need to
	// send them.

	/**
	 * Checks if all the fields are not empty and if confirmation passwords match
	 * 
	 * @param loggedInUserRole Enum for the user role, because only admin has the
	 *                         right to create Salesman
	 * @return string with error message, or null if no error
	 */
	public String validate(UserRole loggedInUserRole) {
		String err = null;

		if (StringUtils.isEmpty(username)) {
			err = "You have to enter username";
		} else if (StringUtils.isEmpty(password1)) {
			err = "You have to enter a password";
		} else if (StringUtils.isEmpty(password2)) {
			err = "You have to enter a confirmation password";
		} else if (StringUtils.isEmpty(password2)) {
			err = "You have to enter a password";
		} else if (password1.equals(password2)) {
			err = "Password and confirmation password mush match";
		} else if (StringUtils.isEmpty(firstName)) {
			err = "You have to enter the first name";
		} else if (StringUtils.isEmpty(lastName)) {
			err = "You have to enter the last name";
		} else if (StringUtils.isEmpty(gender)) {
			err = "You have to enter the gender";
		} else if (birthDate == null) {
			err = "You have to enter the birth date";
		} else if (!StringUtils.isEmpty(userRole)) {
			if (loggedInUserRole != UserRole.ADMIN) {
				if (!userRole.equals("CUSTOMER")) {
					err = "You are not allowed to register that user type!";
				}
			}
			else if (loggedInUserRole == UserRole.ADMIN && userRole.equals("ADMIN")) {
				err = " You are not allowed to register that user type!";
			}
		}
		return err;
	}

	public RegisterDTO() {
		super();
	}

	public RegisterDTO(String username, String password1, String password2, String firstName, String lastName,
			String gender, Long birthDate, String userRole) {
		super();
		this.username = username;
		this.password1 = password1;
		this.password2 = password2;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.userRole = userRole;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
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

	public Long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "RegisterDTO [username=" + username + ", password1=" + password1 + ", password2=" + password2
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", birthDate="
				+ birthDate + ", userRole=" + userRole + "]";
	}

}
