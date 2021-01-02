package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Customer extends User {
	private Double points;
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
	}

	/**
	 * Constructor that sets blocked to FALSE and deleted to FALSE
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthDate
	 * @param userRole
	 * @param points
	 * @param customerType
	 */
	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, double points, CustomerType customerType) {
		super(username, password, firstName, lastName, gender, birthDate, userRole, false, false);
	}

	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted, double points,
			CustomerType customerType, Collection<Comment> comments, Collection<Comment> tickets) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted, points,
				customerType);
		this.comments = comments;
		this.tickets = tickets;
	}

	/**
	 * Constructor that sets blocked to FALSE and deleted to FALSE
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthDate
	 * @param userRole
	 * @param points
	 * @param customerType
	 * @param comments
	 * @param tickets
	 */
	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, double points, CustomerType customerType,
			Collection<Comment> comments, Collection<Comment> tickets) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, false, false, points, customerType,
				comments, tickets);
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		if (points != null && points < 0) {
			this.points = 0d;
		} else {
			this.points = points;
		}
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
