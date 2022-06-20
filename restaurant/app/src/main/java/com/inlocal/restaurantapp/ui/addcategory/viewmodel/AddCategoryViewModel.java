package com.inlocal.restaurantapp.ui.addcategory.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.addcategory.model.RequestAddCategory;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class AddCategoryViewModel extends BaseViewModel {

    public MutableLiveData<String> cateName = new MutableLiveData<>("");
    public MutableLiveData<CateListModel> cateModel = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> addMenuResponse = new MutableLiveData<>();
    public MutableLiveData<String> editMenuResponse = new MutableLiveData<>();

    @Inject
    public AddCategoryViewModel() {
    }

    public void addCategory(View v) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (ValidationUtil.isFieldEmpty(cateName.getValue())) {
            errorFromServer.setValue(v.getContext().getResources().getString(R.string.empty_field));
            return;
        }
        callAddCategotyApi();

    }

    public void updateCategory(View v) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (ValidationUtil.isFieldEmpty(cateModel.getValue().getName())) {
            errorFromServer.setValue(v.getContext().getResources().getString(R.string.empty_field));
            return;
        }
        callUpdateCateApi();
    }

    private void callAddCategotyApi() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.addMenuCategory(getCategoryRequest()), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                addMenuResponse.setValue(message);
            }
        });


    }

    private void callUpdateCateApi() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.editMenuCategory(getEditCategoryRequest()), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                editMenuResponse.setValue(message);
            }
        });


    }

    private RequestAddCategory getCategoryRequest() {
        return new RequestAddCategory(cateName.getValue());
    }

    private CateListModel getEditCategoryRequest() {
        return cateModel.getValue();
    }
}