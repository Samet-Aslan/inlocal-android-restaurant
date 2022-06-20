package com.inlocal.restaurantapp.service.network;


import com.inlocal.restaurantapp.app.instance.Application;
import com.inlocal.restaurantapp.util.Constants;

import java.util.concurrent.TimeUnit;

import kotlin.jvm.Volatile;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    @Volatile
    private static Retrofit mRetrofitService;

    public RetrofitService() {
    }

    public synchronized static Retrofit getInstance() {
        if (mRetrofitService == null) {

            return new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        } else {
            return mRetrofitService;
        }

    }

    private static OkHttpClient getClient() {
        TokenStore tokenStore = new TokenStore(Application.getContext());
        return new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(new DeviceInterceptor(Application.getContext()))
                .addInterceptor(new TokenInterceptor(tokenStore))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                /*.addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("x-auth-deviceid", "1234")
                                .addHeader("x-auth-devicetype", "android")
                                .addHeader("x-auth-notificationkey", "1234")
                                .build();
                        return chain.proceed(request);
                    }
                })*/
                .build();
    }
}
