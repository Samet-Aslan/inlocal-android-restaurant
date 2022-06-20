package com.inlocal.restaurantapp.ui.uploadstory.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ActivityUploadStoryBinding;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.ui.menulistpost.view.MenuListActivity;
import com.inlocal.restaurantapp.ui.uploadstory.viewmodel.UploadStoryViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.io.File;

import javax.inject.Inject;


public class UploadStoryActivity extends BaseActivity<ActivityUploadStoryBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private UploadStoryViewModel viewModel;
    private File imagFile;
    private StoryItem storyItem;
    private MenuItem selectedMenu;
    private String type="";
    private boolean isEdit=false;
    private static final int PERMISSION_CODE = 401;

    @Override
    protected int layoutRes() {
        return R.layout.activity_upload_story;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(UploadStoryViewModel.class);
        // binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        initVar();
        binding.imgCamera.setOnClickListener(v->{
            checkPermission();
        });
        binding.tvTagRest.setOnClickListener(v -> {
            NavUtil.ForActivity.navToForResult(this, MenuListActivity.class, 1000, null);
            //NavUtil.ForActivity.navTo(this, OrderHistoryActivity.class, false, null);

          /*  binding.layoutRestName.setVisibility(View.VISIBLE);
            binding.tvTagRest.setVisibility(View.GONE);*/
        });

        binding.layoutRestName.setOnClickListener(v -> {
            //NavUtil.ForActivity.navTo(this, OrderHistoryActivity.class, false, null);
        });

        binding.btnUpoadStory.setOnClickListener(v -> {
            viewModel.btnCreatePost(v,imagFile,isEdit);
          //  NavUtil.ForActivity.navTo(this, HomeActivity.class, true, null);
        });

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        viewModel.errorFromServer.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackbar(s);
            }
        });

        viewModel.createStoryResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //showSnackbar(s);
                Toast.makeText(UploadStoryActivity.this, s, Toast.LENGTH_SHORT).show();
                NavUtil.ForActivity.navTo(UploadStoryActivity.this, HomeActivity.class, true, null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    selectedMenu = (MenuItem) data.getSerializableExtra(Constants.IntentData.DATA);
                    viewModel.menuItem.setValue(selectedMenu);
                    viewModel.restaurantId.setValue(SharedPrefUtils.getIntData(this,Constants.RESTAURANT_ID)+"");
                    binding.tvTagRest.setText(selectedMenu.getName());
                    GlideApp.with(this)
                            .load(selectedMenu.getImage())
                            .error(R.drawable.profile)
                            .timeout(30000)
                            .centerCrop()
                            .into(binding.imgUser);
                    break;
                case 1200:
                    if(data!=null) {
                        String path = data.getStringExtra("path");
                        imagFile = new File(path);
                        binding.imgStory.setImageUrl(path);
                    }
                    break;
            }
        }
    }



    private void initVar() {
        if(getIntent()!=null && getIntent().hasExtra("type")){
            type= getIntent().getStringExtra("type");
        }
        if (getIntent() != null && getIntent().hasExtra("path")) {
            String path = getIntent().getStringExtra("path");
            binding.imgStory.setImageUrl(path);
            imagFile = new File(path);
        }

        if(getIntent().hasExtra(Constants.IntentData.STORY_ITEM)){
            storyItem = (StoryItem) getIntent().getSerializableExtra(Constants.IntentData.STORY_ITEM);
        }

        if(type.equalsIgnoreCase("edit_story")){
            binding.tvTitle.setText("Update Story");
            isEdit=true;
        }
        //binding.imgUser.setImageUrl(SharedPrefUtils.getStringData(this, Constants.SharePref.PROFILE_PIC));
        if(isEdit){
           // binding.imgCamera.setVisibility(View.VISIBLE);
            binding.imgCamera.setVisibility(View.GONE);
            selectedMenu = new MenuItem();
            selectedMenu.setId(storyItem.getMenuItemId());
            selectedMenu.setName(storyItem.getMenu_name());
            selectedMenu.setImage(storyItem.getMenuImage());
            viewModel.storyId.setValue(storyItem.getStoryId()+"");
            binding.tvTagRest.setText(selectedMenu.getName());
            GlideApp.with(this)
                    .load(storyItem.getStoryImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .centerCrop()
                    .into(binding.imgStory);

            GlideApp.with(this)
                    .load(selectedMenu.getImage()!=null?selectedMenu.getImage():"")
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .centerCrop()
                    .into(binding.imgUser);
            binding.btnUpoadStory.setText("Update Story");

        }else {
            binding.imgCamera.setVisibility(View.GONE);
            GlideApp.with(this)
                    .load(SharedPrefUtils.getStringData(this, Constants.SharePref.PROFILE_PIC))
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .centerCrop()
                    .into(binding.imgUser);
            binding.layoutRestName.setVisibility(View.GONE);
            binding.tvTagRest.setText("Select Menu");
        }
        binding.tvUserName.setText(SharedPrefUtils.getStringData(this, Constants.SharePref.USER_NAME));
        binding.ivRest.setImageUrl(SharedPrefUtils.getStringData(this, Constants.SharePref.PROFILE_PIC));
        //binding.tvTagRest.setVisibility(View.GONE);

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            NavUtil.ForActivity.navToForResult(this, CameraActivity.class, 1200, bundle);
        } else {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE);
        }
    }
}