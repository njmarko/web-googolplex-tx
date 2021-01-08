package web.dto;

public class CustomerTypeDTO {
	private String name;
	private Double discount;
	private Double requiredPoints;
	private Boolean deleted;
	
	public CustomerTypeDTO() {
		super();
	}
	public CustomerTypeDTO(String name, Double discount, Double requiredPoints, Boolean deleted) {
		super();
		this.name = name;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
		this.deleted = deleted;
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

	

}