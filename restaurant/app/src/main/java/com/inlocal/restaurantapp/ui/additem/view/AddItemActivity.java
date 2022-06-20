package com.inlocal.restaurantapp.ui.additem.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.databinding.ActivityAddItemBinding;
import com.inlocal.restaurantapp.databinding.DialogAddItemBinding;
import com.inlocal.restaurantapp.databinding.DialogDeleteBinding;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeList;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeSubItem;
import com.inlocal.restaurantapp.ui.additem.viewmodel.AddItemViewModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryAdapter;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;
import com.inlocal.restaurantapp.easyphotopicker.ChooserType;
import com.inlocal.restaurantapp.easyphotopicker.DefaultCallback;
import com.inlocal.restaurantapp.easyphotopicker.EasyImage;
import com.inlocal.restaurantapp.easyphotopicker.MediaFile;
import com.inlocal.restaurantapp.easyphotopicker.MediaSource;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class AddItemActivity extends BaseActivity<ActivityAddItemBinding> implements AddItemAdapter.OnAddItemParentListener, CategoryAdapter.OnCateItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    private AddItemViewModel viewModel;
    private List<CateListModel> mCategoryList;
    private List<CustomizeList> mData;
    private CateListModel selectedCateListModel;
    private MenuItem menuItem;
    private boolean isAddNew=false;
    private AddItemAdapter mAddItemAdapter;
    private EasyImage easyImage;
    private static final int PERMISSION_CODE = 101;
    private CategoryAdapter mCategoryAdapter;
    private File itemImageFile;
    private Context context;
    private int mPage = 0, totalListRecord = 0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_add_item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddItemViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        initVar();

        binding.imgItem.setOnClickListener(v -> checkPermission());
        binding.flAdd.setOnClickListener(v -> checkPermission());
        binding.clAddItem.setOnClickListener(v -> {
            openDialog(-1, false);
        });

        viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.cateListResponse.observe(this, response -> {
            totalListRecord = response.getTotal();
            if (mPage == 0) {
                mCategoryList.clear();
                mCategoryList = response.getCateListing();
                //mCategoryList.add(0, new CateListModel(1, ""));
                mCategoryAdapter.setList(mCategoryList);
            } else {
                for (int i = 0; i < response.getCateListing().size(); i++) {
                    mCategoryAdapter.addRow(response.getCateListing().get(i));
                }
                mCategoryList.addAll(response.getCateListing());
            }

            if (menuItem != null) {
                for (int i = 0; i < mCategoryList.size(); i++) {
                    if (menuItem.getMenuCategoryId().toString().equalsIgnoreCase(mCategoryList.get(i).getId().toString())) {
                        onItemClick(i, mCategoryList.get(i));
                        break;
                    }
                }
            }

            if(isAddNew && selectedCateListModel!=null){
                isAddNew=false;
                for (int i = 0; i < mCategoryList.size(); i++) {
                    if (selectedCateListModel.getId().toString().equalsIgnoreCase(mCategoryList.get(i).getId().toString())) {
                        onItemClick(i, mCategoryList.get(i));
                        break;
                    }
                }
            }
        });

        binding.cbEatInside.setOnClickListener(v -> {

            viewModel.cbEatInside.setValue(!viewModel.cbEatInside.getValue());
            viewModel.setIsEatInside();
            if (viewModel.cbEatInside.getValue()) {
                binding.cbEatInside.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                binding.cbEatInside.setBackgroundResource(R.drawable.ic_unselected);
            }


        });

        binding.cbDelivery.setOnClickListener(v -> {

            viewModel.cbDelivery.setValue(!viewModel.cbDelivery.getValue());
            viewModel.setIsDeliverable();
            if (viewModel.cbDelivery.getValue()) {
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                binding.cbDelivery.setBackgroundResource(R.drawable.ic_unselected);
            }
        });

        binding.btnAddItem.setOnClickListener(v -> {
            if (selectedCateListModel != null) {
                viewModel.customizeList.setValue(mData);
                if (menuItem != null) {
                    //if(itemImageFile!=null) {
                        viewModel.addItem(v, itemImageFile, true);
                    /*}else {
                        showSnackbar(getResources().getString(R.string.select_menu_image_file));
                    }*/
                } else {
                    if(itemImageFile!=null) {
                        viewModel.addItem(v, itemImageFile, false);
                    }else {
                        showSnackbar(getResources().getString(R.string.select_menu_image_file));
                    }
                }
            } else {
                showSnackbar(getResources().getString(R.string.select_categories_first));
            }
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
            if (booleanEvent.getContentIfNotHandled()) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.addItemResponse.observe(this, reseponItem ->
        {
            Log.e("response", reseponItem);
            Toast.makeText(AddItemActivity.this, reseponItem, Toast.LENGTH_SHORT).show();
            finish();
        });

        viewModel.menuDtailsResponse.observe(this, reseponItemDetails -> {
            if (viewModel.menuDtailsResponse != null) {
                viewModel.itemName.setValue(reseponItemDetails.getMenuItemDetails().getName());
                viewModel.itemPrice.setValue(reseponItemDetails.getMenuItemDetails().getPrice());
                viewModel.itemDesc.setValue(reseponItemDetails.getMenuItemDetails().getDescription());
                //binding.cbEatInside.setChecked(reseponItemDetails.getMenuItemDetails().getEatInsideAvailable().trim().equalsIgnoreCase("1") ? true : false);
                viewModel.cbEatInside.setValue(reseponItemDetails.getMenuItemDetails().getEatInsideAvailable().trim().equalsIgnoreCase("1") ? true : false);
                if (viewModel.cbEatInside.getValue()) {
                    binding.cbEatInside.setBackgroundResource(R.drawable.ic_select_blue_large);
                } else {
                    binding.cbEatInside.setBackgroundResource(R.drawable.ic_unselected);
                }
                viewModel.cbDelivery.setValue(reseponItemDetails.getMenuItemDetails().getDeliveryAvailable().trim().equalsIgnoreCase("1") ? true : false);
                if (viewModel.cbDelivery.getValue()) {
                    binding.cbDelivery.setBackgroundResource(R.drawable.ic_select_blue_large);
                }else {
                    binding.cbDelivery.setBackgroundResource(R.drawable.ic_unselected);
                }

                if (reseponItemDetails.getMenuItemDetails().getImage() != null && !reseponItemDetails.getMenuItemDetails().getImage().equalsIgnoreCase("")) {
                    binding.imgItem.setImageUrl(reseponItemDetails.getMenuItemDetails().getImage());
                    binding.imgItem.setVisibility(View.VISIBLE);
                    binding.flAdd.setVisibility(View.GONE);
                } else {
                    binding.imgItem.setVisibility(View.GONE);
                    binding.flAdd.setVisibility(View.VISIBLE);
                }
                mData.addAll(reseponItemDetails.getMenuItemDetails().getCustomizeList());
                mAddItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initVar() {
        context = AddItemActivity.this;
        easyImage = new EasyImage.Builder(this).setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(true)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName(getString(R.string.app_name)).build();
        binding.toolbar.setActivity(this);

        mData = new ArrayList<>();
        mAddItemAdapter = new AddItemAdapter(mData, this);
        binding.recyclerItem.setAdapter(mAddItemAdapter);
        mCategoryList = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(this);
        binding.recyclerViewCat.setAdapter(mCategoryAdapter);
        if(getIntent().hasExtra(Constants.IntentData.DATA)) {
            menuItem = (MenuItem) getIntent().getSerializableExtra(Constants.IntentData.DATA);
        }
        if(getIntent().hasExtra(Constants.IntentData.CATEGORY_DATA)){
            isAddNew=true;
            selectedCateListModel = (CateListModel) getIntent().getSerializableExtra(Constants.IntentData.CATEGORY_DATA);
        }else{
            isAddNew=false;
        }

        if (menuItem != null) {
            viewModel.requestId.setValue(new RequestById(menuItem.getId()));
            viewModel.callMenuDetails();
            binding.toolbar.txtTitle.setText(R.string.update_item);
            binding.btnAddItem.setText(R.string.update_item);
            viewModel.itemName.setValue(menuItem.getName());
            viewModel.itemPrice.setValue(menuItem.getPrice());
            viewModel.itemDesc.setValue(menuItem.getDescription());
            if (menuItem.getImage() != null && !menuItem.getImage().equalsIgnoreCase("")) {
                binding.imgItem.setImageUrl(menuItem.getImage());
                binding.imgItem.setVisibility(View.VISIBLE);
                binding.flAdd.setVisibility(View.GONE);
            } else {
                binding.imgItem.setVisibility(View.GONE);
                binding.flAdd.setVisibility(View.VISIBLE);
            }
        } else {
            binding.toolbar.txtTitle.setText(R.string.add_new_item);
            binding.btnAddItem.setText(R.string.add_item);
        }
    }

    private void openDialog(int pos, boolean isEdit) {
        Dialog dialog = new Dialog(this);
        DialogAddItemBinding dialogLogoutBinding = DialogAddItemBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (isEdit) {
            dialogLogoutBinding.edtName.setText(mData.get(pos).getTitle());
            dialogLogoutBinding.btnNo.setText(context.getString(R.string.update_item));
        } else {

        }

        dialogLogoutBinding.btnYes.setOnClickListener(v ->
                {
                    KeyboardUtil.hideSoftKeyboard(v);
                    dialog.cancel();
                }
        );

        dialogLogoutBinding.btnNo.setOnClickListener(v -> {
            if (dialogLogoutBinding.edtName.getText().toString().equals("")) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            } else {
                KeyboardUtil.hideSoftKeyboard(v);
                dialog.cancel();
                if (isEdit) {
                    mData.get(pos).setTitle(dialogLogoutBinding.edtName.getText().toString());
                } else {
                    ArrayList<CustomizeSubItem> d1 = new ArrayList<>();
                    mData.add(new CustomizeList(dialogLogoutBinding.edtName.getText().toString(), 1, d1));
                }
                mAddItemAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
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
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
            }
        }
    }

    private void openChooser() {
        easyImage.openChooser(this);
    }

    @Override
    public void onAddItemSelected(int pos, CustomizeList data) {
        showEditDeleteDialog(context, pos, data);
    }

    private void showEditDeleteDialog(Context context, int pos, CustomizeList data) {
        Dialog dialog = new Dialog(context);
        DialogDeleteBinding dialogLogoutBinding = DialogDeleteBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialogLogoutBinding.txtDelete.setVisibility(viewModel.menuDtailsResponse.getValue() != null ? View.VISIBLE : View.GONE);
        dialogLogoutBinding.txtDelete.setOnClickListener(v -> {
            dialog.cancel();
            mData.remove(pos);
            mAddItemAdapter.notifyDataSetChanged();
        });
        dialogLogoutBinding.txtEdit.setOnClickListener(v -> {
            dialog.cancel();
            openDialog(pos, true);
        });
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }

    /*public boolean validate(){
        if(itemImageFile!=null){
            Toast.makeText(this, "Select item image first", Toast.LENGTH_SHORT).show();
            return false;
        }else if(binding.edtDesc){

        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getCateList(mPage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openChooser();
            } else {
                Toast.makeText(this, "Please grant permission from settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(@NotNull MediaFile[] mediaFiles, @NotNull MediaSource mediaSource) {

                CropImage.activity(Uri.fromFile(new File(mediaFiles[0].getFile().getAbsolutePath())))
                        .setAllowFlipping(true)
                        .setAllowRotation(true)
                        .setAllowCounterRotation(true)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(1, 1)
                        .setInitialCropWindowPaddingRatio(0)
                        .start(AddItemActivity.this);
            }
        });

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.e("resultUri", resultUri.getPath());
                File proofImg = new File(resultUri.getPath());
                if (proofImg.exists()) {
                    Log.e("resultUri", true + "");
                } else {
                    Log.e("resultUri", false + "");
                }

                binding.imgItem.setVisibility(View.VISIBLE);
                binding.flAdd.setVisibility(View.GONE);
                File compressedImageFile =proofImg;
                itemImageFile = compressedImageFile;
                binding.imgItem.setImageFile(compressedImageFile);
                binding.flAdd.setVisibility(View.GONE);
                binding.imgItem.setVisibility(View.VISIBLE);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onItemClick(int pos, CateListModel item) {
        for (int i = 0; i < mCategoryList.size(); i++) {
            mCategoryList.get(i).setSelected(false);
        }
        mCategoryList.get(pos).setSelected(true);
        this.selectedCateListModel = mCategoryList.get(pos);
        viewModel.selectedCateListModel.setValue(selectedCateListModel);
        mCategoryAdapter.setList(mCategoryList);
        mCategoryAdapter.notifyDataSetChanged();
    }
}