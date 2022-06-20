package com.inlocal.restaurantapp.ui.menulistpost.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ActivityMenulistPostBinding;
import com.inlocal.restaurantapp.databinding.FragmentMenulistBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.view.CateListActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryAdapter;
import com.inlocal.restaurantapp.ui.menulistpost.viewmodel.MenuListPostViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MenuListActivity extends BaseActivity<ActivityMenulistPostBinding> implements CategoryAdapter.OnCateItemClickListener, MenuItemAdapter.OnMenuItemListener {
    @Inject
    ViewModelFactory viewModelFactory;

    private MenuListPostViewModel viewModel;
    private List<CateListModel> mCategories;
    private List<MenuItem> menuItemList;
    private MenuItem selectedMenuItem;
    private CateListModel selectedCategoryItem;
    private CategoryAdapter mCategoryAdapter;
    private MenuItemAdapter mMenuItemAdapter;
    private int mPage = 0, selectedCatPos = 0, selectedMenuPos = 0, menuPageNo = 0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_menulist_post;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MenuListPostViewModel.class);
        binding.setLifecycleOwner(this);
        init();
        binding.imgBack.setOnClickListener(v -> onBackPressed());
        viewModel.cateModel.observe(this, response -> {
            if (mPage == 0) {
                mCategories.clear();
            }
            mCategories.addAll(response);
            mCategoryAdapter.setList(mCategories);
            if(mCategoryAdapter.getItemCount()>0){
                onItemClick(0,response.get(0));
            }
            //scrollHorizontal();
        });

        viewModel.deleteResponse.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                showSnackbar(response);
                mMenuItemAdapter.removeItem(selectedMenuPos);
                menuItemList.remove(selectedMenuPos);
                selectedMenuItem = null;
                selectedMenuPos = -1;
            }
        });

        viewModel.errorFromServer.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSnackbar(s);
            }
        });
        viewModel.menuListResponse.observe(this, new Observer<MenuListResponseModel>() {
            @Override
            public void onChanged(MenuListResponseModel s) {
                //totalRecordOfMenuList =s.getTotal();
                if(s!=null) {
                    if (menuPageNo == 0) {
                        menuItemList.clear();
                        menuItemList = s.getMenuItemsList();
                        //menuItemList.add(0, new MenuItem(1));
                        mMenuItemAdapter.setList(menuItemList);
                    } else {
                        for (int i = 0; i < s.getMenuItemsList().size(); i++) {
                            mMenuItemAdapter.addRow(s.getMenuItemsList().get(i));
                        }
                        menuItemList.addAll(s.getMenuItemsList());
                    }
                }else{
                    menuItemList.clear();
                    mMenuItemAdapter.setList(menuItemList);
                }

            }
        });

        binding.layoutReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.layoutReferesh.setRefreshing(false);
                menuPageNo = 0;
                viewModel.getMenuItems(menuPageNo);
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


    private void init() {
        mCategories = new ArrayList<>();
        menuItemList = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(this);
        mMenuItemAdapter = new MenuItemAdapter(this);
        binding.recyclerViewCat.setAdapter(mCategoryAdapter);
        binding.recyclerItem.setAdapter(mMenuItemAdapter);
        binding.recyclerViewCat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mCategories.size() < viewModel.totalListRecord.getValue()) {
                        mPage = mPage + 1;
                        viewModel.getCategoryList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

      /*  binding.recyclerViewCat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (menuItemList.size() < viewModel.totalMenuItemRecord.getValue()) {
                        menuPageNo = menuPageNo + 1;
                        viewModel.getMenuItems(menuPageNo);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
    }

    @Override
    public void onMenuItemClick(int pos, MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DATA, item);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getCategoryList(mPage);
    }

    @Override
    public void onItemClick(int pos, CateListModel item) {
        selectedCatPos = pos;
        for (int i = 0; i < mCategories.size(); i++) {
            mCategories.get(i).setSelected(false);
        }
        mCategories.get(pos).setSelected(true);
        this.selectedCategoryItem = mCategories.get(pos);
        viewModel.selectedCateListModel.setValue(selectedCategoryItem);
        mCategoryAdapter.setList(mCategories);
        mCategoryAdapter.notifyDataSetChanged();
        menuPageNo = 0;

        viewModel.getMenuItems(menuPageNo);
        scrollHorizontal();
    }

    private void scrollHorizontal(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // float y = binding.recyclerViewCat.getY() + binding.recyclerViewCat.getChildAt(selectedCatPos).getY();
               // binding.recyclerViewCat.smoothScrollBy(0, (int) y);
                binding.recyclerViewCat.scrollToPosition(selectedCatPos);

            }
        }, 200);
    }

}