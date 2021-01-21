package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Salesman extends User {

	public Collection<Manifestation> manifestations;

	public Salesman() {
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
	 */
	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted) {

		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.manifestations = new ArrayList<Manifestation>();
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
	 */
	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, false, false);
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
	 * @param manifestation
	 */
	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted,
			Collection<Manifestation> manifestation) {
		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.manifestations = manifestation;
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
	 * @param manifestation
	 */
	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Collection<Manifestation> manifestation) {
		this(username, password, firstName, lastName, gender, birthDate, userRole, false, false, manifestation);
	}

	public Collection<Manifestation> getManifestation() {
		return manifestations;
	}

	public void setManifestation(Collection<Manifestation> manifestation) {
		this.manifestations = manifestation;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
