package com.inlocal.restaurantapp.ui.editprofile.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.interfaces.OnitemClickListner;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.commonmodel.Location;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.custom.dropdown.ShowDropDown;
import com.inlocal.restaurantapp.databinding.ActivityEditProfileBinding;

import com.inlocal.restaurantapp.easyphotopicker.ChooserType;
import com.inlocal.restaurantapp.ui.addaddress.model.AddressData;
import com.inlocal.restaurantapp.ui.addaddress.view.AddAdressActivity;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.editprofile.model.ProfileUpdateRequestModel;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.Data;
import com.inlocal.restaurantapp.ui.editprofile.viewmodel.EditProfileViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;
import com.inlocal.restaurantapp.easyphotopicker.DefaultCallback;
import com.inlocal.restaurantapp.easyphotopicker.EasyImage;
import com.inlocal.restaurantapp.easyphotopicker.MediaFile;
import com.inlocal.restaurantapp.easyphotopicker.MediaSource;
import com.inlocal.restaurantapp.util.permissionmanager.ActivityPermissionManager;
import com.inlocal.restaurantapp.util.permissionmanager.PermissionManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding> implements DayAdapter.OnItemClick {

    @Inject
    ViewModelFactory viewModelFactory;
    private EditProfileViewModel viewModel;
    private ArrayList<String> mDataTime;
    private TimeAdapter mTimeAdapter;
    private EasyImage easyImage;
    private File profileFile, coverFile;
    private PermissionManager permissionManager;
    private static final int PERMISSION_CODE = 101;
    private DayAdapter dayAdapter;
    private ProfileUpdateRequestModel profileUpdateRequestModel;
    private String convertedStartTime, convertedEndTime = "";
    private List<OpeningHoursItem> openinghours;
    private List<CateListModel> cuisineList;
    private ShowDropDown showDropDown;
    private Integer cuisineId;
    private Data restaurantProfileDetailsResponse;
    private boolean isCoverClicked = false, addAddressClick = false;
    private AddressData addressData;


    @Override
    protected int layoutRes() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (addAddressClick) {
            addAddressClick = false;
            permissionManager.checkPermissions();
            if (permissionManager.isGranted())
                gotoAddAddress();
            else
                openSettings();
        } else if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openChooser();
            } else {
                Toast.makeText(this, "Please grant permission from settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void gotoAddAddress() {
        Intent intent = new Intent(EditProfileActivity.this, AddAdressActivity.class);
        intent.putExtra("addressData", (Serializable) addressData);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(@NotNull MediaFile[] mediaFiles, @NotNull MediaSource mediaSource) {

                File proofImg = mediaFiles[0].getFile();
                ///File compressedImageFile = new Compressor(EditProfileActivity.this).compressToFile(proofImg);
                File compressedImageFile = proofImg;
                if (isCoverClicked) {
//                    viewModel.profilePicture.setValue(compressedImageFile);
                    isCoverClicked = false;
                    coverFile = compressedImageFile;
                    binding.imgCover.setImageFile(compressedImageFile);
                } else {
//                    viewModel.profilePicture.setValue(compressedImageFile);
                    profileFile = compressedImageFile;
                    binding.imgUser.setImageFile(compressedImageFile);
                }

            }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(EditProfileViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        permissionManager = new ActivityPermissionManager(this, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getProfileDetails();
        viewModel.getAddressDetailsResponse();
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;

        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.getDefault());

        Date date = null;

        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        return formattedDate;
    }

    @Override
    public void onItemClick(int pos, OpeningHoursItem model) {
        openinghours.set(pos, model);
        dayAdapter.updatePost(pos, openinghours.get(pos));
        //dayAdapter.set(pos, new MonthDayModel(mData.get(pos).getName(), isChecked));
    }

    private void init() {
        binding.toolbar.setActivity(this);
        binding.toolbar.txtTitle.setText("Edit Profile");
        profileUpdateRequestModel = new ProfileUpdateRequestModel();
        showDropDown = new ShowDropDown(this);
        easyImage = new EasyImage.Builder(this).setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(true)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName(getString(R.string.app_name)).build();
        mDataTime = new ArrayList<>();
        mDataTime.add("");
        mTimeAdapter = new TimeAdapter(mDataTime);
        viewModel.email.setValue(SharedPrefUtils.getStringData(this, Constants.SharePref.USER_EMAIL));
        viewModel.name.setValue(SharedPrefUtils.getStringData(this, Constants.SharePref.USER_NAME));
        openinghours = new ArrayList<>();
        dayAdapter = new DayAdapter(this);
        dayAdapter.setList(openinghours);
        binding.itemDay.setAdapter(dayAdapter);
        //viewModel.getProfileDetails();
        //viewModel.getAddressDetailsResponse();
        viewModel.restaurantProfileDetailsResponseMutableLiveData.observe(this, restaurantProfileDetailsResponse -> {
            this.restaurantProfileDetailsResponse = restaurantProfileDetailsResponse;
            binding.setData(restaurantProfileDetailsResponse.getRestaurantDetails());
            binding.imgUser.setImageUrl(restaurantProfileDetailsResponse.getRestaurantDetails().getProfilePicture());
            binding.imgCover.setImageUrl(restaurantProfileDetailsResponse.getRestaurantDetails().getCoverImage());
            viewModel.email.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getEmail());
            viewModel.name.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getName());
            viewModel.deliveryCharges.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getDeliveryCharges() + "");
            viewModel.deliveryNote.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getDeliveryNote() != null ? restaurantProfileDetailsResponse.getRestaurantDetails().getDeliveryNote() : "");
            viewModel.description.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getDescription());
            binding.edtPhone.setText(restaurantProfileDetailsResponse.getRestaurantDetails().getPhone().getNumber());
            binding.edtCate.setText(restaurantProfileDetailsResponse.getRestaurantDetails().getCuisineCategory().getName());
            cuisineId = restaurantProfileDetailsResponse.getRestaurantDetails().getCuisineCategory().getId();
            openinghours = restaurantProfileDetailsResponse.getOpeningHours();
            viewModel.cbReservation.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getRestReservationAvailable().equalsIgnoreCase("1"));
            if (viewModel.cbReservation.getValue()) {
                binding.cbReservation.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                binding.cbReservation.setBackgroundResource(R.drawable.ic_unselected);
            }
            viewModel.cbDelivery.setValue(restaurantProfileDetailsResponse.getRestaurantDetails().getRestDeliveryAvailable().equalsIgnoreCase("1"));
            if (viewModel.cbDelivery.getValue()) {
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_select_blue_large);
            }else {
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_unselected);
            }
            SharedPrefUtils.saveData(EditProfileActivity.this, Constants.SharePref.USER_NAME, viewModel.name.getValue());
            SharedPrefUtils.saveData(EditProfileActivity.this, Constants.SharePref.USER_EMAIL, viewModel.email.getValue());
            SharedPrefUtils.saveData(EditProfileActivity.this, Constants.SharePref.PROFILE_PIC, restaurantProfileDetailsResponse.getRestaurantDetails().getProfilePicture());
            dayAdapter.setList(openinghours);

        });

        viewModel.addressData.observe(this, addressData -> {
            this.addressData = addressData;
        });

        binding.txtAddressValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressClick = true;
                if (permissionManager != null && permissionManager.askForPermissions()) {
                    addAddressClick = false;
                    gotoAddAddress();
                }

