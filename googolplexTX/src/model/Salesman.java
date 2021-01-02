package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Salesman extends User {

	public Collection<Manifestation> manifestation;

	public Salesman() {
		super();
	}

	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted) {

		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.manifestation = new ArrayList<Manifestation>();
	}

	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean blocked, Boolean deleted,
			Collection<Manifestation> manifestation) {
		super(username, password, firstName, lastName, gender, birthDate, userRole, blocked, deleted);
		this.manifestation = manifestation;
	}

	public Collection<Manifestation> getManifestation() {
		return manifestation;
	}

	public void setManifestation(Collection<Manifestation> manifestation) {
		this.manifestation = manifestation;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
