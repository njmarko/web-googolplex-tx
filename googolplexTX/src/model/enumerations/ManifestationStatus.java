package model.enumerations;

public enum ManifestationStatus {
	ACTIVE, INACTIVE;

	private String[] names = { "Active", "Inactive" };

	public String toString() {
		return names[this.ordinal()];
	}
}
