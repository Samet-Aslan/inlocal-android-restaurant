package com.inlocal.restaurantapp.ui.notificationssettings.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.NotificationSettings;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class NotificationSettingsViewModel extends BaseViewModel {

    public MutableLiveData<NotificationSettings> notificationSettingsResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> post = new MutableLiveData<>();
    public MutableLiveData<String> stories = new MutableLiveData<>();
    public MutableLiveData<String> comments = new MutableLiveData<>();
    public MutableLiveData<String> followers = new MutableLiveData<>();
    public MutableLiveData<String> likes = new MutableLiveData<>();
    public MutableLiveData<String> orders = new MutableLiveData<>();
    public MutableLiveData<String> bookings = new MutableLiveData<>();
    public MutableLiveData<String> payments = new MutableLiveData<>();

    @Inject
    public NotificationSettingsViewModel() {
    }


    public void setPostChnaged(CompoundButton button, boolean isChecked) {
        post.setValue(isChecked ? "1" : "0");
    }

    public void setStoriesChnaged(CompoundButton button, boolean isChecked) {
        stories.setValue(isChecked ? "1" : "0");
    }

    public void setCommentsChnaged(CompoundButton button, boolean isChecked) {
        comments.setValue(isChecked ? "1" : "0");
    }

    public void setFollowersChnaged(CompoundButton button, boolean isChecked) {
        followers.setValue(isChecked ? "1" : "0");
    }

    public void setLikesChnaged(CompoundButton button, boolean isChecked) {
        likes.setValue(isChecked ? "1" : "0");
    }

    public void setOrdersChnaged(CompoundButton button, boolean isChecked) {
        orders.setValue(isChecked ? "1" : "0");
    }

    public void setBookingsChnaged(CompoundButton button, boolean isChecked) {
        bookings.setValue(isChecked ? "1" : "0");
    }

    public void setPaymentsChnaged(CompoundButton button, boolean isChecked) {
        payments.setValue(isChecked ? "1" : "0");
    }

    public void getNotificationSettings() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<NotificationSettings> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getNotificationSettings(), new NetworkCallback<NotificationSettings>() {
            @Override
            public void onSuccessResponse(NotificationSettings response) {
                isProgressEnabled.setValue(new Event<>(false));
                notificationSettingsResponse.setValue(response);
                post.setValue(response.getPost());
                stories.setValue(response.getStories());
                comments.setValue(response.getComments());
                followers.setValue(response.getFollowers());
                likes.setValue(response.getLikes());
                orders.setValue(response.getOrders());
                bookings.setValue(response.getBookings());
                payments.setValue(response.getPayment());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                notificationSettingsResponse.setValue(null);
            }

        });
    }

    public void updateViewPost(View view){

    }

    public void updateNotificationSettings(View view) {
        notificationSettingsResponse.getValue().setPost(post.getValue());
        notificationSettingsResponse.getValue().setStories(stories.getValue());
        notificationSettingsResponse.getValue().setComments(comments.getValue());
        notificationSettingsResponse.getValue().setFollowers(followers.getValue());
        notificationSettingsResponse.getValue().setLikes(likes.getValue());
        notificationSettingsResponse.getValue().setOrders(orders.getValue());
        notificationSettingsResponse.getValue().setBookings(bookings.getValue());
        notificationSettingsResponse.getValue().setPayment(payments.getValue());
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateNotificationSettings(notificationSettingsResponse.getValue()), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(message);
            }
        });
    }
}