package model.enumerations;

public enum TicketStatus {
	RESERVED, CANCELED;

	private String[] names = { "Reserved", "Canceled" };

	public String toString() {
		return names[this.ordinal()];
	}
}
