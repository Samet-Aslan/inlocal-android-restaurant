package com.inlocal.restaurantapp.ui.categorylist.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.addcategory.model.RequestAddCategory;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListResponse;
import com.inlocal.restaurantapp.ui.categorylist.model.RequestCateList;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class CategoryListViewModel extends BaseViewModel {

    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<CateListResponse> cateListResponse = new MutableLiveData<>();
    public MutableLiveData<CateListModel> cateModel = new MutableLiveData<>();
    public MutableLiveData<String> deleteResponse = new MutableLiveData<>();

    @Inject
    public CategoryListViewModel() {
    }

    public void getCateList(int page) {
        callListing(page);
    }

    public void deleteCategory(View v) {
        deleteCategory();
    }

    private void deleteCategory() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.deleteMenuCategory(getDeleteCategoryRequest()), new EmptyNetworkCallback() {

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

    private void callListing(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuList(getListRequest(page)), new NetworkCallback<CateListResponse>() {
            @Override
            public void onSuccessResponse(CateListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                cateListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                cateListResponse.setValue(null);
            }

        });


    }

    private RequestList getListRequest(int page) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
    }

    private CateListModel getDeleteCategoryRequest() {
        return cateModel.getValue();
    }
}