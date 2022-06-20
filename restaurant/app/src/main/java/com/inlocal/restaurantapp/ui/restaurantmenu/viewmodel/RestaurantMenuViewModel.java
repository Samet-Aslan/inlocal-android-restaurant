package com.inlocal.restaurantapp.ui.restaurantmenu.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import java.util.List;

import javax.inject.Inject;

public class RestaurantMenuViewModel extends BaseViewModel {

    public MutableLiveData<Integer> skip = new MutableLiveData<>(0);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<List<CateListModel>> cateModel = new MutableLiveData<>();
    public MutableLiveData<Integer> totalListRecord = new MutableLiveData<>(0);
    public MutableLiveData<MenuListResponseModel> menuListResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalMenuItemRecord = new MutableLiveData<>(0);
    public MutableLiveData<Integer> restId = new MutableLiveData<>(-1);
    public MutableLiveData<CateListModel> selectedCateListModel = new MutableLiveData<>();

    @Inject
    public RestaurantMenuViewModel() {
    }

    public void getCategoryList(int page) {
        callCategoryListing(page);
    }

    private void callCategoryListing(int page) {
        //  isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<CateListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestMenuList(getCatListRequest(page)), new NetworkCallback<CateListResponse>() {
            @Override
            public void onSuccessResponse(CateListResponse response) {
                //isProgressEnabled.setValue(new Event<>(false));
                totalListRecord.setValue(response.getTotal());
                cateModel.setValue(response.getCateListing());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                //  isProgressEnabled.setValue(new Event<>(false));
                cateModel.setValue(null);
                errorFromServer.setValue(error);
            }

        });
    }

    public void getRestMenuList(int page, int type) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<MenuListResponseModel> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuListById(getListRequest(page, type)), new NetworkCallback<MenuListResponseModel>() {
            @Override
            public void onSuccessResponse(MenuListResponseModel response) {
                isProgressEnabled.setValue(new Event<>(false));
                menuListResponse.setValue(response);
                totalMenuItemRecord.setValue(response.getTotal());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                menuListResponse.setValue(null);
            }

        });
    }

    private RequestList getListRequest(int page, int type) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, selectedCateListModel.getValue().getId(), restId.getValue(), type);
    }

    private RequestList getCatListRequest(int page) {
        RequestList requestList = new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
        requestList.setRestaurant_id(restId.getValue());
        return requestList;
    }
}
