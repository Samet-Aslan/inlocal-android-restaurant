package com.inlocal.restaurantapp.ui.homefragments.ui.profile.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.DialogAddItemBinding;
import com.inlocal.restaurantapp.databinding.DialogDeleteBinding;
import com.inlocal.restaurantapp.databinding.DialogLogoutBinding;
import com.inlocal.restaurantapp.databinding.FragmentProfileBinding;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeList;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeSubItem;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.changepassword.view.ChangePasswordActivity;
import com.inlocal.restaurantapp.ui.favorites.view.FavoritesActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.profile.viewmodel.ProfileViewModel;
import com.inlocal.restaurantapp.ui.myorders.view.MyOrdersActivity;
import com.inlocal.restaurantapp.ui.notificationssettings.view.NotificationsSettingsActivity;
import com.inlocal.restaurantapp.ui.statistics.view.StatisticsActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {


    @Inject
    ViewModelFactory viewModelFactory;
    private ProfileViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ProfileViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        initVar();
        viewModel.errorFromServer.observe(getViewLifecycleOwner(), this::showSnackbar);

        viewModel.logoutResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                SharedPrefUtils.saveData(getBaseActivity(), Constants.AGENT_LOGGED_IN, false);
                NavUtil.ForActivity.navTo(getActivity(), LoginActivity.class, true, null);
            }
        });

        binding.cardNotification.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getActivity(), NotificationsSettingsActivity.class, false, null);
        });
        binding.logo.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isMyProfile", true);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_profile_to_navigation_restaurant_details, bundle);
        });
        binding.btnLogout.setOnClickListener(v -> {
            openDialog();
        });
        binding.cardMyOrders.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getActivity(), MyOrdersActivity.class, false, null);
        });
        binding.cardSummary.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getActivity(), StatisticsActivity.class, false, null);
        });
        binding.cardChangePsw.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getActivity(), ChangePasswordActivity.class, false, null);
        });
        binding.cardMyFav.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_profile_to_navigation_favoruites);
        });
    }

    private void initVar() {
        binding.txtName.setText(SharedPrefUtils.getStringData(getContext(), Constants.SharePref.USER_NAME));
        binding.logo.setImageUrl(SharedPrefUtils.getStringData(getContext(), Constants.SharePref.PROFILE_PIC));
    }

    private void openDialog() {
        Dialog dialog = new Dialog(getContext());
        DialogLogoutBinding dialogLogoutBinding = DialogLogoutBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogLogoutBinding.btnYes.setOnClickListener(v -> {
            viewModel.logout(binding.btnLogout);
            dialog.cancel();
        });

        dialogLogoutBinding.btnNo.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.show();
    }
}
