package web.dto;

public class PasswordDTO {
	private String username;
	private String oldPassword;
	private String newPassword;

	public String validate() {
		String error = null;
		if (oldPassword == null || newPassword == null || newPassword.trim().length() == 0) {
			error = "Password must contain characters";
		}

		return error;
	}

	public PasswordDTO() {
		super();
	}

	/**
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 */
	public PasswordDTO(String username, String oldPassword, String newPassword) {
		super();
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordDTO [username=" + username + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ "]";
	}

}