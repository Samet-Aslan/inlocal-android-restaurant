package com.inlocal.restaurantapp.ui.addaddress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressDetailsResponse implements Serializable {


	@SerializedName("data")
	private AddressData data;



	public void setData(AddressData data){
		this.data = data;
	}

	public AddressData getData(){
		return data;
	}


}