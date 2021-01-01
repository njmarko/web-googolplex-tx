package model.enumerations;

public enum CommentStatus {
	PENDING, ACCEPTED, REJECTED;

	private String[] names = { "Pending", "Accepted", "Rejected" };

	public String toString() {
		return names[this.ordinal()];
	}
}
