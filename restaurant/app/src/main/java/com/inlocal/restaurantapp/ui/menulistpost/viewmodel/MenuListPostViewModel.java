package com.inlocal.restaurantapp.ui.menulistpost.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

public class MenuListPostViewModel extends BaseViewModel {

    public MutableLiveData<Integer> skip = new MutableLiveData<>(0);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<List<CateListModel>> cateModel = new MutableLiveData<>();
    public MutableLiveData<MenuListResponseModel> menuListResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalListRecord = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalMenuItemRecord = new MutableLiveData<>(0);
    public MutableLiveData<MenuItem> selectedMenuItem = new MutableLiveData<>();
    public MutableLiveData<CateListModel> selectedCateListModel = new MutableLiveData<>();
    public MutableLiveData<String> deleteResponse = new MutableLiveData<>();

    @Inject
    public MenuListPostViewModel() {

    }


    public void deleteMenuItem(View v) {
        callDeleteMenuItemAPI();
    }

    /*public void getCuisineList(int page) {
        callCuisineListing(page);
    }*/
    public void getCategoryList(int page) {
        callCategoryListing(page);
    }

    public void getMenuItems(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<MenuListResponseModel> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuItemList(getListRequest(page)), new NetworkCallback<MenuListResponseModel>() {
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

    private RequestList getListRequest(int page) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, selectedCateListModel.getValue().getId());
    }

    private void callCategoryListing(int page) {
        //isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<CateListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuList(getCatListRequest(page)), new NetworkCallback<CateListResponse>() {
            @Override
            public void onSuccessResponse(CateListResponse response) {
                //isProgressEnabled.setValue(new Event<>(false));
                totalListRecord.setValue(response.getTotal());
                cateModel.setValue(response.getCateListing());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                //  isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });


    }

    private void callDeleteMenuItemAPI() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.deleteMenuItem(getDeleteMenuItem()), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                deleteResponse.setValue(message);
            }
        });


    }

    private RequestList getCatListRequest(int page) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
    }

    private MenuItem getDeleteMenuItem() {
        return selectedMenuItem.getValue();
    }
}