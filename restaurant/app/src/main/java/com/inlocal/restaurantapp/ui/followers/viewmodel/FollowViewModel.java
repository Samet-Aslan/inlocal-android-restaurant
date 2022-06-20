package com.inlocal.restaurantapp.ui.followers.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.followers.model.FollowersListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.FollowingListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.RequestFollowersList;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestOthersFollowersLIst;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ResponseOthersFollowersList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class FollowViewModel extends BaseViewModel {
    public MutableLiveData<Integer> type = new MutableLiveData<>(1);
    public MutableLiveData<Integer> totalFollowersListCount = new MutableLiveData<>();
    public MutableLiveData<FollowersListingResponse> followerListResponse = new MutableLiveData<>();
    public MutableLiveData<ResponseOthersFollowersList> responseOtherFolloweList = new MutableLiveData<>();
    public MutableLiveData<Integer> totalOthersFollowetCout = new MutableLiveData<>();
    public MutableLiveData<Integer> totalFollowingListCount = new MutableLiveData<>();
    public MutableLiveData<FollowingListingResponse> followingListResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<Boolean> followActionResponse = new MutableLiveData<>();
    public MutableLiveData<RequestOthersFollowersLIst> requestOthersFollowes = new MutableLiveData<>(new RequestOthersFollowersLIst());

    @Inject
    public FollowViewModel() {
    }

    public void changeType() {
        if (type.getValue() == 1) {
            type.setValue(2);
        } else {
            type.setValue(1);
        }
    }

    public void getFollowersList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getFollowersList(getFollowerListRequest(page)), new NetworkCallback<FollowersListingResponse>() {
            @Override
            public void onSuccessResponse(FollowersListingResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalFollowersListCount.setValue(response.getTotal());
                followerListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                followerListResponse.setValue(null);
            }

        });
    }


    public void getOtherFollowingList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getOthersFollowersList(getOtherFollowersReqest(page)), new NetworkCallback<ResponseOthersFollowersList>() {
            @Override
            public void onSuccessResponse(ResponseOthersFollowersList response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalOthersFollowetCout.setValue(response.getTotal());
                responseOtherFolloweList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                responseOtherFolloweList.setValue(null);
            }

        });
    }

    public void makeFollower(RequestFollowe request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.requestFollower(request), new EmptyNetworkCallback() {
            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //followerResponse.setValue(error);
                followActionResponse.setValue(false);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                // followerResponse.setValue(message);
                Log.e("response", message);
                followActionResponse.setValue(true);
            }
        });
    }

    public void getFollowingList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getFollowingList(getFollowerListRequest(page)), new NetworkCallback<FollowingListingResponse>() {
            @Override
            public void onSuccessResponse(FollowingListingResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalFollowingListCount.setValue(response.getTotal());
                followingListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                followingListResponse.setValue(null);
            }

        });
    }

    private RequestFollowersList getFollowerListRequest(int page) {
        return new RequestFollowersList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant");
    }

    private RequestOthersFollowersLIst getOtherFollowersReqest(int page) {
        requestOthersFollowes.getValue().setSkip(Constants.DEFAULT_PAGE_SIZE * page);
        requestOthersFollowes.getValue().setLimit(Constants.DEFAULT_PAGE_SIZE);
        return requestOthersFollowes.getValue();
    }

    private RequestFollowe getRequestFollower(int restId, String action) {
        return new RequestFollowe(restId, "", action, "Restaurant");
    }
}
