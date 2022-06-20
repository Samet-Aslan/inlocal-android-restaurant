package com.inlocal.restaurantapp.ui.statistics.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.ui.statistics.model.RequestStatistics;
import com.inlocal.restaurantapp.ui.statistics.model.ResponseBestSeller;
import com.inlocal.restaurantapp.ui.statistics.model.ResponseStatic;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class StaticViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> networkError = new MutableLiveData<>(false);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<ResponseStatic> responseStatic = new MutableLiveData<>();
    public MutableLiveData<ResponseBestSeller> reseponseBestSeller = new MutableLiveData<>();

    private MutableLiveData<Event<String>> _navigateToDashboard = new MutableLiveData<Event<String>>();
    public LiveData<Event<String>> navigateToDashboard = _navigateToDashboard;

    @Inject
    public StaticViewModel() {
    }

    public void getStatics(String timeSpam) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ResponseStatic> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getStatics(new RequestStatistics(timeSpam)), new NetworkCallback<ResponseStatic>() {
            @Override
            public void onSuccessResponse(ResponseStatic response) {
                isProgressEnabled.setValue(new Event<>(false));
                responseStatic.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });
    }

    public void getBestSeller() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ResponseBestSeller> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getBestSeller(), new NetworkCallback<ResponseBestSeller>() {
            @Override
            public void onSuccessResponse(ResponseBestSeller response) {
                isProgressEnabled.setValue(new Event<>(false));
                reseponseBestSeller.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                reseponseBestSeller.setValue(null);
            }

        });
    }


}