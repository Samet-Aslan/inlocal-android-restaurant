package com.inlocal.restaurantapp.ui.statistics.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityStatisticsBinding;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryAdapter;
import com.inlocal.restaurantapp.ui.statistics.viewmodel.StaticViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StatisticsActivity extends BaseActivity<ActivityStatisticsBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private StaticViewModel viewModel;

    private List<CateListModel> mCategories;
    private CategoryAdapter mCategoryAdapter;
    private BestSellerAdapter bestSellerAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_statistics;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(StaticViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();

        binding.tvToday.setOnClickListener(v -> {
            unSelected();
            binding.tvToday.setBackground(getResources().getDrawable(R.drawable.blue_assigned_bg));
            viewModel.getStatics(Constants.TimeSpam.TODAY);
            binding.txtSummary.setText(getResources().getString(R.string.today_summery));
            binding.tvTodaySales.setText(getResources().getString(R.string.today_sales));
            binding.tvTodayOrders.setText(getResources().getString(R.string.today_orders));
            binding.tvTodayAccepted.setText(getResources().getString(R.string.today_accepted));
            binding.tvTodayCancelled.setText(getResources().getString(R.string.today_cancelled));
        });

        binding.tvThisWeek.setOnClickListener(v -> {
            unSelected();
            binding.tvThisWeek.setBackground(getResources().getDrawable(R.drawable.blue_assigned_bg));
            viewModel.getStatics(Constants.TimeSpam.THIS_WEEK);
            binding.txtSummary.setText(getResources().getString(R.string.this_week_summery));
            binding.tvTodaySales.setText(getResources().getString(R.string.this_week_Sales));
            binding.tvTodayOrders.setText(getResources().getString(R.string.this_week_Orders));
            binding.tvTodayAccepted.setText(getResources().getString(R.string.this_week_accepted));
            binding.tvTodayCancelled.setText(getResources().getString(R.string.this_week_cancelled));
        });

        binding.tvThisMonth.setOnClickListener(v -> {
            unSelected();
            binding.tvThisMonth.setBackground(getResources().getDrawable(R.drawable.blue_assigned_bg));
            viewModel.getStatics(Constants.TimeSpam.THIS_MONTH);
            binding.txtSummary.setText(getResources().getString(R.string.this_month_summery));
            binding.tvTodaySales.setText(getResources().getString(R.string.this_month_Sales));
            binding.tvTodayOrders.setText(getResources().getString(R.string.this_month_Orders));
            binding.tvTodayAccepted.setText(getResources().getString(R.string.this_month_accepted));
            binding.tvTodayCancelled.setText(getResources().getString(R.string.this_month_cancelled));
        });

        binding.tvAllTime.setOnClickListener(v -> {
            unSelected();
            binding.tvAllTime.setBackground(getResources().getDrawable(R.drawable.blue_assigned_bg));
            viewModel.getStatics(Constants.TimeSpam.ALL_TIME);
            binding.txtSummary.setText(getResources().getString(R.string.all_time_summery));
            binding.tvTodaySales.setText(getResources().getString(R.string.all_time_Sales));
            binding.tvTodayOrders.setText(getResources().getString(R.string.all_time_Orders));
            binding.tvTodayAccepted.setText(getResources().getString(R.string.all_time_accepted));
            binding.tvTodayCancelled.setText(getResources().getString(R.string.all_time_cancelled));
        });

        viewModel.responseStatic.observe(this, response -> {
            if (response != null) {
                binding.setData(response);
            }
        });

        viewModel.reseponseBestSeller.observe(this, responseBestSeller -> {
            if (responseBestSeller != null) {
                bestSellerAdapter.setList(responseBestSeller.getBestSellerList());
            }
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
                    if (booleanEvent != null) {
                        String s = booleanEvent.getContentIfNotHandled() + "";
                        if (s.equalsIgnoreCase("null")) {
                            hideLoading();
                        } else if (s.equalsIgnoreCase("true")) {
                            showLoading();
                        } else {
                            hideLoading();
                        }
                    }
                }
        );
    }

    private void initVar() {
        bestSellerAdapter = new BestSellerAdapter();
        binding.toolbar.setActivity(this);
        mCategories = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(mCategories);
        binding.toolbar.txtTitle.setText("Statistics");
        binding.recyclerItem.setAdapter(mCategoryAdapter);
        binding.recyclerBestSeller.setAdapter(bestSellerAdapter);
        unSelected();
        binding.tvToday.setBackground(getResources().getDrawable(R.drawable.blue_assigned_bg));
        viewModel.getStatics(Constants.TimeSpam.TODAY);
        binding.txtSummary.setText(getResources().getString(R.string.today_summery));
        binding.tvTodaySales.setText(getResources().getString(R.string.today_sales));
        binding.tvTodayOrders.setText(getResources().getString(R.string.today_orders));
        binding.tvTodayAccepted.setText(getResources().getString(R.string.today_accepted));
        binding.tvTodayCancelled.setText(getResources().getString(R.string.today_cancelled));
    }

    private void unSelected() {
        binding.tvToday.setBackground(getResources().getDrawable(R.drawable.black_assigned_bg));
        binding.tvThisWeek.setBackground(getResources().getDrawable(R.drawable.black_assigned_bg));
        binding.tvThisMonth.setBackground(getResources().getDrawable(R.drawable.black_assigned_bg));
        binding.tvAllTime.setBackground(getResources().getDrawable(R.drawable.black_assigned_bg));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getBestSeller();
    }
}