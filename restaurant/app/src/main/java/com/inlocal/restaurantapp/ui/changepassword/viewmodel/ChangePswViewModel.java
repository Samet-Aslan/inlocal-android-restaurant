package com.inlocal.restaurantapp.ui.changepassword.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.changepassword.model.ChangePasswordRequestModel;
import com.inlocal.restaurantapp.ui.changepassword.model.ChangePasswordResponse;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

public class ChangePswViewModel extends BaseViewModel {
    public MutableLiveData<String> oldPsw = new MutableLiveData<>("");
    public MutableLiveData<String> newPsw = new MutableLiveData<>("");
    public MutableLiveData<String> cnfPsw = new MutableLiveData<>("");
    public MutableLiveData<String> oldPswError = new MutableLiveData<>("");
    public MutableLiveData<String> newPswError = new MutableLiveData<>("");
    public MutableLiveData<String> cnfPswError = new MutableLiveData<>("");
    private MutableLiveData<Event<Boolean>> _pswSet = new MutableLiveData<Event<Boolean>>();
    public LiveData<Event<Boolean>> pswSet = _pswSet;
    public MutableLiveData<String> changepasswordResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();


    @Inject
    public ChangePswViewModel() {
    }

    public void oldPswChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            oldPswError.setValue("");
        }
    }

    public void newPswChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            newPswError.setValue("");
        }
    }

    public void cnfPswChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            cnfPswError.setValue("");
        }
    }

    public void submitPassword(View v) {
        if (ValidationUtil.isFieldEmpty(oldPsw.getValue())) {
            oldPswError.setValue(v.getContext().getString(R.string.str_enter_old_psw));
            return;
        }
        if (ValidationUtil.isPasswordValid(oldPsw.getValue())) {
            oldPswError.setValue(v.getContext().getResources().getString(R.string.error_password_invalid));
            return;
        }

        if (ValidationUtil.isFieldEmpty(newPsw.getValue())) {
            newPswError.setValue(v.getContext().getString(R.string.str_enter_new_psw));
            return;
        }
        if (ValidationUtil.isPasswordValid(newPsw.getValue())) {
            newPswError.setValue(v.getContext().getResources().getString(R.string.error_password_invalid));
            return;
        }
//        if (!ValidationUtil.isPasswordPatternValid(newPsw.getValue())) {
//            newPswError.setValue(v.getContext().getString(R.string.str_invalid_password_pattern));
//            return;
//        }
        if (ValidationUtil.isFieldEmpty(cnfPsw.getValue())) {
            cnfPswError.setValue(v.getContext().getString(R.string.str_enter_cnf_psw));
            return;
        }
        if (!newPsw.getValue().equals(cnfPsw.getValue())) {
            cnfPswError.setValue(v.getContext().getString(R.string.str_both_psw_not_matched));
            return;
        }

        changePassWord(v);

    }

    private void changePassWord(View v) {

        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.changePassWord(getChangePasswordRequest(v)), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                changepasswordResponse.setValue(message);
            }
        });
    }

    private ChangePasswordRequestModel getChangePasswordRequest(View v) {
        return new ChangePasswordRequestModel(SharedPrefUtils.getIntData(v.getContext(), Constants.RESTAURANT_ID), oldPsw.getValue(), newPsw.getValue());


    }

}
