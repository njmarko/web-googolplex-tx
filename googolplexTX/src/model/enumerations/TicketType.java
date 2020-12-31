package model.enumerations;

public enum TicketType {
	VIP, REGULAR, FAN_PIT;

	private String[] names = { "Vip", "Regular", "Fan Pit" };

	public String toString() {
		return names[this.ordinal()];
	}
}
