package web.dto;

public class CommentDTO {
	private String id;
	private String text;
	private Integer rating;
	private String approved;

	public String manifestation;
	public String customer;

	public CommentDTO() {
		super();
	}

	/**
	 * @param id
	 * @param text
	 * @param rating
	 * @param approved
	 * @param manifestation
	 * @param customer
	 */
	public CommentDTO(String id, String text, Integer rating, String approved, String manifestation, String customer) {
		super();
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.approved = approved;
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

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", text=" + text + ", rating=" + rating + ", approved=" + approved
				+ ", manifestation=" + manifestation + ", customer=" + customer + "]";
	}

}
