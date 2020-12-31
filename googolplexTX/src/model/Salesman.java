package model;

import java.util.Collection;

public class Salesman extends User {

	public Collection<Manifestation> manifestation;

	public Salesman() {
		super();
	}

	public Salesman(Collection<Manifestation> manifestation) {
		super();
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
