package com.inlocal.restaurantapp.ui.addcategory.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityAddCategoryBinding;
import com.inlocal.restaurantapp.ui.addcategory.viewmodel.AddCategoryViewModel;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Inject;

public class AddCategoryActivity extends BaseActivity<ActivityAddCategoryBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private AddCategoryViewModel viewModel;
    private CateListModel model;

    @Override
    protected int layoutRes() {
        return R.layout.activity_add_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.toolbar.setActivity(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddCategoryViewModel.class);
        binding.setVm(viewModel);
        init();

        binding.btnSave.setOnClickListener(v -> {
            if (model != null) {
                viewModel.cateModel.getValue().setName(binding.tvMenuName.getText().toString());
                viewModel.updateCategory(v);
            } else {
                viewModel.cateName.setValue(binding.tvMenuName.getText().toString());
                viewModel.addCategory(v);
            }
        });
    }

    private void init() {
        binding.toolbar.txtTitle.setText(getResources().getString(R.string.add_new_category_cap));
        model = (CateListModel) getIntent().getSerializableExtra(Constants.IntentData.DATA);
        if (model != null) {
            binding.toolbar.txtTitle.setText(getResources().getString(R.string.update_category));
            binding.tvMenuName.setText(model.getName());
            viewModel.cateModel.setValue(model);
            binding.btnSave.setText(getResources().getString(R.string.update));
        }
        viewModel.errorFromServer.observe(this, this::showSnackbar);
        viewModel.addMenuResponse.observe(this, response -> {
            showSnackbar(response);
            binding.tvMenuName.setText("");
        });
        viewModel.editMenuResponse.observe(this, response -> {
            Toast.makeText(AddCategoryActivity.this, response, Toast.LENGTH_SHORT).show();
            finish();
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