//                NavUtil.ForActivity.navTo(EditProfileActivity.this, AddAddressActivity.class, false, bundle);

            }
        });

        binding.llAddItem.setOnClickListener(v -> {
            mTimeAdapter.addData("");
        });

        viewModel.callCuisineListing(0);
        viewModel.cuisineList.observe(this, cateListModelList -> {
            cuisineList = cateListModelList;
        });

        binding.txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown.showCuisines(binding.edtCate, cuisineList, new OnitemClickListner() {
                    @Override
                    public void onClick(View view, int position) {
                        cuisineId = cuisineList.get(position).getId();
                    }
                });
            }
        });

        viewModel.profileUpdateResponse.observe(this, s -> {
            if (s != null) {
                SharedPrefUtils.saveData(EditProfileActivity.this, Constants.SharePref.USER_NAME, viewModel.name.getValue());
                SharedPrefUtils.saveData(EditProfileActivity.this, Constants.SharePref.USER_EMAIL, viewModel.email.getValue());
                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }

        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
            if (booleanEvent.getContentIfNotHandled()) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.imageUploadResponse.observe(this, stringResponse -> {
            if (stringResponse != null) {
                showSnackbar(stringResponse);
            }
        });

        binding.btSave.setOnClickListener(v -> {
            viewModel.uploadFileImage(v, profileFile, coverFile);
            if (binding.edtPhone.getText().toString().equals("")) {
                binding.btSave.setEnabled(false);
            } else if (binding.edtName.getText().toString().equals("")) {
                binding.btSave.setEnabled(false);
            } else if (binding.edtCate.getText().toString().equals("")) {
                binding.btSave.setEnabled(false);
            } else {

                binding.btSave.setEnabled(true);
                profileUpdateRequestModel.setId(SharedPrefUtils.getIntData(EditProfileActivity.this, Constants.RESTAURANT_ID));
                profileUpdateRequestModel.setEmail(viewModel.email.getValue());
                profileUpdateRequestModel.setPhone(viewModel.phone.getValue());
                profileUpdateRequestModel.setName(viewModel.name.getValue());
                profileUpdateRequestModel.setCuisineId(cuisineId);
                profileUpdateRequestModel.setDescription(restaurantProfileDetailsResponse.getRestaurantDetails().getDescription());
                profileUpdateRequestModel.setOwnerName(restaurantProfileDetailsResponse.getRestaurantDetails().getOwnerName());
                profileUpdateRequestModel.setOwnerPhone(restaurantProfileDetailsResponse.getRestaurantDetails().getOwnerPhone());
                profileUpdateRequestModel.setOwnerEmail(restaurantProfileDetailsResponse.getRestaurantDetails().getOwnerEmail());
                profileUpdateRequestModel.setAddress(restaurantProfileDetailsResponse.getRestaurantDetails().getAddress());
                profileUpdateRequestModel.setLogitute(restaurantProfileDetailsResponse.getRestaurantDetails().getLocation().getLogitute()+"");
                profileUpdateRequestModel.setLatitue(restaurantProfileDetailsResponse.getRestaurantDetails().getLocation().getLatitue()+"");
                profileUpdateRequestModel.setLocation(restaurantProfileDetailsResponse.getRestaurantDetails().getLocation());
                profileUpdateRequestModel.setZipcode(restaurantProfileDetailsResponse.getRestaurantDetails().getZipcode());
                profileUpdateRequestModel.setRestDeliveryAvailable(viewModel.isDeliverable.getValue());
                profileUpdateRequestModel.setRestReservationAvailable(viewModel.isEatInside.getValue());
                profileUpdateRequestModel.setCommissionType("percentage");
                profileUpdateRequestModel.setCommissionValue(0);
                profileUpdateRequestModel.setDeliveryCharges(Double.parseDouble(viewModel.deliveryCharges.getValue()));
                profileUpdateRequestModel.setDeliveryNotes(viewModel.deliveryNote.getValue());
                profileUpdateRequestModel.setDescription(viewModel.description.getValue());
                profileUpdateRequestModel.setOpeningHours(openinghours);
                viewModel.updateProfile(binding.getRoot(), profileUpdateRequestModel);
            }
        });

        binding.imgEdit.setOnClickListener(v ->
                checkPermission());

        binding.coverimgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCoverClicked = true;
                checkPermission();
            }
        });

        binding.cbReservation.setOnClickListener(v -> {

            viewModel.cbReservation.setValue(!viewModel.cbReservation.getValue());
            viewModel.setIsEatInside();
            if (viewModel.cbReservation.getValue()) {
                profileUpdateRequestModel.setRestReservationAvailable("1");
                binding.cbReservation.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                profileUpdateRequestModel.setRestReservationAvailable("0");
                binding.cbReservation.setBackgroundResource(R.drawable.ic_unselected);
            }


        });

        binding.cbDelivery.setOnClickListener(v -> {

            viewModel.cbDelivery.setValue(!viewModel.cbDelivery.getValue());
            viewModel.setIsDeliverable();
            if (viewModel.cbDelivery.getValue()) {
                profileUpdateRequestModel.setRestDeliveryAvailable("1");
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                profileUpdateRequestModel.setRestDeliveryAvailable("0");
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_unselected);
            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            openChooser();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);
            }
        }
    }

    private void openChooser() {
        easyImage.openChooser(this);
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


}