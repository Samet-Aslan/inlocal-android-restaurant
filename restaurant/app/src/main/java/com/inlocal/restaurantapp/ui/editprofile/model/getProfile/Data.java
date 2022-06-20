package com.inlocal.restaurantapp.ui.editprofile.model.getProfile;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.NotificationSettings;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;

public class Data implements Serializable {

	@SerializedName("restaurantDetails")
	private RestaurantDetails restaurantDetails;

	@SerializedName("openingHours")
	private List<OpeningHoursItem> openingHours;

	@SerializedName("notificationSettings")
	private NotificationSettings notificationSettings;

	public void setRestaurantDetails(RestaurantDetails restaurantDetails){
		this.restaurantDetails = restaurantDetails;
	}

	public RestaurantDetails getRestaurantDetails(){
		return restaurantDetails;
	}

	public void setOpeningHours(List<OpeningHoursItem> openingHours){
		this.openingHours = openingHours;
	}

	public List<OpeningHoursItem> getOpeningHours(){
		return openingHours;
	}

	public void setNotificationSettings(NotificationSettings notificationSettings){
		this.notificationSettings = notificationSettings;
	}

	public NotificationSettings getNotificationSettings(){
		return notificationSettings;
	}
}