package com.inlocal.restaurantapp.ui.addaddress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressData implements Serializable {

	@SerializedName("zipcode")
	private String zipcode;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("country")
	private String country;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("restaurant_id")
	private Integer restaurantId;

	@SerializedName("location")
	private Location location;

	@SerializedName("landmark")
	private String landmark;

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setRestaurantId(Integer restaurantId){
		this.restaurantId = restaurantId;
	}

	public Integer getRestaurantId(){
		return restaurantId;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setLandmark(String landmark){
		this.landmark = landmark;
	}

	public String getLandmark(){
		return landmark;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}