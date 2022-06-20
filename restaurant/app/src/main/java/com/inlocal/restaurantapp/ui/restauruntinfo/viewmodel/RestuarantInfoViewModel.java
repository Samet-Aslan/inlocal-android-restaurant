package com.inlocal.restaurantapp.ui.restauruntinfo.viewmodel;

import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class RestuarantInfoViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> networkError = new MutableLiveData<>(false);

    private MutableLiveData<Event<String>> _navigateToDashboard = new MutableLiveData<Event<String>>();
    public LiveData<Event<String>> navigateToDashboard = _navigateToDashboard;

    @Inject
    public RestuarantInfoViewModel() {
    }



}