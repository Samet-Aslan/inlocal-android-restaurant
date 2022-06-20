package com.inlocal.restaurantapp.ui.homefragments.ui.home.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.GlobalConfig;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.followers.model.FollowersListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.FollowingListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.RequestFollowersList;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestFavourites;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestLike;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.StoryListResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMenuDetails;
import com.inlocal.restaurantapp.ui.statistics.model.RequestStatistics;
import com.inlocal.restaurantapp.ui.statistics.model.ResponseStatic;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class HomeViewModel extends BaseViewModel {

    public MutableLiveData<Integer> totalFeedWalListCoun = new MutableLiveData<>();
    public MutableLiveData<Integer> totalStoryListCount = new MutableLiveData<>();
    public MutableLiveData<FeedWallListResponse> feedWallListResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> likeResponse = new MutableLiveData<>();
    public MutableLiveData<StoryListResponse> storyListResponse = new MutableLiveData<>();
    public MutableLiveData<GlobalConfig> getGlobalConfig = new MutableLiveData<>();
    public MutableLiveData<ResponseCustomerDetails> responseCustomerDetails = new MutableLiveData<>();

    @Inject
    public HomeViewModel() {
    }

    public void getFeedWallList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getFeedWalListResponse(getFollowerListRequest(page)), new NetworkCallback<FeedWallListResponse>() {
            @Override
            public void onSuccessResponse(FeedWallListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalFeedWalListCoun.setValue(response.getTotal());
                feedWallListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
              //  errorFromServer.setValue(error);
                feedWallListResponse.setValue(null);
            }

        });
    }


    public void getStoryList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getStoryList(getFollowerListRequest(page)), new NetworkCallback<StoryListResponse>() {
            @Override
            public void onSuccessResponse(StoryListResponse response) {
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

    public void makeLike(FeedWallItem item) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.requestLike(getRequestLike(item)), new EmptyNetworkCallback() {
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

    public void makeFavourites(FeedWallItem item) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.requestFavourites(getRequestFavourites(item)), new EmptyNetworkCallback() {
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

    private RequestFollowersList getFollowerListRequest(int page) {
        return new RequestFollowersList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant");
    }

    private RequestLike getRequestLike(FeedWallItem item) {
        return new RequestLike(item.getPostId(), "Restaurant", item.getLiked() ? "Like" : "Unlike");
    }

    private RequestFavourites getRequestFavourites(FeedWallItem item) {
        return new RequestFavourites(item.getPostId(), "Restaurant", item.getFavorite() ? "Favorite" : "Unfavorite");
    }

    public void gloablConfig() {
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<GlobalConfig> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.globalConfig(), new NetworkCallback<GlobalConfig>() {
            @Override
            public void onSuccessResponse(GlobalConfig response) {
                getGlobalConfig.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                errorFromServer.setValue(error);
                getGlobalConfig.setValue(null);
            }

        });


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
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                responseCustomerDetails.setValue(null);
            }

        });
    }

}