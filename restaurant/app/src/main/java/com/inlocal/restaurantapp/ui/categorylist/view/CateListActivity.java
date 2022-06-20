package com.inlocal.restaurantapp.ui.categorylist.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityCateListBinding;
import com.inlocal.restaurantapp.databinding.DialogConfirmDeleteBinding;
import com.inlocal.restaurantapp.databinding.DialogDeleteBinding;
import com.inlocal.restaurantapp.ui.addcategory.view.AddCategoryActivity;
import com.inlocal.restaurantapp.ui.additem.model.AddItemModel;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.viewmodel.CategoryListViewModel;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class CateListActivity extends BaseActivity<ActivityCateListBinding> implements CateListAdapter.AddCatCallback {

    @Inject
    ViewModelFactory viewModelFactory;
    private CategoryListViewModel viewModel;
    private List<CateListModel> mData;
    private CateListAdapter adapter;
    private CateListModel selectedItem;
    private int mPage = 0, totalListRecord = 0, selectedPos = 0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_cate_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.toolbar.setActivity(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(CategoryListViewModel.class);
        //binding.setVm(viewModel);
        initVar();

        viewModel.errorFromServer.observe(this,response->{
            showSnackbar(response);
            /*mData.clear();
            mData.add(0, new CateListModel(1, ""));
            adapter.setList(mData);*/
        });
        //viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.cateListResponse.observe(this, response -> {
            if(response!=null) {
                totalListRecord = response.getTotal();
                if (mPage == 0) {
                    mData.clear();
                    mData = response.getCateListing();
                    mData.add(0, new CateListModel(1, ""));
                    adapter.setList(mData);
                } else {
                    for (int i = 0; i < response.getCateListing().size(); i++) {
                        adapter.addRow(response.getCateListing().get(i));
                    }
                    mData.addAll(response.getCateListing());
                }
            }else{
                mData.clear();
                mData.add(0, new CateListModel(1, ""));
                adapter.setList(mData);
            }
            /*if (adapter.getItemCount() == 0) {
                binding.tvNoResult.setVisibility(View.VISIBLE);
                binding.recyclerItem.setVisibility(View.GONE);
            } else {
                binding.tvNoResult.setVisibility(View.GONE);
                binding.recyclerItem.setVisibility(View.VISIBLE);
            }*/
        });

        viewModel.deleteResponse.observe(this, response -> {
            showSnackbar(response);
            adapter.removeItem(selectedPos);
            mData.remove(selectedPos);
            selectedItem = null;
            selectedPos = -1;
        });

        binding.layoutReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.layoutReferesh.setRefreshing(false);
                mPage = 0;
                viewModel.getCateList(mPage);
            }
        });

        binding.recyclerItem.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.getItemCount() < totalListRecord) {
                        mPage = mPage + 1;
                        viewModel.getCateList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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


        /*mData.add(new CateListModel(1, ""));
        mData.add(new CateListModel(2, "Beverage"));
        mData.add(new CateListModel(2, "Beverage"));
        mData.add(new CateListModel(2, "Veg"));
        mData.add(new CateListModel(2, "Veg"));
        mData.add(new CateListModel(2, "Starters"));
        mData.add(new CateListModel(2, "Starters"));
        mData.add(new CateListModel(2, "Main Course"));
        mData.add(new CateListModel(2, "Main Course"));
        mData.add(new CateListModel(2, "Non veg"));
        mData.add(new CateListModel(2, "Non veg"));
        mData.add(new CateListModel(2, "Wine"));
        mData.add(new CateListModel(2, "Wine"));
        mData.add(new CateListModel(2, "Dessert"));
        mData.add(new CateListModel(2, "Dessert"));
        mData.add(new CateListModel(2, "Breads"));*/


    }

    @Override
    public void add() {
        NavUtil.ForActivity.navTo(this, AddCategoryActivity.class, false, null);
        //finish();
    }

    @Override
    public void update(int pos, CateListModel model) {
        selectedItem = model;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DATA, model);
        NavUtil.ForActivity.navTo(this, AddCategoryActivity.class, false, bundle);
    }

    @Override
    public void delete(int pos, CateListModel model) {
        selectedItem = model;
        selectedPos=pos;
        confirmDeleteDialog(CateListActivity.this, pos, model);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getCateList(mPage);
    }

    private void initVar() {
        binding.toolbar.txtTitle.setText("Category Listing");
        mData = new ArrayList<>();
        adapter = new CateListAdapter(mData, this);
        binding.recyclerItem.setAdapter(adapter);
    }

    private void confirmDeleteDialog(Context context, int pos, CateListModel data) {
        Dialog dialog = new Dialog(context);
        DialogConfirmDeleteBinding dialogLogoutBinding = DialogConfirmDeleteBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialogLogoutBinding.txtYes.setOnClickListener(v -> {
            dialog.cancel();
            viewModel.cateModel.setValue(data);
            viewModel.deleteCategory(v);
        });
        dialogLogoutBinding.txtNo.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }
}