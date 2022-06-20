package com.inlocal.restaurantapp.ui.addaddress.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

	@SerializedName("latitue")
	private String latitue;

	@SerializedName("logitute")
	private String logitute;

	public void setLatitue(String latitue){
		this.latitue = latitue;
	}

	public String getLatitue(){
		return latitue;
	}

	public void setLogitute(String logitute){
		this.logitute = logitute;
	}

	public String getLogitute(){
		return logitute;
	}
}