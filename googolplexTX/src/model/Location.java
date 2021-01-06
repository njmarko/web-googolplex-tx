package model;

public class Location {
	private Double longitude;
	private Double latitude;
	private String number;
	private String city;
	private Integer zipCode;
	private String street;

	public Location() {
		super();
	}

	public Location(Double longitude, Double latitude, String number, String city, Integer zipCode, String street) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Location [longitude=" + longitude + ", latitude=" + latitude + ", number=" + number + ", city=" + city
				+ ", zipCode=" + zipCode + ", street=" + street + "]";
	}

}
