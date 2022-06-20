package com.inlocal.restaurantapp.ui.restaurantdetails.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.comment.model.CommentRequestBody;
import com.inlocal.restaurantapp.ui.comment.model.RequestReport;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.Data;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.favorites.model.RequesFavouritesList;
import com.inlocal.restaurantapp.ui.followers.model.FollowersListingResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.CusisineListResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.MyPostResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.MyStoryListResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestBlockUser;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMyPostList;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestRestauruntDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ResponseRestauruntDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ReuqestInsightList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class RestroDetailsViewModel extends BaseViewModel {
    public MutableLiveData<Integer> gridType = new MutableLiveData<>(1);
    public MutableLiveData<Boolean> follow = new MutableLiveData<>(false);
    public MutableLiveData<Data> restaurantProfileDetailsResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MyPostResponse> postListResponse = new MutableLiveData<>();
    public MutableLiveData<MyPostResponse> insightListResponse = new MutableLiveData<>();
    public MutableLiveData<MyStoryListResponse> storyListResponse = new MutableLiveData<>();
    public MutableLiveData<RestaurantDetails> restauruntDetails = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> bloackReponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalPostListCount = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalInsightListCount = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalStoryListCount = new MutableLiveData<>(0);
    public MutableLiveData<Integer> restId = new MutableLiveData<>(-1);
    public MutableLiveData<String> followerResponse = new MutableLiveData<>("");
    public MutableLiveData<Boolean> bothListSet = new MutableLiveData<>(false);

    @Inject
    public RestroDetailsViewModel() {
    }

    public void changeImagesGrid(int type) {
        gridType.setValue(type);
    }

    public void changeFollowStatus() {
        follow.setValue(!follow.getValue());
        makeFollower();
    }

    public void getProfileDetails() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getProfileDetails(), new NetworkCallback<Data>() {
            @Override
            public void onSuccessResponse(Data response) {
                isProgressEnabled.setValue(new Event<>(false));
                RestaurantDetails restaurantDetails = response.getRestaurantDetails();
                if (restaurantDetails.getRestaurantId() == null && restaurantDetails.getId() != null) {
                    response.getRestaurantDetails().setRestaurantId(restaurantDetails.getId());
                } else if (restaurantDetails.getId() == null && restaurantDetails.getRestaurantId() != null) {
                    response.getRestaurantDetails().setId(restaurantDetails.getRestaurantId());
                }
                restaurantProfileDetailsResponseMutableLiveData.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
            }

        });
    }

    public void makeFollower() {
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

    public void getMyPostList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMyPostList(getRequestMyPost(page, true)), new NetworkCallback<MyPostResponse>() {
            @Override
            public void onSuccessResponse(MyPostResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal());
                postListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //errorFromServer.setValue(error);
                postListResponse.setValue(null);
            }

        });
    }

    public void getMyInsightsList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMyInsightsList(getRquestInsightsList(page)), new NetworkCallback<MyPostResponse>() {
            @Override
            public void onSuccessResponse(MyPostResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalInsightListCount.setValue(response.getTotal());
                insightListResponse.setValue(response);

            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
               // errorFromServer.setValue(error);
                totalInsightListCount.setValue(null);
            }

        });
    }

    public void getMyStoryList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMyStoryList(getRequestMyPost(page, false)), new NetworkCallback<MyStoryListResponse>() {
            @Override
            public void onSuccessResponse(MyStoryListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalStoryListCount.setValue(response.getTotal());
                storyListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //errorFromServer.setValue(error);
                storyListResponse.setValue(null);
            }

        });
    }

    public void getRestauruntDetails(int id) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestauruntDetails(new RequestRestauruntDetails(id + "", "Restaurant")), new NetworkCallback<ResponseRestauruntDetails>() {
            @Override
            public void onSuccessResponse(ResponseRestauruntDetails response) {
                isProgressEnabled.setValue(new Event<>(false));
                restauruntDetails.setValue(response.getRestaurantDetails());
                follow.setValue(response.getRestaurantDetails().getIsFollow());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                Log.e("error", error);
                errorFromServer.setValue(error);
                restauruntDetails.setValue(null);
            }

        });
    }

    public void getRestPostList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestauruntPostList(getRequestRestauruntDetails(page)), new NetworkCallback<MyPostResponse>() {
            @Override
            public void onSuccessResponse(MyPostResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal());
                postListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //   errorFromServer.setValue(error);
                postListResponse.setValue(null);
            }

        });
    }

    public void getRestStoryList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestauruntStoryList(getRequestRestauruntDetails(page)), new NetworkCallback<MyStoryListResponse>() {
            @Override
            public void onSuccessResponse(MyStoryListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalStoryListCount.setValue(response.getTotal());
                storyListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                // errorFromServer.setValue(error);
                storyListResponse.setValue(null);
            }

        });
    }

    public void callBlockUser(RequestBlockUser request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.bloackUser(request), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                bloackReponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    private RequestMyPostList getRequestMyPost(int page, boolean isPost) {
        if (isPost) {
            return new RequestMyPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant", null);
        } else {
            return new RequestMyPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, null, "Restaurant");
        }
    }

    private RequestRestauruntDetails getRequestRestauruntDetails(int page) {
        return new RequestRestauruntDetails(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, restId.getValue() + "", "Restaurant");
    }

    private ReuqestInsightList getRquestInsightsList(int page) {
        return new ReuqestInsightList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant", restId.getValue());
    }

    private RequestFollowe getRequestFollower() {
        return new RequestFollowe(restId.getValue(), "Restaurant", follow.getValue() ? "Follow" : "Unfollow", "Restaurant");
    }

}
