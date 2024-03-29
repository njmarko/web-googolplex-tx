package model;

public class CustomerType {
	private String name;
	private Double discount;
	private Double requiredPoints;
	private Boolean deleted;

	public CustomerType() {
		super();
	}

	/**
	 * @param name
	 * @param discount
	 * @param requiredPoints
	 * @param deleted
	 */
	public CustomerType(String name, Double discount, Double requiredPoints, Boolean deleted) {
		super();
		this.name = name;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
		this.deleted = deleted;
	}

	/**
	 * Constructor that sets deleted to FALSE
	 * 
	 * @param name
	 * @param discount
	 * @param requiredPoints
	 */
	public CustomerType(String name, Double discount, Double requiredPoints) {
		this(name, discount, requiredPoints, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(Double requiredPoints) {
		this.requiredPoints = requiredPoints;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "CustomerType [name=" + name + ", discount=" + discount + ", requiredPoints=" + requiredPoints
				+ ", deleted=" + deleted + "]";
	}

}
