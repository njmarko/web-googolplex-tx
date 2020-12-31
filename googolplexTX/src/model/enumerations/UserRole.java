package model.enumerations;

public enum UserRole {
	ADMIN, CUSTOMER, SALESMAN;

	private String[] names = { "Admin", "Customer", "Salesman" };

	public String toString() {
		return names[this.ordinal()];
	}
}
