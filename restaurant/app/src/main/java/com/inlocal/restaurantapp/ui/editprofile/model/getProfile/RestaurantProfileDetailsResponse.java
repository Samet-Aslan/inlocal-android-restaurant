package com.inlocal.restaurantapp.ui.editprofile.model.getProfile;

import com.google.gson.annotations.SerializedName;

public class RestaurantProfileDetailsResponse{

	@SerializedName("code")
	private Integer code;

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private Boolean success;

	@SerializedName("message")
	private String message;

	public void setCode(Integer code){
		this.code = code;
	}

	public Integer getCode(){
		return code;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setSuccess(Boolean success){
		this.success = success;
	}

	public Boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}