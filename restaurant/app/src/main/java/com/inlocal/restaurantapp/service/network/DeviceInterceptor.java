package com.inlocal.restaurantapp.service.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceInterceptor implements Interceptor {

    @NonNull
    private final Context context;

    public DeviceInterceptor(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @SuppressLint("HardwareIds")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        final Request.Builder builder = chain.request().newBuilder();
        String notificationId = FirebaseInstanceId.getInstance().getToken();
        builder.addHeader("Accept", "application/json");
        builder.addHeader("x-auth-devicetype", "ANDROID");
        if (TextUtils.isEmpty(notificationId) || notificationId == null)
            notificationId = "xyz";

        if(deviceId != null) {
            builder.addHeader("x-auth-deviceid", deviceId);
        }
        builder.addHeader("x-auth-notificationkey", notificationId);

        return chain.proceed(builder.build());
    }

}
