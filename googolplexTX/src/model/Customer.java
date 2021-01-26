package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Customer extends User {
	private Double points;
	private CustomerType customerType;

	private Collection<Comment> comments;
	private Collection<Ticket> tickets;

	public Customer() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthDate
	 * @param userRole
	 * @param blocked
	 * @param deleted
	 * @param points
	 * @param customerType
	 */
	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted, double points,
			CustomerType customerType) {
		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.points = points;
		this.customerType = customerType;
		this.comments = new ArrayList<Comment>();
		this.tickets = new ArrayList<Ticket>();
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
		this(username, password, firstName, lastName, gender, birthDate, userRole, false, false, points, customerType);
	}

	
	/**
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthDate
	 * @param userRole
	 * @param blocked
	 * @param deleted
	 * @param points
	 * @param customerType
	 * @param comments
	 * @param tickets
	 */
	public Customer(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted, double points,
			CustomerType customerType, Collection<Comment> comments, Collection<Ticket> tickets) {
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
			Collection<Comment> comments, Collection<Ticket> tickets) {
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
		if (comments.isEmpty()) {
			return comments;
		}
		Collection<Comment> retVal = comments.stream().filter((Comment ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
		return retVal;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public Collection<Ticket> getTickets() {
		if (tickets.isEmpty()) {
			return tickets;
		}
		tickets = tickets.stream().filter((Ticket ent) -> {
			return !ent.getDeleted();
		}).collect(Collectors.toList());
		return tickets;
	}

	public void setTickets(Collection<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "Customer [points=" + points + ", customerType=" + customerType + "]";
	}

}
