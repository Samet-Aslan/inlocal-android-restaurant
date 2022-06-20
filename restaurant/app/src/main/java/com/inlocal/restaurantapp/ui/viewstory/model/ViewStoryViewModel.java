package com.inlocal.restaurantapp.ui.viewstory.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.viewstory.viewmodel.RequestDeleteStory;
import com.inlocal.restaurantapp.util.Event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import javax.inject.Inject;

public class ViewStoryViewModel extends BaseViewModel {

    public MutableLiveData<String> deleteResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();

    @Inject
    public ViewStoryViewModel() {
    }


    public void callDeleteStory(RequestDeleteStory request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.storyDelete(request), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                deleteResponse.setValue(error);
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


}