package com.inlocal.restaurantapp.ui.auth.splash.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivitySplashBinding;
import com.inlocal.restaurantapp.service.PushMessagingService;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected int layoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Constants.publicContext = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SharedPrefUtils.getBooleanData(SplashActivity.this, Constants.AGENT_LOGGED_IN)) {
                    NavUtil.ForActivity.navTo(SplashActivity.this, LoginActivity.class, true, null);
                } else {
                    if (getIntent().hasExtra(Constants.IntentKey.RedirectionTarget) && getIntent().hasExtra(Constants.IntentKey.RedirectionExtra)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.IntentKey.RedirectionTarget, getIntent().getStringExtra(Constants.IntentKey.RedirectionTarget));
                        bundle.putInt(Constants.IntentKey.RedirectionExtra, getIntent().getIntExtra(Constants.IntentKey.RedirectionExtra, 0));
                        NavUtil.ForActivity.navTo(SplashActivity.this, HomeActivity.class, true, bundle);
                    } else {
                        NavUtil.ForActivity.navTo(SplashActivity.this, HomeActivity.class, true, null);
                    }
                }
            }
        }, 3000);
    }
}