package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.RequestOrderList;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.ResponseDeliveryOrder;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class OrderDeliveryViewModel extends BaseViewModel {

    public MutableLiveData<String> listType = new MutableLiveData<>("dine_in");
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<Integer> totalDeliverOrderListCount = new MutableLiveData<>();
    public MutableLiveData<Integer> totalDineInOrderListCount = new MutableLiveData<>();
    public MutableLiveData<ResponseDeliveryOrder> resposeDeliverOrderList = new MutableLiveData<>();
    public MutableLiveData<ResponseDeliveryOrder> resposeDineInOrderList = new MutableLiveData<>();

    @Inject
    public OrderDeliveryViewModel() {

    }

    public void changeData() {
        if (listType.getValue().equals("dine_in")) {
            listType.setValue("delivery");
        } else {
            listType.setValue("dine_in");
        }
    }

    public void getDeliverOrderList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getDeliverOrder(requestOrderList(page)), new NetworkCallback<ResponseDeliveryOrder>() {
            @Override
            public void onSuccessResponse(ResponseDeliveryOrder response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalDeliverOrderListCount.setValue(response.getTotal());
                resposeDeliverOrderList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                resposeDeliverOrderList.setValue(null);
                Log.e("error", error);
            }

        });
    }

    public void getDeliverOrderListNoLoader(int page) {
       // isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getDeliverOrder(requestOrderList(page)), new NetworkCallback<ResponseDeliveryOrder>() {
            @Override
            public void onSuccessResponse(ResponseDeliveryOrder response) {
              //  isProgressEnabled.setValue(new Event<>(false));
                totalDeliverOrderListCount.setValue(response.getTotal());
                resposeDeliverOrderList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
              //  isProgressEnabled.setValue(new Event<>(false));
               // errorFromServer.setValue(error);
                resposeDeliverOrderList.setValue(null);
                Log.e("error", error);
            }

        });
    }

    public void getDineInOrdeList(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getDineInOrdeList(requestOrderList(page)), new NetworkCallback<ResponseDeliveryOrder>() {
            @Override
            public void onSuccessResponse(ResponseDeliveryOrder response) {
                isProgressEnabled.setValue(new Event<>(false));
                totalDineInOrderListCount.setValue(response.getTotal());
                resposeDineInOrderList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                resposeDineInOrderList.setValue(null);
                Log.e("error", error);
            }

        });
    }

    public void getDineInOrdeListNoLoader(int page) {
        //isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getDineInOrdeList(requestOrderList(page)), new NetworkCallback<ResponseDeliveryOrder>() {
            @Override
            public void onSuccessResponse(ResponseDeliveryOrder response) {
                //isProgressEnabled.setValue(new Event<>(false));
                totalDineInOrderListCount.setValue(response.getTotal());
                resposeDineInOrderList.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
               // isProgressEnabled.setValue(new Event<>(false));
               // errorFromServer.setValue(error);
                resposeDineInOrderList.setValue(null);
                Log.e("error", error);
            }

        });
    }

    private RequestOrderList requestOrderList(int page){
        return new RequestOrderList(Constants.DEFAULT_PAGE_SIZE * page, 15/*Constants.DEFAULT_PAGE_SIZE*/, "Restaurant");
    }

}