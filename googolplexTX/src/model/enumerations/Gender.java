package model.enumerations;

public enum Gender {
	MALE, FEMALE;

	private String[] names = { "Male", "Female" };

	public String toString() {
		return names[this.ordinal()];
	}

}
