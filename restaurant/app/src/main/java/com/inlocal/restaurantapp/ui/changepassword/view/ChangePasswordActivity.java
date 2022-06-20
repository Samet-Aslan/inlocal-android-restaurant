package com.inlocal.restaurantapp.ui.changepassword.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityChangePasswordBinding;
import com.inlocal.restaurantapp.ui.changepassword.viewmodel.ChangePswViewModel;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Inject;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding> {
    @Inject
    ViewModelFactory viewModelFactory;
    private ChangePswViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_change_password;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChangePswViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        binding.toolbar.setActivity(this);
        binding.toolbar.txtTitle.setText("Change Password");


        viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.changepasswordResponse.observe(this, changePasswordResponse -> {
            if (!changePasswordResponse.equals("")) {
                Toast.makeText(ChangePasswordActivity.this, changePasswordResponse, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
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