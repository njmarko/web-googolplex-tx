package model;

import model.enumerations.CommentStatus;

/**
 * @author Marko
 *
 */
public class Comment {
	private String id;
	private String text;
	private Integer rating;
	private CommentStatus approved;
	private Boolean deleted;

	public Manifestation manifestation;
	public Customer customer;

	public Comment() {
		super();
	}

	public Comment(String id, String text, Integer rating, CommentStatus approved, Boolean deleted) {
		super();
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.approved = approved;
		this.deleted = deleted;
	}

	/**
	 * Constructor that sets comment status to PENDING and deleted to false
	 * 
	 * @param id
	 * @param text
	 * @param rating
	 */
	public Comment(String id, String text, Integer rating) {
		this(id, text, rating, CommentStatus.PENDING, false);
	}

	public Comment(String id, String text, Integer rating, CommentStatus approved, Boolean deleted,
			Manifestation manifestation, Customer customer) {
		this(id, text, rating, approved, deleted);
		this.manifestation = manifestation;
		this.customer = customer;
	}

	/**
	 * Constructor that sets comment status to PENDING and deleted to false
	 * 
	 * @param id
	 * @param text
	 * @param rating
	 * @param manifestation
	 * @param customer
	 */
	public Comment(String id, String text, Integer rating, Manifestation manifestation, Customer customer) {
		this(id, text, rating, CommentStatus.PENDING, false, manifestation, customer);
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
		return "Comment [id=" + id + ", text=" + text + ", rating=" + rating + ", approved=" + approved + ", deleted="
				+ deleted + "]";
	}

}
