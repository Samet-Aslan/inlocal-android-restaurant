package com.inlocal.restaurantapp.ui.homefragments.ui.profile.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginRequestModel;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class ProfileViewModel extends BaseViewModel {
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> logoutResponse = new MutableLiveData<>();

    @Inject
    public ProfileViewModel() {
    }


    public void logout(View v) {
        callLogoutAPi();
    }

    private void callLogoutAPi() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.logout(), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                logoutResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }
}