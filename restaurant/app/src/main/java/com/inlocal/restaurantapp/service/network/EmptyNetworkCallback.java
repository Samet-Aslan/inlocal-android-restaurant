package com.inlocal.restaurantapp.service.network;

public interface EmptyNetworkCallback<T> {
    void onErrorResponse(String error, int errorCode, boolean isNetworkErro);

    void onEmptySuccessRespon(String message, int code, boolean isNetworkErro);

}
