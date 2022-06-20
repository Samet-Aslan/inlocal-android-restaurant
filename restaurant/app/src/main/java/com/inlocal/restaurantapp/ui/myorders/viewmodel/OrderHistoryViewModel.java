package com.inlocal.restaurantapp.ui.myorders.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.favorites.model.RequesFavouritesList;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.RequestOrderList;
import com.inlocal.restaurantapp.ui.myorders.model.DateRange;
import com.inlocal.restaurantapp.ui.myorders.model.ExportResponse;
import com.inlocal.restaurantapp.ui.myorders.model.OrderHistoryResponse;
import com.inlocal.restaurantapp.ui.myorders.model.RequestExportFile;
import com.inlocal.restaurantapp.ui.notifications.model.NotificationListResponse;
import com.inlocal.restaurantapp.ui.notifications.model.RequestReadNotification;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class OrderHistoryViewModel extends BaseViewModel {

    public MutableLiveData<Integer> skip = new MutableLiveData<>(0);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<OrderHistoryResponse> orderHistoryListResponse = new MutableLiveData<>();
    public MutableLiveData<ExportResponse> exportResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalListRecord = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> recodFound = new MutableLiveData<>(false);

    @Inject
    public OrderHistoryViewModel() {

    }

    public void getOrderHistoryList(int page, DateRange dateRange) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<OrderHistoryResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getOrderHistory(getListRequest(page, dateRange)), new NetworkCallback<OrderHistoryResponse>() {
            @Override
            public void onSuccessResponse(OrderHistoryResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalListRecord.setValue(response.getTotal());
                orderHistoryListResponse.setValue(response);
                recodFound.setValue(response.getOrderHistory().size() > 0 ? true : false);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
                orderHistoryListResponse.setValue(null);
                recodFound.setValue(false);
            }

        });
    }

    public void getExportResponse() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<ExportResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getExportResponse(new RequestExportFile("Restaurant")), new NetworkCallback<ExportResponse>() {
            @Override
            public void onSuccessResponse(ExportResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                exportResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                exportResponse.setValue(null);
            }

        });
    }

    private RequestOrderList getListRequest(int page, DateRange dateRange) {
        return new RequestOrderList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE,
                "Restaurant", dateRange);
    }
}