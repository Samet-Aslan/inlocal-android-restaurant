package com.inlocal.restaurantapp.ui.bookingdetails.viewmodel;

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
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestOrderDetails;
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestUpdateOrderStatus;
import com.inlocal.restaurantapp.ui.bookingdetails.model.ReuqestItemStatus;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class BookingDetailsViewModel extends BaseViewModel {

    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<DeliveryOrderItem> odrderDetails = new MutableLiveData<>();
    public MutableLiveData<String> updateOrderStatusResponse = new MutableLiveData<>();

    @Inject
    public BookingDetailsViewModel() {
    }

    public void getOrderDetails(int orderId) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<DeliveryOrderItem> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getDeliverOrderDetails(new RequestOrderDetails(orderId)), new NetworkCallback<DeliveryOrderItem>() {
            @Override
            public void onSuccessResponse(DeliveryOrderItem response) {
                isProgressEnabled.setValue(new Event<>(false));
                odrderDetails.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                odrderDetails.setValue(null);
            }

        });
    }

    public void updateOrderStatus(RequestUpdateOrderStatus request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<DeliveryOrderItem> networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateOrderStatus(request), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                updateOrderStatusResponse.setValue(null);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                updateOrderStatusResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    public void updateItemStatus(ReuqestItemStatus request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateItemStatus(request), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                updateOrderStatusResponse.setValue(null);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                updateOrderStatusResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

}