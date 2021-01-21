package web.dto;

import spark.utils.StringUtils;

public class LoginDTO {

	private String username;
	private String password;

	public String validate() {
		// TODO check for aditional things about password maybe 8 characters etc
		// Empty is null or "". blank is just "" or white spaces
		String err = null;
		if (StringUtils.isEmpty(this.username)) {
			err = "Username must not be empty";
		} else if (StringUtils.isEmpty(password)) {
			err = "Password must not be empty";
		}

		return err;
	}

	public LoginDTO() {
		super();
	}

	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "LoginDTO [username=" + username + ", password=" + password + "]";
	}

}
