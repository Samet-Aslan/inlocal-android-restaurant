package com.inlocal.restaurantapp.ui.comment.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.comment.model.CommentData;
import com.inlocal.restaurantapp.ui.comment.model.CommentListRequest;
import com.inlocal.restaurantapp.ui.comment.model.CommentRequestBody;
import com.inlocal.restaurantapp.ui.comment.model.RequestDeletePost;
import com.inlocal.restaurantapp.ui.comment.model.RequestPostDetails;
import com.inlocal.restaurantapp.ui.comment.model.RequestReport;
import com.inlocal.restaurantapp.ui.comment.model.ResponsePostDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestFavourites;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestLike;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.myorders.model.ExportResponse;
import com.inlocal.restaurantapp.ui.myorders.model.RequestExportFile;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMenuDetails;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class CommentViewModel extends BaseViewModel {

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> imgLogo = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>("");
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>("");
    public MutableLiveData<String> likeCount = new MutableLiveData<>();
    public MutableLiveData<String> reportResponse = new MutableLiveData<>();
    public MutableLiveData<String> deleteResponse = new MutableLiveData<>();

    public MutableLiveData<String> response = new MutableLiveData<>();

    public MutableLiveData<CommentData> commentDataReponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalFeedWalListCoun = new MutableLiveData<>();
    public MutableLiveData<Integer> currentPage = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    public MutableLiveData<MenuDetailsResponse> menuDtailsResponse = new MutableLiveData<>();
    public MutableLiveData<ResponsePostDetails> postResponse = new MutableLiveData<>();
    public MutableLiveData<ResponseCustomerDetails> responseCustomerDetails = new MutableLiveData<>();

    @Inject
    public CommentViewModel() {
    }

    public void createComment(CommentRequestBody commentRequestBody, FeedWallItem feedWallItem) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.createComment(commentRequestBody), new EmptyNetworkCallback() {
            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                response.setValue(message);
                getCommentList(0, feedWallItem.getPostId());
                Log.e("response", message);
            }
        });
    }

    public void getPostDetails(int postId) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ResponsePostDetails> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getPostDetails(new RequestPostDetails("Restaurant",postId)), new NetworkCallback<ResponsePostDetails>() {
            @Override
            public void onSuccessResponse(ResponsePostDetails response) {
                isProgressEnabled.setValue(new Event<>(false));
                postResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                postResponse.setValue(null);
            }

        });
    }

    public void getCommentList(int page, int postId) {
        isProgressEnabled.setValue(new Event<>(true));
//        isLoading.setValue(true);
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getCommentList(getCommentListRequest(page, postId)), new NetworkCallback<CommentData>() {
            @Override
            public void onSuccessResponse(CommentData response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalFeedWalListCoun.setValue(response.getTotal());
                currentPage.setValue(response.getSkip());
                commentDataReponse.setValue(response);
//                isLoading.setValue(false);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                commentDataReponse.setValue(null);
//                isLoading.setValue(false);
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
                menuDtailsResponse.setValue(null);
                menuDtailsResponse.setValue(null);
            }
        });

    }

    public void callOtherRestMenu(int restId, int menuId) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuDetails(new RequestMenuDetails(menuId,restId)), new NetworkCallback<MenuDetailsResponse>() {
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

    public void callReport(RequestReport requestReport) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.postReport(requestReport), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                reportResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    public void deletePost(Integer postId, String postUserType) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.deletePost(new RequestDeletePost(postId, postUserType)), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                deleteResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    private RequestFavourites getRequestFavourites(FeedWallItem item) {
        return new RequestFavourites(item.getPostId(), "Restaurant", item.getFavorite() ? "Favorite" : "Unfavorite");
    }

    private RequestLike getRequestLike(FeedWallItem item) {
        return new RequestLike(item.getPostId(), "Restaurant", item.getLiked() ? "Like" : "Unlike");
    }

    private CommentListRequest getCommentListRequest(int page, int postId) {
        return new CommentListRequest(page, 40, postId);
    }

}

