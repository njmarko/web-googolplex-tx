package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Customer extends User {
	private double points;
	private CustomerType customerType;

	private Collection<Comment> comments;
	private Collection<Comment> tickets;

	public Customer() {
		super();
	}

	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted, double points,
			CustomerType customerType) {
		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.points = points;
		this.customerType = customerType;
		this.comments = new ArrayList<Comment>();
		this.tickets = new ArrayList<Comment>();
		;
	}

	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted, double points,
			CustomerType customerType, Collection<Comment> comments, Collection<Comment> tickets) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted, points,
				customerType);
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
