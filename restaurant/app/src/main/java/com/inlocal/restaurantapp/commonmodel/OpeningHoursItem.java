package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class OpeningHoursItem implements Serializable {

	@SerializedName("starttime")
	private String openAt;

	@SerializedName("isOpen")
	private String isOpen;

	@SerializedName("closetime")
	private String closeAt;

	@SerializedName("weekday_name")
	private String weekdayName;

	public OpeningHoursItem(String day, String isOpen, String openAt, String closeAt){
		this.weekdayName = day;
		this.isOpen = isOpen;
		this.openAt = openAt;
		this.closeAt = closeAt;

	}

	public void setStartTime(String startTime){
		this.openAt = startTime;
	}

	public String getStartTime(){
		return openAt;
	}

	public void setIsOpen(String isOpen){
		this.isOpen = isOpen;
	}

	public String getIsOpen(){
		return isOpen;
	}

	public void setCloseTime(String closeTime){
		this.closeAt = closeTime;
	}

	public String getCloseTime(){
		return closeAt;
	}

	public void setWeekdayName(String day){
		this.weekdayName = day;
	}

	public String getWeekdayName(){
		return weekdayName;
	}
}