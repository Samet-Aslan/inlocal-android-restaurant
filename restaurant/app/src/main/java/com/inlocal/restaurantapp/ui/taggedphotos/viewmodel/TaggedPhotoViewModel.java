package com.inlocal.restaurantapp.ui.taggedphotos.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.CustomerDetails;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestFavourites;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestLike;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.MyPostResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestInsightListing;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMenuDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMyPostList;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestRestauruntDetails;
import com.inlocal.restaurantapp.ui.taggedphotos.model.ResponseTopPostList;
import com.inlocal.restaurantapp.ui.userdetails.model.CustomerPostListResponse;
import com.inlocal.restaurantapp.ui.userdetails.model.ReuqestCustomerPostList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TaggedPhotoViewModel extends BaseViewModel {
    public MutableLiveData<CustomerDetails> customerDetails = new MutableLiveData<>();
    public MutableLiveData<RestaurantDetails> restaurantDetails = new MutableLiveData<>();
    public MutableLiveData<Integer> restId = new MutableLiveData<>();
    public MutableLiveData<CustomerPostListResponse> custPostListResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalPostListCount = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<MenuDetailsResponse> menuDtailsResponse = new MutableLiveData<>();
    public MutableLiveData<MyPostResponse> postListResponse = new MutableLiveData<>();
    public MutableLiveData<ResponseTopPostList> responseTopPostList = new MutableLiveData<>();

    @Inject
    public TaggedPhotoViewModel() {
    }

    public void getCustomerPostList(int page) {
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

    public void getCustomerTopPostList(int page, int postId, String postType) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<FeedWallItem> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getPostViewLis(getPostListRequest(page, postId, postType)), new NetworkCallback<FeedWallItem>() {
            @Override
            public void onSuccessResponse(FeedWallItem response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal() + 1);
                List<FeedWallItem> feedWallItemList = response.getPostList();
                if (feedWallItemList != null && response.getSkip() == 0) {
                    feedWallItemList.add(0, response);
                }
                //postListResponse = new MutableLiveData<MyPostResponse>();
                CustomerPostListResponse myPostResponse = new CustomerPostListResponse();
                myPostResponse.setCustomerPostList(feedWallItemList);
                myPostResponse.setLimit(response.getLimit());
                myPostResponse.setSkip(response.getSkip());
                myPostResponse.setTotal(response.getTotal() + 1);
                custPostListResponse.setValue(myPostResponse);
                //responseTopPostList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //   errorFromServer.setValue(error);
                responseTopPostList.setValue(null);
            }

        });
    }

    public void getRestMenuPostViewList(int page, int postId, String postType) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<FeedWallItem> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestMenuPostViewLis(getPostListRequest(page, postId, postType)), new NetworkCallback<FeedWallItem>() {
            @Override
            public void onSuccessResponse(FeedWallItem response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal() + 1);
                List<FeedWallItem> feedWallItemList = response.getPostList();
                if (feedWallItemList != null && response.getSkip() == 0) {
                    feedWallItemList.add(0, response);
                }
                //postListResponse = new MutableLiveData<MyPostResponse>();
                CustomerPostListResponse myPostResponse = new CustomerPostListResponse();
                myPostResponse.setCustomerPostList(feedWallItemList);
                myPostResponse.setLimit(response.getLimit());
                myPostResponse.setSkip(response.getSkip());
                myPostResponse.setTotal(response.getTotal() + 1);
                custPostListResponse.setValue(myPostResponse);
                //responseTopPostList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //   errorFromServer.setValue(error);
                responseTopPostList.setValue(null);
            }

        });
    }

    public void callMenuDetails(int id) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuItemDetails(new RequestById(id)), new NetworkCallback<MenuDetailsResponse>() {
            @Override
            public void onSuccessResponse(MenuDetailsResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                menuDtailsResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                menuDtailsResponse.setValue(null);
            }

        });

    }

    public void callOtherRestMenu(int restId, int menuId) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuDetails(new RequestMenuDetails(menuId, restId)), new NetworkCallback<MenuDetailsResponse>() {
            @Override
            public void onSuccessResponse(MenuDetailsResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                menuDtailsResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                menuDtailsResponse.setValue(null);
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
                errorFromServer.setValue(error);
                postListResponse.setValue(null);
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

    public void getPostListById(int page, int postId, String postType) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<FeedWallItem> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getPostViewLis(getPostListRequest(page, postId, postType)), new NetworkCallback<FeedWallItem>() {
            @Override
            public void onSuccessResponse(FeedWallItem response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal() + 1);
                List<FeedWallItem> feedWallItemList = response.getPostList();
                if (feedWallItemList != null && response.getSkip() == 0) {
                    feedWallItemList.add(0, response);
                }
                //postListResponse = new MutableLiveData<MyPostResponse>();
                MyPostResponse myPostResponse = new MyPostResponse();
                myPostResponse.setRestaruntPostList(feedWallItemList);
                myPostResponse.setMyPostList(feedWallItemList);
                myPostResponse.setLimit(response.getLimit());
                myPostResponse.setSkip(response.getSkip());
                myPostResponse.setTotal(response.getTotal() + 1);
                postListResponse.setValue(myPostResponse);
                //responseTopPostList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //   errorFromServer.setValue(error);
                responseTopPostList.setValue(null);
            }

        });
    }

    public void getInsightList(int page, int postId, String postType) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<FeedWallItem> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getInsightViewList(getInsightListRequest(page, postId, postType)), new NetworkCallback<FeedWallItem>() {
            @Override
            public void onSuccessResponse(FeedWallItem response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostListCount.setValue(response.getTotal() + 1);
                List<FeedWallItem> feedWallItemList = response.getPostList();
                if (feedWallItemList != null && response.getSkip() == 0) {
                    feedWallItemList.add(0, response);
                }
                //postListResponse = new MutableLiveData<MyPostResponse>();
                MyPostResponse myPostResponse = new MyPostResponse();
                myPostResponse.setRestaruntPostList(feedWallItemList);
                myPostResponse.setMyPostList(feedWallItemList);
                myPostResponse.setLimit(response.getLimit());
                myPostResponse.setSkip(response.getSkip());
                myPostResponse.setTotal(response.getTotal() + 1);
                postListResponse.setValue(myPostResponse);
                //responseTopPostList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                //   errorFromServer.setValue(error);
                responseTopPostList.setValue(null);
            }

        });
    }

    private RequestLike getRequestLike(FeedWallItem item) {
        return new RequestLike(item.getPostId(), "Restaurant", item.getLiked() ? "Like" : "Unlike");
    }

    private RequestFavourites getRequestFavourites(FeedWallItem item) {
        return new RequestFavourites(item.getPostId(), "Restaurant", item.getFavorite() ? "Favorite" : "Unfavorite");
    }

    private ReuqestCustomerPostList getRequestCustomerPostList(int page) {
        return new ReuqestCustomerPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, customerDetails.getValue().getCustomerId(), "Restaurant");
    }

    private RequestRestauruntDetails getRequestRestauruntDetails(int page) {
        return new RequestRestauruntDetails(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, restaurantDetails.getValue().getRestaurantId() + "", "Restaurant");
    }

    private RequestRestauruntDetails getPostListRequest(int page, int postId, String postType) {
        return new RequestRestauruntDetails(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, postId, postType, "Restaurant");
    }

    private RequestInsightListing getInsightListRequest(int page, int postId, String postType) {
        Log.e("restId", restaurantDetails.getValue().getRestaurantId() + "");
        return new RequestInsightListing(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, postId, restaurantDetails.getValue().getRestaurantId(), postType, "Restaurant");
    }

    private RequestMyPostList getRequestMyPost(int page, boolean isPost) {
        if (isPost) {
            return new RequestMyPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant", null);
        } else {
            return new RequestMyPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, null, "Restaurant");
        }
    }
}
