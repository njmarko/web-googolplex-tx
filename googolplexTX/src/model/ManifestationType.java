package model;

public class ManifestationType {

	private String name;
	private Boolean deleted;

	public ManifestationType() {
		super();
	}

	public ManifestationType(String name) {
		super();
		this.name = name;
		this.deleted = false;
	}
	
	public ManifestationType(String name, Boolean deleted) {
		super();
		this.name = name;
		this.deleted = deleted;
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
	public String toString() {
		return "ManifestationType [name=" + name + ", deleted=" + deleted + "]";
	}

}
