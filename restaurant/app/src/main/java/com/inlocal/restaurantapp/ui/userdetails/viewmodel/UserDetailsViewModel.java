package com.inlocal.restaurantapp.ui.userdetails.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.CustomerDetails;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.ui.userdetails.model.CustomerPostListResponse;
import com.inlocal.restaurantapp.ui.userdetails.model.ReuqestCustomerPostList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import java.io.Serializable;

import javax.inject.Inject;

public class UserDetailsViewModel extends BaseViewModel {
    public MutableLiveData<CustomerDetails> customerDetails = new MutableLiveData<>();
    public MutableLiveData<CustomerPostListResponse> custPostListResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalPostListCount = new MutableLiveData<>();
    public MutableLiveData<Boolean> follow = new MutableLiveData<>(false);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<ResponseCustomerDetails> responseCustomerDetails = new MutableLiveData<>();

    @Inject
    public UserDetailsViewModel() {
    }

    public void changeFollowStatus() {
        follow.setValue(!follow.getValue());
        makeFollower();
    }

    public void getCustomerDetails(int customerId) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ResponseCustomerDetails> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getCustomerDetails(new RequestCustomerDetails(customerId+"","Restaurant")), new NetworkCallback<ResponseCustomerDetails>() {
            @Override
            public void onSuccessResponse(ResponseCustomerDetails response) {
                isProgressEnabled.setValue(new Event<>(false));
                responseCustomerDetails.setValue(response);
                customerDetails.setValue(response.getCustomerDetails());
                getCustomerPostList(0);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                responseCustomerDetails.setValue(null);
            }

        });
    }

    public void makeFollower(){
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.requestFollower(getRequestFollower()), new EmptyNetworkCallback() {
            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //followerResponse.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                // followerResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    public void getCustomerPostList(int page){
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall<CustomerPostListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getCustomerPost(getRequestCustomerPostList(page)), new NetworkCallback<CustomerPostListResponse>() {
            @Override
            public void onSuccessResponse(CustomerPostListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal());
                custPostListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                custPostListResponse.setValue(null);
            }

        });
    }

    private RequestFollowe getRequestFollower() {
        return new RequestFollowe(customerDetails.getValue().getCustomerId(),"Customer",follow.getValue()?"Follow":"Unfollow","Restaurant");
    }

    private ReuqestCustomerPostList getRequestCustomerPostList(int page) {
        return new ReuqestCustomerPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE,customerDetails.getValue().getCustomerId(),"Restaurant");
    }
}
