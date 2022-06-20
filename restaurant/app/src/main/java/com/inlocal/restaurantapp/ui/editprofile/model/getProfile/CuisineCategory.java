package com.inlocal.restaurantapp.ui.editprofile.model.getProfile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CuisineCategory implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}
}