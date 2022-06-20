package com.inlocal.restaurantapp.service.network;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @NonNull
    private final TokenStore tokenStore;

    public TokenInterceptor(@NonNull TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @NonNull
    @SuppressLint("HardwareIds")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String token = tokenStore.load();

        if (token != null) {
            builder.addHeader("Authorization", "Bearer "+ token);
        }

        return chain.proceed(builder.build());
    }

}
