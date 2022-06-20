package com.inlocal.restaurantapp.ui.addaddress.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityAddAddressBinding;
import com.inlocal.restaurantapp.ui.addaddress.model.AddressData;
import com.inlocal.restaurantapp.ui.addaddress.viewmodel.AddAddressViewModel;
import com.inlocal.restaurantapp.ui.locationmap.MapActivity;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;
import com.inlocal.restaurantapp.util.permissionmanager.ActivityPermissionManager;
import com.inlocal.restaurantapp.util.permissionmanager.PermissionManager;

import java.io.Serializable;

import javax.inject.Inject;

public class AddAdressActivity extends BaseActivity<ActivityAddAddressBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private AddAddressViewModel viewModel;
    private boolean isPassVisible = false;
    AddressData addressData;
    String countryCode = "";

    @Override
    protected int layoutRes() {
        return R.layout.activity_add_address;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddAddressViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setActivity(this);
        binding.toolbar.txtTitle.setText("Add Address");
        init();


       /* binding.ivBack.setOnClickListener(v -> {
            KeyboardUtil.hideSoftKeyboard(AddAddressActivity.this);
            finish();
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void init() {
        if (getIntent().hasExtra("addressData")) {
            addressData = (AddressData) getIntent().getSerializableExtra("addressData");
        }
        viewModel.houseNo.setValue(addressData.getAddress() != null ? addressData.getAddress() : "");
        viewModel.landMark.setValue(addressData.getLandmark() != null ? addressData.getLandmark() : "");
        viewModel.zipcode.setValue(addressData.getZipcode() != null ? addressData.getZipcode() : "");
        viewModel.city.setValue(addressData.getCity() != null ? addressData.getCity() : "");
        viewModel.countrycode.setValue(addressData.getCountryCode() != null ? addressData.getCountryCode() : "");
        viewModel.country.setValue(addressData.getCountry() != null ? addressData.getCountry() : "");

        viewModel.latitude.setValue(addressData.getLocation().getLatitue());
        viewModel.longitude.setValue(addressData.getLocation().getLogitute());


        viewModel.allOk.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btnSave.setAlpha(aBoolean ? 1 : 0.5f);
            }
        });

        binding.ccp.setCountryForNameCode(viewModel.countrycode.getValue());



        viewModel.navigateToDashboard.observeForever(new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                KeyboardUtil.hideSoftKeyboard(AddAdressActivity.this);
                Toast.makeText(AddAdressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.countrycode.setValue(binding.ccp.getSelectedCountryNameCode());
                viewModel.country.setValue(binding.ccp.getSelectedCountryName());

                Intent intent = new Intent(AddAdressActivity.this, MapActivity.class);
                intent.putExtra("lat", addressData.getLocation().getLatitue());
                intent.putExtra("long",addressData.getLocation().getLogitute());
                startActivityForResult(intent, 100);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        viewModel.addressUpdateResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(AddAdressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent ->

                {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            if (data != null) {
                viewModel.latitude.setValue(data.getStringExtra("lat"));
                viewModel.longitude.setValue(data.getStringExtra("long"));
                viewModel.updateProfileAddress();
            }
        }
    }


    private void clearAllText() {
        binding.etCity.getText().clear();
        binding.etLandMark.getText().clear();
        binding.etHouseNo.getText().clear();
        binding.etZipCode.getText().clear();

    }

}