package model;

import java.util.Collection;

public class Customer extends User {
	private double points;
	private CustomerType customerType;
	
	private Collection<Comment> comments;
	private Collection<Comment> tickets;
}
