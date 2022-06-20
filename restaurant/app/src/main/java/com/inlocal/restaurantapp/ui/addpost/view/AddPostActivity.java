package com.inlocal.restaurantapp.ui.addpost.view;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ActivityAddPostBinding;
import com.inlocal.restaurantapp.ui.addpost.viewmodel.AddPostViewModel;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.ui.menulistpost.view.MenuListActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.io.File;

import javax.inject.Inject;

public class AddPostActivity extends BaseActivity<ActivityAddPostBinding> {
    @Inject
    ViewModelFactory viewModelFactory;
    private AddPostViewModel viewModel;
    private MenuItem selectedMenu;
    private StoryItem storyItem;
    private FeedWallItem feedWallItem;
    private File pictureFile;
    private static final int PERMISSION_CODE = 401;
    private String type = "";

    @Override
    protected int layoutRes() {
        return R.layout.activity_add_post;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddPostViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        init();

        binding.tvMenuName.setOnClickListener(v -> {
            NavUtil.ForActivity.navToForResult(this, MenuListActivity.class, 1000, null);
        });

        binding.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });


    }

    private void init() {
        binding.imgClose.setOnClickListener(v -> onBackPressed());
        viewModel.restaurantId.setValue(SharedPrefUtils.getIntData(this, Constants.RESTAURANT_ID) + "");
        if (getIntent() != null && getIntent().hasExtra("path")) {
            String path = getIntent().getStringExtra("path");
            pictureFile = new File(path);
            binding.imgImage.setImageUrl(path);
        }
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentData.DATA)) {
            binding.imgImage.setImageUrl(feedWallItem.getPostImage());
            viewModel.message.setValue(feedWallItem.getMessage());
            selectedMenu = new MenuItem();
            selectedMenu.setId(feedWallItem.getMenuItemId());
            selectedMenu.setName(feedWallItem.getMenuName());
            viewModel.menuItem.setValue(selectedMenu);
            binding.tvMenuName.setText(feedWallItem.getMenuName());
            binding.setVm(viewModel);
        }

        if (getIntent() != null && getIntent().hasExtra(Constants.IntentData.FEED_WALL_DATA)) {
            feedWallItem = (FeedWallItem) getIntent().getSerializableExtra(Constants.IntentData.FEED_WALL_DATA);
            viewModel.postId.setValue(feedWallItem.getPostId() + "");
            binding.imgImage.setImageUrl(feedWallItem.getPostImage());
            viewModel.message.setValue(feedWallItem.getMessage());
            if (feedWallItem.getMenuItemId() != null) {
                selectedMenu = new MenuItem();
                selectedMenu.setId(feedWallItem.getMenuItemId());
                viewModel.menuItem.setValue(selectedMenu);
                binding.tvMenuName.setText(feedWallItem.getMenuName() + "");
                binding.setVm(viewModel);
            }
        } else if (getIntent() != null && getIntent().hasExtra(Constants.IntentData.STORY_ITEM)) {
            storyItem = (StoryItem) getIntent().getSerializableExtra(Constants.IntentData.STORY_ITEM);
            viewModel.storyId.setValue(storyItem.getStoryId() + "");
            binding.imgImage.setImageUrl(storyItem.getStoryImage());
            viewModel.message.setValue(storyItem.getMessage());
            if (storyItem.getMenuItemId() != null) {
                selectedMenu = new MenuItem();
                selectedMenu.setId(storyItem.getMenuItemId());
                viewModel.menuItem.setValue(selectedMenu);
                binding.tvMenuName.setText(storyItem.getMenu_name() + "");
                binding.setVm(viewModel);
            }
        }

        if (getIntent() != null && getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
            if (type.equals("story")) {
                binding.txtTitle.setText("Add Story");
            }else if (type.equals("edit_story")) {
                binding.txtTitle.setText("Edit Story");
            } else if (type.equals("post")) {
                binding.txtTitle.setText("Add Post");
            } else if (type.equals("edit_post")) {
                binding.txtTitle.setText("Edit Post");
            }
        }

        viewModel.errorFromServer.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackbar(s);
            }
        });

        viewModel.createPostResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //showSnackbar(s);
                Toast.makeText(AddPostActivity.this, s, Toast.LENGTH_SHORT).show();
                NavUtil.ForActivity.navTo(AddPostActivity.this, HomeActivity.class, true, null);
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

        binding.imgNext.setOnClickListener(v -> {
            if (type.equals("edit_post")) {
                viewModel.btnCreatePost(v, pictureFile, true);
            } else if (type.equalsIgnoreCase("post")) {
                viewModel.btnCreatePost(v, pictureFile, false);
            } else if (type.equalsIgnoreCase("story")) {
                viewModel.btnCreateStory(v, pictureFile, false);
            }
            // NavUtil.ForActivity.navTo(this, HomeActivity.class, true, null))
        });

        binding.btnSharePost.setOnClickListener(v -> {
            if (type.equals("edit_post")) {
                viewModel.btnCreatePost(v, pictureFile, true);
            } else if (type.equalsIgnoreCase("post")) {
                viewModel.btnCreatePost(v, pictureFile, false);
            } else if (type.equalsIgnoreCase("story")) {
                Toast.makeText(AddPostActivity.this, "Story created", Toast.LENGTH_SHORT).show();
                //viewModel.btnCreateStory(v,pictureFile,false);
            }
            //

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    selectedMenu = (MenuItem) data.getSerializableExtra(Constants.IntentData.DATA);
                    viewModel.menuItem.setValue(selectedMenu);
                    binding.tvMenuName.setText(selectedMenu.getName());
                    break;
                case 1200:
                    String path = data.getStringExtra("path");
                    pictureFile = new File(path);
                    binding.imgImage.setImageUrl(path);
                    break;
            }
        }
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