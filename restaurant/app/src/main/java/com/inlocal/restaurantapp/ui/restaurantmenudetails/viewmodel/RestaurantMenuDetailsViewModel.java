package com.inlocal.restaurantapp.ui.restaurantmenudetails.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuItemDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.StoryListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMenuDetails;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.model.RequestMenuPostList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class RestaurantMenuDetailsViewModel extends BaseViewModel {

    public MutableLiveData<Integer> totalPostCount = new MutableLiveData<>();
    public MutableLiveData<MenuDetailsResponse> menuListResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<MenuDetailsResponse> menuDtailsResponse = new MutableLiveData<>();

    @Inject
    public RestaurantMenuDetailsViewModel() {
    }

    public void getMenuPostList(int menuId, int restId,int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getPostMenuList(requestMenuPostList(menuId, restId, page)), new NetworkCallback<MenuDetailsResponse>() {
            @Override
            public void onSuccessResponse(MenuDetailsResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalPostCount.setValue(response.getTotal());
                menuListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                menuListResponse.setValue(null);
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

    private RequestMenuPostList requestMenuPostList(int menuId, int restId, int page) {
        return new RequestMenuPostList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, menuId,restId);
    }


}

