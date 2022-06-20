package com.inlocal.restaurantapp.service.network;

import com.inlocal.restaurantapp.base.BaseResponse;

public interface NetworkCallback<T> {
    void onSuccessResponse(T response);

    void onErrorResponse(String error, int errorCode, boolean isNetworkErro);


}
