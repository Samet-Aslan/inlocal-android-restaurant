package com.inlocal.restaurantapp.ui.notificationssettings.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityNotificationsSettingsBinding;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.notificationssettings.viewmodel.NotificationSettingsViewModel;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Inject;

public class NotificationsSettingsActivity extends BaseActivity<ActivityNotificationsSettingsBinding> {
    @Inject
    ViewModelFactory viewModelFactory;
    private NotificationSettingsViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_notifications_settings;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.toolbar.txtTitle.setText(getResources().getString(R.string.notification_settings));
        binding.toolbar.setActivity(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(NotificationSettingsViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();

        viewModel.notificationSettingsResponse.observe(this, notificationSettings -> {

        });

        viewModel.errorFromServer.observe(this, response -> {
            if (response != null) {
                //showSnackbar(response);
                Toast.makeText(NotificationsSettingsActivity.this, response, Toast.LENGTH_SHORT).show();
                //NavUtil.ForActivity.navTo(NotificationsSettingsActivity.this, HomeActivity.class, true, null);
                finish();
            }
        });
        //viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );

    }

    private void initVar() {
        viewModel.getNotificationSettings();
        binding.setVm(viewModel);
    }
}