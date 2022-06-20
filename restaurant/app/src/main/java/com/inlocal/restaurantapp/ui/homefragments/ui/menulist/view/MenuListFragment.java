package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.DialogConfirmDeleteBinding;
import com.inlocal.restaurantapp.databinding.FragmentMenulistBinding;
import com.inlocal.restaurantapp.ui.additem.view.AddItemActivity;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.view.CateListActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.viewmodel.MenuListViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MenuListFragment extends BaseFragment<FragmentMenulistBinding> implements CategoryAdapter.OnCateItemClickListener, CategoryItemAdapter.OnMenuItemListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private MenuListViewModel viewModel;
    private List<CateListModel> mCategories;
    private List<MenuItem> menuItemList;
    private MenuItem selectedMenuItem;
    private CateListModel selectedCategoryItem;
    private CategoryAdapter mCategoryAdapter;
    private CategoryItemAdapter mCategoryItemAdapter;
    private int mPage = 0, selectedCatPos = -1, selectedMenuPos = 0, menuPageNo = 0;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_menulist;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MenuListViewModel.class);
        binding.setLifecycleOwner(this);
        init();
        viewModel.cateModel.observe(getActivity(), response -> {
            if (response != null) {
                if (mPage == 0) {
                    mCategories.clear();
                }
                mCategories.addAll(response);
                mCategoryAdapter.setList(mCategories);
                if (mCategoryAdapter.getItemCount() > 0) {
                    if(selectedCatPos == -1) {
                        onItemClick(0, response.get(0));
                    }else{
                        onItemClick(selectedCatPos, response.get(0));
                    }
                }
            }/*else{
                menuItemList.clear();
                menuItemList.add(0, new MenuItem(1));
                mCategoryItemAdapter.setList(menuItemList);
            }*/

        });

        viewModel.deleteResponse.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String response) {
                showSnackbar(response);
                mCategoryItemAdapter.removeItem(selectedMenuPos);
                menuItemList.remove(selectedMenuPos);
                selectedMenuItem = null;
                selectedMenuPos = -1;
            }
        });

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                /*showSnackbar(s);*/
            }
        });
        viewModel.menuListResponse.observe(getViewLifecycleOwner(), new Observer<MenuListResponseModel>() {
            @Override
            public void onChanged(MenuListResponseModel s) {
                //totalRecordOfMenuList =s.getTotal();
                if (s != null) {
                    //binding.tvNoRecord.setVisibility(View.GONE);
                    if (menuPageNo == 0) {
                        menuItemList.clear();
                        menuItemList = s.getMenuItemsList();
                        menuItemList.add(0, new MenuItem(1));
                        mCategoryItemAdapter.setList(menuItemList);
                    } else {
                        for (int i = 0; i < s.getMenuItemsList().size(); i++) {
                            mCategoryItemAdapter.addRow(s.getMenuItemsList().get(i));
                        }
                        menuItemList.addAll(s.getMenuItemsList());
                    }
                } else {
                    //binding.tvNoRecord.setVisibility(View.VISIBLE);
                    if (mCategoryAdapter.getItemCount() >= 1) {
                        menuItemList.clear();
                        menuItemList.add(0, new MenuItem(1));
                        mCategoryItemAdapter.setList(menuItemList);
                    }
                }
            }
        });
        binding.ivCatList.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getBaseActivity(), CateListActivity.class, false, null);
        });

        binding.layoutReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.layoutReferesh.setRefreshing(false);
                menuPageNo = 0;
                viewModel.getMenuItems(menuPageNo);
            }
        });

        viewModel.isProgressEnabled.observe(getBaseActivity(), booleanEvent -> {
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
        mCategoryItemAdapter = new CategoryItemAdapter(this);
        binding.recyclerViewCat.setAdapter(mCategoryAdapter);
        binding.recyclerItem.setAdapter(mCategoryItemAdapter);
        binding.recyclerViewCat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
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

        binding.recyclerViewCat.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
    }

    @Override
    public void onAddClick(int pos) {

        Bundle bundle = new Bundle();
        if (selectedCategoryItem != null) {
            bundle.putSerializable(Constants.IntentData.CATEGORY_DATA, selectedCategoryItem);
        }
        NavUtil.ForActivity.navTo(getBaseActivity(), AddItemActivity.class, false, bundle);
    }

    @Override
    public void onMenuItemDeleteClick(int pos, MenuItem item) {
        selectedMenuItem = item;
        selectedMenuPos = pos;
        confirmDeleteDialog(getContext(), pos, item);
    }

    @Override
    public void onMenuItemEditClick(int pos, MenuItem item) {
        selectedMenuItem = item;
        selectedMenuPos = pos;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DATA, item);
        NavUtil.ForActivity.navTo(getBaseActivity(), AddItemActivity.class, false, bundle);
    }

    @Override
    public void onMenuItemClick(int pos) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getCategoryList(mPage);
    }

    private void confirmDeleteDialog(Context context, int pos, MenuItem data) {
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
            viewModel.selectedMenuItem.setValue(data);
            viewModel.deleteMenuItem(v);
        });
        dialogLogoutBinding.txtNo.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
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
    }
}