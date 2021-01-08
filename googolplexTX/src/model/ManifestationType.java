package model;

public class ManifestationType {

	private String name;
	private Boolean deleted;

	public ManifestationType() {
		super();
	}

	public ManifestationType(String name, Boolean deleted) {
		super();
		this.name = name;
		this.deleted = deleted;
	}

	/**
	 * Constructor that sets deleted to FALSE
	 * 
	 * @param name
	 */
	public ManifestationType(String name) {
		this(name, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManifestationType other = (ManifestationType) obj;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ManifestationType [name=" + name + ", deleted=" + deleted + "]";
	}

}
