package web.dto;

public class ManifestationTypeDTO {
	private String name;

	public ManifestationTypeDTO() {
		super();
	}

	/**
	 * @param name
	 */
	public ManifestationTypeDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ManifestationTypeDTO [name=" + name + "]";
	}

}
