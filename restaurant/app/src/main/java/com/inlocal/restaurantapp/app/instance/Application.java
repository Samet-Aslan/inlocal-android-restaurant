package com.inlocal.restaurantapp.app.instance;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inlocal.restaurantapp.app.di.AppComponent;
import com.inlocal.restaurantapp.app.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class Application extends DaggerApplication {
    private static Application baseApplication;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Application.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent component = DaggerAppComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }

}
