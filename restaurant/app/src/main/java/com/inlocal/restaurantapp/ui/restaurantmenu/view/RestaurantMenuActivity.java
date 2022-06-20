package com.inlocal.restaurantapp.ui.restaurantmenu.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ActivityRestaurantMenuBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryAdapter;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryItemAdapter;
import com.inlocal.restaurantapp.ui.restaurantmenu.viewmodel.RestaurantMenuViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RestaurantMenuActivity extends BaseFragment<ActivityRestaurantMenuBinding> implements
        RestaurantMenuItemsAdapter.ViewMenuDetailsCallbakc, CategoryAdapter.OnCateItemClickListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private RestaurantMenuViewModel viewModel;
    private List<CateListModel> mCategories;
    private CateListModel selectedCategoryItem;
    private CategoryAdapter mCategoryAdapter;
    private RestaurantDetails restaurantDetails;
    private RestaurantMenuItemsAdapter menuListAdapter;
    private List<MenuItem> menuItemList;
    private int mPage = 0, type = 0, selectedCatPos = 0, selectedMenuPos = 0, menuPageNo = 0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_restaurant_menu;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestaurantMenuViewModel.class);
        binding.setLifecycleOwner(this);
        binding.topView.setActivity(getBaseActivity());
        setMenuAdapter();

        viewModel.menuListResponse.observe(getViewLifecycleOwner(), new Observer<MenuListResponseModel>() {
            @Override
            public void onChanged(MenuListResponseModel s) {
                //totalRecordOfMenuList =s.getTotal();
                if (s != null) {
                    binding.recyclerMenuItems.setVisibility(View.VISIBLE);
                    binding.tvNoRecord.setVisibility(View.GONE);
                    if (menuPageNo == 0) {
                        menuItemList.clear();
                        menuItemList = s.getMenuItemsList();
                        menuListAdapter.setList(menuItemList);
                    } else {
                        for (int i = 0; i < s.getMenuItemsList().size(); i++) {
                            menuListAdapter.addRow(s.getMenuItemsList().get(i));
                        }
                        menuItemList.addAll(s.getMenuItemsList());
                    }
                }else{
                    menuItemList.clear();
                    binding.recyclerMenuItems.setVisibility(View.GONE);
                    binding.tvNoRecord.setVisibility(View.VISIBLE);

                }

            }
        });

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), response -> {
            /*showSnackbar(response);*/
        });

        viewModel.cateModel.observe(getViewLifecycleOwner(), response -> {
            if(response!=null && response.size()>0) {
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
                /*if (mCategoryAdapter.getItemCount() > 0) {
                    onItemClick(0, response.get(0));
                }*/
            }
        });

        binding.recyclerMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


    }

    private void setMenuAdapter() {
        if (getArguments().containsKey("type")) {
            type = getArguments().getInt("type");
        }

        if (getArguments().containsKey(Constants.IntentData.RESTAURUNT_ID)) {
            viewModel.restId.setValue(getArguments().getInt(Constants.IntentData.RESTAURUNT_ID));
        }

        if (getArguments().containsKey(Constants.IntentData.RESTAURUNT_DETAILS)) {
            restaurantDetails = (RestaurantDetails) getArguments().getSerializable(Constants.IntentData.RESTAURUNT_DETAILS);
            if (restaurantDetails.getId() != null) {
                restaurantDetails.setRestaurantId(restaurantDetails.getId());
            } else if (restaurantDetails.getRestaurantId() != null) {
                restaurantDetails.setId(restaurantDetails.getRestaurantId());
            }
            viewModel.restId.setValue(restaurantDetails.getRestaurantId());
        }
        binding.topView.txtTitle.setText(restaurantDetails.getName());
        mCategories = new ArrayList<>();
        menuItemList = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(this);
        binding.recyclerMenu.setAdapter(mCategoryAdapter);
        menuListAdapter = new RestaurantMenuItemsAdapter(this);
        binding.recyclerMenuItems.setAdapter(menuListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getCategoryList(mPage);
    }

    @Override
    public void view(int pos, MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentData.RESTAURUNT_ID, viewModel.restId.getValue());
        bundle.putInt(Constants.IntentData.MENU_ID, item.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_res_menu_to_menuDetails, bundle);

        //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_res_menu_to_menuDetails);
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
        viewModel.getRestMenuList(menuPageNo, type);
    }
}