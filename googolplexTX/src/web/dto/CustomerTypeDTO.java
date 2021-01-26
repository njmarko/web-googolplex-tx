package web.dto;

public class CustomerTypeDTO {
	private String name;
	private Double discount;
	private Double requiredPoints;

	
	public CustomerTypeDTO() {
		super();
	}
	
	
	/**
	 * @param name
	 * @param discount
	 * @param requiredPoints
	 */
	public CustomerTypeDTO(String name, Double discount, Double requiredPoints) {
		super();
		this.name = name;
		this.discount = discount;
		this.requiredPoints = requiredPoints;

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


	@Override
	public String toString() {
		return "CustomerTypeDTO [name=" + name + ", discount=" + discount + ", requiredPoints=" + requiredPoints + "]";
	}
	
	

}
