package com.inlocal.restaurantapp.service.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.inlocal.restaurantapp.app.instance.Application;
import com.inlocal.restaurantapp.base.BaseEmptyResponse;
import com.inlocal.restaurantapp.base.BaseResponse;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.SessionDialogActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall<T> {

    public void makeCall(Call<BaseResponse<T>> call, NetworkCallback<T> callback) {
        if (NetworkUtil.isNetworkConnected(Application.getContext())) {
            continueCall(call, callback);
        } else {
            callback.onErrorResponse("Internet not available", 1001, true);
        }

    }

    public void makeEmptyCall(Call<BaseEmptyResponse> call, EmptyNetworkCallback<T> callback) {
        if (NetworkUtil.isNetworkConnected(Application.getContext())) {
            continueEmptyResponseCall(call, callback);
        } else {
            callback.onErrorResponse("Internet not available", 1001, true);
        }

    }

    private void continueCall(Call<BaseResponse<T>> call, NetworkCallback<T> callback) {
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(@NotNull Call<BaseResponse<T>> call, @NotNull Response<BaseResponse<T>> response) {
                Log.e("//////////////", "response=" + response);
                Log.e("//////////////", "code=" + response.code());
                BaseResponse<T> b = response.body();
                if (b == null) {
                    try {
                        JSONObject json = new JSONObject(response.errorBody().string());
                        String msg = "";
                        int code = 500;
                        if (json.has("message")) {
                            msg = json.getString("message");
                        }
                        if (json.has("code")) {
                            code = json.getInt("code");
                        }
                        if (code == 401) {
                            openSessionTimeoutDialog(Constants.publicContext, 401);
                        } else {
                            callback.onErrorResponse(msg, code, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onErrorResponse("Please try after sometime.", 500, false);

                    }
                    return;
                }
                if (b.isSuccess()) {
                    if (b.getCode() == 200) {
                        callback.onSuccessResponse(b.getData());
                    } else if (b.getCode() == 401) {
                        openSessionTimeoutDialog(Constants.publicContext, 401);
                    } else {
                        callback.onErrorResponse(b.getMessage(), b.getCode(), false);
                    }
                } else {
                    if (b.getCode() == 401) {
                        openSessionTimeoutDialog(Constants.publicContext, 401);
                    } else {
                        callback.onErrorResponse(b.getMessage(), b.getCode(), false);
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<BaseResponse<T>> call, @NotNull Throwable t) {
                callback.onErrorResponse(t.getMessage(), 500, false);
            }
        });
    }

    private void continueEmptyResponseCall(Call<BaseEmptyResponse> call, EmptyNetworkCallback<T> callback) {
        call.enqueue(new Callback<BaseEmptyResponse>() {
            @Override
            public void onResponse(@NotNull Call<BaseEmptyResponse> call, @NotNull Response<BaseEmptyResponse> response) {
                Log.e("//////////////", "response=" + response);
                Log.e("//////////////", "code=" + response.code());
                BaseEmptyResponse b = response.body();
                if (b == null) {
                    try {
                        JSONObject json = new JSONObject(response.errorBody().string());
                        String msg = "";
                        int code = 500;
                        if (json.has("message")) {
                            msg = json.getString("message");
                        }
                        if (json.has("code")) {
                            code = json.getInt("code");
                        }
                        if (code == 401) {
                            openSessionTimeoutDialog(Constants.publicContext, 401);
                        } else {
                            callback.onErrorResponse(msg, code, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onErrorResponse("Please try after sometime.", 500, false);

                    }
                    return;
                }
                if (b.isSuccess()) {
                    if (b.getCode() == 200) {
                        callback.onEmptySuccessRespon(b.getMessage(), b.getCode(), false);
                    } else if (b.getCode() == 401) {
                        openSessionTimeoutDialog(Constants.publicContext, 401);
                    } else {
                        callback.onErrorResponse(b.getMessage(), b.getCode(), false);
                    }
                } else {
                    if (b.getCode() == 401) {
                        openSessionTimeoutDialog(Constants.publicContext, 401);
                    } else {
                        callback.onErrorResponse(b.getMessage(), b.getCode(), false);
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<BaseEmptyResponse> call, @NotNull Throwable t) {
                callback.onErrorResponse(t.getMessage(), 500, false);
            }
        });
    }

    private void openSessionTimeoutDialog(Context context, int code) {
        Intent i = new Intent(context, SessionDialogActivity.class);
        i.putExtra("code", code);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
