package vn.kayter.sample_code.model;

public class Address {
	private String apartmentNumber;
	private String floor;
	private String building;
	private String streetNumber;
	private String street;
	private String city;
	private String country;
	private Integer addressType;

	public Address(String apartmentNumber, String floor, String building, String streetNumber, String street,
			String city, String country, Integer addressType) {
		super();
		this.apartmentNumber = apartmentNumber;
		this.floor = floor;
		this.building = building;
		this.streetNumber = streetNumber;
		this.street = street;
		this.city = city;
		this.country = country;
		this.addressType = addressType;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getAddressType() {
		return addressType;
	}

	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

}
