package model;

import java.time.LocalDate;
import java.util.Collection;

import model.enumerations.Gender;
import model.enumerations.UserRole;

public class Salesman extends User {

	public Collection<Manifestation> manifestation;

	public Salesman() {
		super();
	}

	public Salesman(Collection<Manifestation> manifestation) {
		super();
		this.manifestation = manifestation;
	}

	public Salesman(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, UserRole userRole, Boolean deleted) {

		super(username, password, firstName, lastName, gender, birthDate, userRole, deleted);

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
