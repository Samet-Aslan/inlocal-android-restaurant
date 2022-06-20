package com.inlocal.restaurantapp.ui.notifications.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

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
import com.inlocal.restaurantapp.ui.favorites.model.RequesFavouritesList;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestFavourites;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.notifications.model.NotificationListResponse;
import com.inlocal.restaurantapp.ui.notifications.model.RequestReadNotification;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import java.util.List;

import javax.inject.Inject;

public class NotificationViewModel extends BaseViewModel {

    public MutableLiveData<Integer> skip = new MutableLiveData<>(0);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> notifictionReadResponse = new MutableLiveData<>();
    public MutableLiveData<NotificationListResponse> notificationListResponse = new MutableLiveData<>();
    public MutableLiveData<Integer> totalListRecord = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> recodFound = new MutableLiveData<>(false);

    @Inject
    public NotificationViewModel() {

    }

    public void getNotificationList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<NotificationListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getNotificationList(getListRequest(page)), new NetworkCallback<NotificationListResponse>() {
            @Override
            public void onSuccessResponse(NotificationListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                notificationListResponse.setValue(response);
                response.setTotal(response.getTotal());
                totalListRecord.setValue(response.getTotal());
                recodFound.setValue(response.getTotal() > 0 ? true : false);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                notificationListResponse.setValue(null);
                recodFound.setValue(false);
            }

        });
    }

    public void readNotification(int id) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.readNotification(getReadRequest(id)), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                notifictionReadResponse.setValue(null);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                notifictionReadResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    private RequesFavouritesList getListRequest(int page) {
        return new RequesFavouritesList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE, "Restaurant");
    }

    private RequestReadNotification getReadRequest(int notificationId) {
        return new RequestReadNotification("Restaurant", notificationId);
    }
}