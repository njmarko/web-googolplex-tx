package web.dto;

public class CommentDTO {
	private String id;
	private String text;
	private Integer rating;
	private String approved;
	private Boolean deleted;

	public String manifestation;
	public String customer;
	
	public CommentDTO() {
		super();
	}
	public CommentDTO(String id, String text, Integer rating, String approved, Boolean deleted, String manifestation,
			String customer) {
		super();
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.approved = approved;
		this.deleted = deleted;
		this.manifestation = manifestation;
		this.customer = customer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getManifestation() {
		return manifestation;
	}
	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
