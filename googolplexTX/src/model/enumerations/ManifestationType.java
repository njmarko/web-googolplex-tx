package model.enumerations;

public enum ManifestationType {
	CONCERT, FESTIVAL, THEATRE;

	private String[] names = { "Concert", "Festival", "Theatre" };

	public String toString() {
		return names[this.ordinal()];
	}
}
