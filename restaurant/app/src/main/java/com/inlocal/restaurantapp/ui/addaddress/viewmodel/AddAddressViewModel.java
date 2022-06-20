package com.inlocal.restaurantapp.ui.addaddress.viewmodel;

import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.addaddress.model.updateAddress.UpdateAddressRequest;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

import okhttp3.MultipartBody;

public class AddAddressViewModel extends BaseViewModel {
    public MutableLiveData<String> houseNo = new MutableLiveData<>();
    public MutableLiveData<String> zipcode = new MutableLiveData<>("");
    public MutableLiveData<String> city = new MutableLiveData<>("");
    public MutableLiveData<String> landMark = new MutableLiveData<>("");
    public MutableLiveData<String> countrycode = new MutableLiveData<>("");
    public MutableLiveData<String> country = new MutableLiveData<>("");

    public MutableLiveData<Boolean> allOk = new MutableLiveData<>(false);


    public MutableLiveData<Boolean> houseNoError = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> zipcodeError = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> cityError = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> landMarkError = new MutableLiveData<>(false);

    public MutableLiveData<String> latitude = new MutableLiveData<>("");
    public MutableLiveData<String> longitude = new MutableLiveData<>("");
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> addressUpdateResponse = new MutableLiveData<>();


    private MutableLiveData<Event<String>> _navigateToDashboard = new MutableLiveData<Event<String>>();
    public LiveData<Event<String>> navigateToDashboard = _navigateToDashboard;

    @Inject
    public AddAddressViewModel() {
    }


    public void isAllOk() {
        Log.e("houeNo", !ValidationUtil.isFieldEmpty(houseNo.getValue()) + "");
        Log.e("city", !ValidationUtil.isFieldEmpty(city.getValue()) + "");
        Log.e("landMark", !ValidationUtil.isFieldEmpty(landMark.getValue()) + "");
        Log.e("zipcode", ValidationUtil.isZipCodeValid(zipcode.getValue()) + "");


        if (!ValidationUtil.isFieldEmpty(houseNo.getValue()) && !ValidationUtil.isFieldEmpty(city.getValue())
                /*&& !ValidationUtil.isFieldEmpty(landMark.getValue())*/ && ValidationUtil.isZipCodeValid(zipcode.getValue())) {
            allOk.setValue(true);
        } else {
            allOk.setValue(false);
        }
    }

    public void onZipCodeChange(Editable s) {
        if (ValidationUtil.isZipCodeValid(s.toString())) {
            zipcodeError.setValue(false);
        } else {
            zipcodeError.setValue(true);
        }
        isAllOk();
    }

    public void onHouseChange(Editable s) {
        if (s.toString().length() > 0) {
            houseNoError.setValue(false);
        } else {
            houseNoError.setValue(true);
        }
        isAllOk();
    }

    public void onLandMarkChange(Editable s) {
        landMarkError.setValue(false);
        /*if (s.toString().length() > 0) {
            landMarkError.setValue(false);
        } else {
            landMarkError.setValue(true);
        }*/
        isAllOk();
    }

    public void onCityChange(Editable s) {
        if (s.toString().length() > 0) {
            cityError.setValue(false);
        } else {
            cityError.setValue(true);
        }
        isAllOk();
    }

    public void updateProfileAddress() {

        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateRestaurantAddress(updateAddressRequest()), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                addressUpdateResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    UpdateAddressRequest updateAddressRequest() {
       return new UpdateAddressRequest(zipcode.getValue(),latitude.getValue(),countrycode.getValue(),houseNo.getValue(),
               longitude.getValue(),city.getValue(),landMark.getValue(),country.getValue());
    }


    public void save(View v) {
        _navigateToDashboard.setValue(new Event<>("navigate"));
    }


}