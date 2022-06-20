package com.inlocal.restaurantapp.ui.auth.login.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseResponse;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.service.network.TokenStore;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginRequestModel;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<String> deviceToken = new MutableLiveData<>();
    public MutableLiveData<String> countryCode = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> emailError = new MutableLiveData<>("");
    public MutableLiveData<String> passwordError = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isPswVisible = new MutableLiveData<>(false);
    private MutableLiveData<Event<String>> _navigateToDashboard = new MutableLiveData<Event<String>>();
    public LiveData<Event<String>> navigateToDashboard = _navigateToDashboard;
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<LoginResponseModel> loginResponse = new MutableLiveData<>();

    @Inject
    public LoginViewModel() {
    }

    public void setIsPswVisible(EditText editText) {
        isPswVisible.setValue(!isPswVisible.getValue());
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setSelection(editText.getText().toString().length());
            }
        }, 400);

    }

    public void phoneNumberChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            emailError.setValue("");
        }
    }

    public void passwordChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            passwordError.setValue("");
        }
    }

    public void login(View v) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (ValidationUtil.isFieldEmpty(email.getValue())) {
            emailError.setValue(v.getContext().getResources().getString(R.string.error_email_empty));
            return;
        }
        if (!ValidationUtil.isEmailValid(email.getValue())) {
            emailError.setValue(v.getContext().getResources().getString(R.string.error_email_invalid));
            return;
        }
        if (ValidationUtil.isFieldEmpty(password.getValue())) {
            passwordError.setValue(v.getContext().getResources().getString(R.string.error_password_empty));
            return;
        }
        if (ValidationUtil.isPasswordValid(password.getValue())) {
            passwordError.setValue(v.getContext().getResources().getString(R.string.error_password_invalid));
            return;
        }
        if (!NetworkUtil.isNetworkConnected(v.getContext())) {
            passwordError.postValue(v.getContext().getResources().getString(R.string.str_internet_not_connected));
            return;
        }
//        _navigateToDashboard.setValue(new Event<>("navigate"));
        callLoginApi();

    }

    private void callLoginApi() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall<LoginResponseModel> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.doLogin(getLoginRequest()), new NetworkCallback<LoginResponseModel>() {
            @Override
            public void onSuccessResponse(LoginResponseModel response) {
                isProgressEnabled.setValue(new Event<>(false));
                loginResponse.setValue(response);
                errorFromServer.setValue("Login Success");
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });
    }

    private LoginRequestModel getLoginRequest() {
        return new LoginRequestModel(email.getValue(), password.getValue());
    }
}