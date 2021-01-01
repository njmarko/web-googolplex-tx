package model;

import model.enumerations.CommentStatus;

public class Comment {
	private String text;
	private Integer rating;
	private CommentStatus approved;
	private Boolean deleted;

	public Manifestation manifestation;
	public Customer customer;

	public Comment() {
		super();
	}

	public Comment(String text, Integer rating, CommentStatus approved, Boolean deleted) {
		super();
		this.text = text;
		this.rating = rating;
		this.approved = approved;
		this.deleted = deleted;
	}

	public Comment(String text, Integer rating, CommentStatus approved, Boolean deleted, Manifestation manifestation,
			Customer customer) {
		super();
		this.text = text;
		this.rating = rating;
		this.approved = approved;
		this.deleted = deleted;
		this.manifestation = manifestation;
		this.customer = customer;
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

	public CommentStatus getApproved() {
		return approved;
	}

	public void setApproved(CommentStatus approved) {
		this.approved = approved;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", rating=" + rating + ", approved=" + approved + ", deleted=" + deleted + "]";
	}

}
