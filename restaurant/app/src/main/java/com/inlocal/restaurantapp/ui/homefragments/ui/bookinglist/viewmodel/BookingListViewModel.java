package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestUpdateOrderStatus;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.RequestResrvationStatusUpdate;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.ReservationListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.ui.myorders.model.DateRange;
import com.inlocal.restaurantapp.ui.myorders.model.OrderHistoryResponse;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class BookingListViewModel extends BaseViewModel {
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> logoutResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalListRecord = new MutableLiveData<>();
    public MutableLiveData<ReservationListResponse> reservatListResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> recordFound = new MutableLiveData<>();
    public MutableLiveData<String> updateBookingStatusResponse = new MutableLiveData<>();

    @Inject
    public BookingListViewModel() {
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

    public void getReservationList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ReservationListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getReservationList(getListRequest(page)), new NetworkCallback<ReservationListResponse>() {
            @Override
            public void onSuccessResponse(ReservationListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalListRecord.setValue(response.getTotal());
                reservatListResponse.setValue(response);
                recordFound.setValue(response.getTotal() > 0 ? true : false);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                reservatListResponse.setValue(null);
                recordFound.setValue(false);
            }

        });
    }

    public void updateOrderStatus(RequestResrvationStatusUpdate request) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<DeliveryOrderItem> networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateBookingStatus(request), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                updateBookingStatusResponse.setValue(null);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                updateBookingStatusResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    private RequestList getListRequest(int page){
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
    }
}