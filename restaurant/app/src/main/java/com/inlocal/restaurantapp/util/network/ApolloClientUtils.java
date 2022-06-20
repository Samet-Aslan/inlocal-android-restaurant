package com.inlocal.restaurantapp.util.network;


import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApolloClientUtils {
    private static ApolloClient mApolloClient = null;
    private static ApolloClientUtils mApolloClientUtils;

    private ApolloClientUtils() {

    }

    public synchronized static ApolloClientUtils getInstance() {
        if (mApolloClientUtils == null) {
            mApolloClientUtils = new ApolloClientUtils();
        }
        return mApolloClientUtils;
    }

    public ApolloClient createApolloClient() {
        if (mApolloClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            mApolloClient = ApolloClient.builder()
                    .serverUrl("")
                    .okHttpClient(okHttpClient)
                    .build();
        }
        return mApolloClient;
    }
}
