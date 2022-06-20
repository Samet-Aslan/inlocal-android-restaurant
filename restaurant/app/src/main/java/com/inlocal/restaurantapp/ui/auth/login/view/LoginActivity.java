package com.inlocal.restaurantapp.ui.auth.login.view;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityLoginBinding;
import com.inlocal.restaurantapp.service.network.TokenStore;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private TokenStore tokenStore;

    @Inject
    ViewModelFactory viewModelFactory;
    private LoginViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        init();
    }

    private void init() {
        this.tokenStore = new TokenStore(this);
        /*viewModel.email.setValue("sandipan.debnath@innofied.com");
        viewModel.password.setValue("12345678");
        viewModel.email.setValue("krutika.chotara@innofied.com");
        viewModel.password.setValue("123456");*/
        //viewModel.password.setValue("hGR43T5l");
        viewModel.navigateToDashboard.observeForever(new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                SharedPrefUtils.saveData(LoginActivity.this, Constants.AGENT_LOGGED_IN, true);
                NavUtil.ForActivity.navTo(LoginActivity.this, HomeActivity.class, true, null);
            }
        });

        viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.loginResponse.observe(this, loginResponseModel -> {
            tokenStore.store(loginResponseModel.token);
            Log.e("loginToken", tokenStore.load());
            SharedPrefUtils.saveData(LoginActivity.this, Constants.AGENT_LOGGED_IN, true);
            SharedPrefUtils.saveData(LoginActivity.this, Constants.RESTAURANT_ID, loginResponseModel.getRestaurantDetails().getId());
            SharedPrefUtils.saveData(LoginActivity.this, Constants.ACCESS_TOKEN, loginResponseModel.getToken());
            SharedPrefUtils.saveData(LoginActivity.this, Constants.SharePref.USER_NAME, loginResponseModel.getRestaurantDetails().getName());
            SharedPrefUtils.saveData(LoginActivity.this, Constants.SharePref.USER_EMAIL, loginResponseModel.getRestaurantDetails().getEmail());
            SharedPrefUtils.saveData(LoginActivity.this, Constants.SharePref.PROFILE_PIC, loginResponseModel.getRestaurantDetails().getProfilePicture());
            SharedPrefUtils.saveNotificationData(LoginActivity.this,loginResponseModel.getNotificationSettings());
            NavUtil.ForActivity.navTo(LoginActivity.this, HomeActivity.class, true, null);
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );
    }

}