package com.inlocal.restaurantapp.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inlocal.restaurantapp.util.Event;

public abstract class BaseViewModel extends ViewModel {
    public MutableLiveData<Event<Boolean>> isProgressEnabled = new MutableLiveData<>();

    public BaseViewModel() {
    }

    public MutableLiveData<Event<Boolean>> getIsLoading() {
        return isProgressEnabled;
    }

    public void setIsLoading(Event<Boolean> isLoading) {
        isProgressEnabled.setValue(isLoading);
    }
}
