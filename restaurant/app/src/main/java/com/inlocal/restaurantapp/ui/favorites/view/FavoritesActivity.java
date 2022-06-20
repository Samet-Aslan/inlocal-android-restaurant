package com.inlocal.restaurantapp.ui.favorites.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ActivityFavoritesBinding;
import com.inlocal.restaurantapp.ui.favorites.viewmodel.FavoritesViewModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.ImagesAdapter;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.RestaurantDetailsActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesActivity extends BaseFragment<ActivityFavoritesBinding> implements ImagesAdapter.ImagesCallback {
    @Inject
    ViewModelFactory viewModelFactory;
    private FavoritesViewModel viewModel;
    private ImagesAdapter adapter, adapterUser;
    private int mRestPageNo = 0, mUserPageNo = 0;
    private List<PostItem> restPostList, userPostList;

    @Override
    protected int layoutRes() {
        return R.layout.activity_favorites;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        initVar();


        binding.recyclerRes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (restPostList.size() < viewModel.totalFavouritesListCount.getValue()) {
                        mRestPageNo = mRestPageNo + 1;
                        viewModel.page.setValue(mRestPageNo);
                        viewModel.getFavList();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.recyclerUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (userPostList.size() < viewModel.totalFavouritesListCount.getValue()) {
                        mUserPageNo = mUserPageNo + 1;
                        viewModel.page.setValue(mUserPageNo);
                        viewModel.getFavList();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        viewModel.favouritesResponse.observe(getViewLifecycleOwner(), response -> {
            binding.recyclerRes.setVisibility(View.GONE);
            binding.tvNoRecord.setVisibility(View.GONE);
            binding.recyclerUser.setVisibility(View.GONE);
            if (viewModel.type.getValue() == 1) {
                if (response != null) {
                    binding.recyclerRes.setVisibility(View.VISIBLE);
                    if (mRestPageNo == 0) {
                        restPostList.clear();
                        restPostList = response.getFavoritePostList();
                        adapter.setItemList(restPostList);
                    } else {
                        for (int i = 0; i < response.getFavoritePostList().size(); i++) {
                            adapter.addRow(response.getFavoritePostList().get(i));
                        }
                        restPostList.addAll(response.getFavoritePostList());
                    }
                } else {
                    binding.tvNoRecord.setVisibility(View.VISIBLE);
                }
            } else {
                if (response != null) {
                    binding.recyclerUser.setVisibility(View.VISIBLE);
                    if (mUserPageNo == 0) {
                        userPostList.clear();
                        userPostList = response.getFavoritePostList();
                        adapterUser.setItemList(userPostList);
                    } else {
                        for (int i = 0; i < response.getFavoritePostList().size(); i++) {
                            adapterUser.addRow(response.getFavoritePostList().get(i));
                        }
                        userPostList.addAll(response.getFavoritePostList());
                    }
                } else {
                    binding.tvNoRecord.setVisibility(View.VISIBLE);
                }
            }

        });

    }


    @Override
    public void click(int pos, PostItem itemList) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentData.POST_ID, itemList.getId());
        if(itemList.getPostUserType().equalsIgnoreCase("Restaurant") && itemList.getUserPostBy() == SharedPrefUtils.getIntData(getContext(),Constants.RESTAURANT_ID)){
            bundle.putBoolean("showOwnerMenu", true);
        }else{
            bundle.putBoolean("showOwnerMenu", false);
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_favorites_to_navigation_comment, bundle);
    }

    private void initVar(){
        binding.toolbar.setActivity(getBaseActivity());
        binding.toolbar.txtTitle.setText("Favourites");
        adapter = new ImagesAdapter(this);
        adapterUser = new ImagesAdapter(this);
        restPostList=new ArrayList<>();
        userPostList=new ArrayList<>();
        adapter.setItemList(restPostList);
        adapterUser.setItemList(userPostList);
        binding.recyclerRes.setAdapter(adapter);
        binding.recyclerUser.setAdapter(adapterUser);
        viewModel.changeType(1);
    }
}