package model;

public class Address {
	private String number;
	private String city;
	private Integer zipCode;
	private String street;

	public Address() {
		super();
	}

	public Address(String number, String city, Integer zipCode, String street) {
		super();
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

	@Override
	public String toString() {
		return "Address [number=" + number + ", city=" + city + ", zipCode=" + zipCode + ", street=" + street + "]";
	}

}
