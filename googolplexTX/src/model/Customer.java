package model;

import java.util.Collection;

public class Customer extends User {
	private double points;
	private CustomerType customerType;

	private Collection<Comment> comments;
	private Collection<Comment> tickets;

	public Customer() {
		super();
	}

	public Customer(double points) {
		super();
		this.points = points;
	}

	public Customer(double points, CustomerType customerType) {
		super();
		this.points = points;
		this.customerType = customerType;
	}

	public Customer(double points, CustomerType customerType, Collection<Comment> comments,
			Collection<Comment> tickets) {
		super();
		this.points = points;
		this.customerType = customerType;
		this.comments = comments;
		this.tickets = tickets;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public Collection<Comment> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<Comment> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "Customer [points=" + points + ", customerType=" + customerType + "]";
	}

}
