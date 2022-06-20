package com.inlocal.restaurantapp.ui.changepassword.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangePasswordResponse implements Serializable {

	@SerializedName("code")
	@Expose
	private Integer code;

	@SerializedName("success")
	@Expose
	private Boolean success;

	@SerializedName("message")
	@Expose
	private String message;

	public void setCode(Integer code){
		this.code = code;
	}

	public Integer getCode(){
		return code;
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