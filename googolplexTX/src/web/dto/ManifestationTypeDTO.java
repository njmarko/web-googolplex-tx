package web.dto;

public class ManifestationTypeDTO {
	private String name;
	private Boolean deleted;
	
	public ManifestationTypeDTO() {
		super();
	}
	public ManifestationTypeDTO(String name, Boolean deleted) {
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
	
}
