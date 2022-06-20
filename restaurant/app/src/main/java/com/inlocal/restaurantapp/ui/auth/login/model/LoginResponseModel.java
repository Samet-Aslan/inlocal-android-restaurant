package com.inlocal.restaurantapp.ui.auth.login.model;

import com.inlocal.restaurantapp.commonmodel.NotificationSettings;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.commonmodel.Phone;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;

import java.util.List;

public class LoginResponseModel {

    public String token;
    public NotificationSettings notificationSettings;
    public List<OpeningHoursItem> openingHours;
    public RestaurantDetails restaurantDetails;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    public List<OpeningHoursItem> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHoursItem> openingHours) {
        this.openingHours = openingHours;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
