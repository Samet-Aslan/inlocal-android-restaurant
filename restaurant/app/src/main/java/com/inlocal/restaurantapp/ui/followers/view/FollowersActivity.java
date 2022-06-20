package com.inlocal.restaurantapp.ui.followers.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityFollowersBinding;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.followers.model.FollowersItem;
import com.inlocal.restaurantapp.ui.followers.viewmodel.FollowViewModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FollowersActivity extends BaseActivity<ActivityFollowersBinding> implements FollowingAdapter.OnFollowingClickListener, FollowersAdapter.OnFollowersClickListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private FollowViewModel viewModel;
    private int mPage = 0, mPageFollowers = 0;
    private List<FollowersItem> followersList;
    private List<FollowersItem> followingList;
    private FollowersAdapter followersAdapter;
    private FollowingAdapter followingAdapter;
    private boolean isMyProfile = false, isFollowing = false;
    private int selectedPos = -1;

    @Override
    protected int layoutRes() {
        return R.layout.activity_followers;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FollowViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        initVar();

        viewModel.followActionResponse.observe(this, response -> {
            if (response != null) {
                /*if (isFollowing) {
                    followingList.get(selectedPos).setFollow(followingList.get(selectedPos).isFollow() != null ? !followingList.get(selectedPos).isFollow() : false);
                    followingAdapter.notifyItemChanged(selectedPos);
                } else {
                    followersList.remove(selectedPos);
                    followersAdapter.notifyDataSetChanged();
                }*/

                if (isFollowing) {
                    followersList.get(selectedPos).setFollow(followersList.get(selectedPos).isFollow() != null ? !followersList.get(selectedPos).isFollow() : false);
                    followersAdapter.notifyItemChanged(selectedPos);
                } else if(followingList!=null && followingList.size()>selectedPos) {
                    followingList.remove(selectedPos);
                    followingAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    mPageFollowers = 0;
                    binding.recyclerFollowing.setVisibility(View.VISIBLE);
                    binding.recyclerFollowers.setVisibility(View.GONE);
                    if (isMyProfile) {
                        viewModel.getFollowingList(mPageFollowers);
                    } else {
                        viewModel.requestOthersFollowes.getValue().setListType("Following");
                        viewModel.getOtherFollowingList(mPageFollowers);
                    }
                } else {
                    mPage = 0;
                    binding.recyclerFollowers.setVisibility(View.VISIBLE);
                    binding.recyclerFollowing.setVisibility(View.GONE);
                    if (isMyProfile) {
                        viewModel.getFollowersList(mPage);
                    } else {
                        viewModel.requestOthersFollowes.getValue().setListType("Followers");
                        viewModel.getOtherFollowingList(mPage);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewModel.errorFromServer.observe(this, this::showSnackbar);

        viewModel.followerListResponse.observe(this, response -> {
            if (response != null) {
                if (mPage == 0) {
                    followersList.clear();
                    followersList = response.getMyFollowerList();
                    followersAdapter.setList(followersList);
                } else {
                    for (int i = 0; i < response.getMyFollowerList().size(); i++) {
                        followersAdapter.addRow(response.getMyFollowerList().get(i));
                    }
                    followersList.addAll(response.getMyFollowerList());
                }
            }
        });

        viewModel.followingListResponse.observe(this, response -> {
            if (response != null) {
                if (mPageFollowers == 0) {
                    followingList.clear();
                    followingList = response.getMyFollowerList();
                    followingAdapter.setList(followingList);
                } else {
                    for (int i = 0; i < response.getMyFollowerList().size(); i++) {
                        followingAdapter.addRow(response.getMyFollowerList().get(i));
                    }
                    followingList.addAll(response.getMyFollowerList());
                }
            }
        });

        viewModel.responseOtherFolloweList.observe(this, response -> {
            if (response != null) {
                if (viewModel.requestOthersFollowes.getValue().getListType().equalsIgnoreCase("Following")) {
                    mPage = 0;
                    if (mPageFollowers == 0) {
                        followingList.clear();
                        followingList = response.getOtherFollowerList();
                        followingAdapter.setList(followingList);
                    } else {
                        for (int i = 0; i < response.getOtherFollowerList().size(); i++) {
                            followingAdapter.addRow(response.getOtherFollowerList().get(i));
                        }
                        followingList.addAll(response.getOtherFollowerList());
                    }
                } else {
                    mPageFollowers = 0;
                    if (mPage == 0) {
                        followersList.clear();
                        followersList = response.getOtherFollowerList();
                        followersAdapter.setList(followersList);
                    } else {
                        for (int i = 0; i < response.getOtherFollowerList().size(); i++) {
                            followersAdapter.addRow(response.getOtherFollowerList().get(i));
                        }
                        followersList.addAll(response.getOtherFollowerList());
                    }
                }
            }
        });

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {

            if (booleanEvent.getContentIfNotHandled()) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        binding.recyclerFollowers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isMyProfile) {
                        if (followersList.size() < viewModel.totalFollowersListCount.getValue()) {
                            mPage = mPage + 1;
                            viewModel.getFollowersList(mPage);
                        }
                    } else {
                        if (followingList.size() < viewModel.totalOthersFollowetCout.getValue()) {
                            mPage = mPage + 1;
                            viewModel.getOtherFollowingList(mPage);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.recyclerFollowing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isMyProfile) {
                        if (followingList.size() < viewModel.totalFollowingListCount.getValue()) {
                            mPageFollowers = mPageFollowers + 1;
                            viewModel.getFollowingList(mPageFollowers);

                        }
                    } else {
                        if (followingList.size() < viewModel.totalOthersFollowetCout.getValue()) {
                            mPageFollowers = mPageFollowers + 1;
                            viewModel.getOtherFollowingList(mPageFollowers);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initVar() {
        binding.toolbar.txtTitle.setText(getResources().getString(R.string.followers));
        binding.toolbar.setActivity(this);
        followersList = new ArrayList<>();
        followersAdapter = new FollowersAdapter(this);
        followersAdapter.setList(followersList);
        binding.recyclerFollowers.setAdapter(followersAdapter);
        followingList = new ArrayList<>();
        followingAdapter = new FollowingAdapter(this);
        followingAdapter.setList(followingList);
        binding.recyclerFollowing.setAdapter(followingAdapter);
        isMyProfile = getIntent().getBooleanExtra("isMyProfile", false);
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentData.CUSTOMER_TYPE)) {
            viewModel.requestOthersFollowes.getValue().setOtherUserType(getIntent().getStringExtra(Constants.IntentData.CUSTOMER_TYPE));
            viewModel.requestOthersFollowes.getValue().setOtherUserId(getIntent().getIntExtra(Constants.IntentData.OTHER_USER_ID, 0));
            //viewModel.requestOthersFollowes.getValue().setOtherUserType(getIntent().getStringExtra(Constants.IntentData.OTHER_USER_TYPE));
        }
        if (getIntent() != null && getIntent().hasExtra("type")) {
            int type = getIntent().getIntExtra("type", 1);
            if (type == 1) {
                binding.tabLayout.getTabAt(0).select();
                binding.recyclerFollowers.setVisibility(View.VISIBLE);
                binding.recyclerFollowing.setVisibility(View.GONE);
                mPage = 0;
                if (!isMyProfile) {
                    viewModel.requestOthersFollowes.getValue().setListType("Followers");
                    viewModel.getOtherFollowingList(mPage);
                } else {
                    viewModel.getFollowersList(mPage);
                }

            } else {
                mPageFollowers = 0;
                binding.tabLayout.getTabAt(1).select();
                binding.recyclerFollowing.setVisibility(View.VISIBLE);
                binding.recyclerFollowers.setVisibility(View.GONE);
                if (!isMyProfile) {
                    viewModel.requestOthersFollowes.getValue().setListType("Following");
                    viewModel.getOtherFollowingList(mPageFollowers);
                } else {
                    viewModel.getFollowingList(mPageFollowers);
                }
            }
        }


    }

    @Override
    public void onActionClick(int pos, FollowersItem followersItem) {
        isFollowing = false;
        selectedPos = pos;
        RequestFollowe requestFollowe = new RequestFollowe();
        if (followersItem.isFollow() != null) {
            //requestFollowe.setFollowStatus(followersItem.isFollow() ? "Follow" : "Unfollow");
            requestFollowe.setFollowStatus("Unfollow");
        } else {
            requestFollowe.setFollowStatus("Unfollow");
        }
        requestFollowe.setFollowerId(followersItem.getId());
        requestFollowe.setFollowerUserType(followersItem.getUserType());
        requestFollowe.setLoginUserType("Restaurant");
        viewModel.makeFollower(requestFollowe);
    }

    @Override
    public void onUnFollowingClick(int pos, FollowersItem followersItem) {

        isFollowing = true;
        selectedPos = pos;
        RequestFollowe requestFollowe = new RequestFollowe();
        requestFollowe.setFollowStatus(followersItem != null ? !followersItem.isFollow() ? "Follow" : "Unfollow" : "Unfollow");
        requestFollowe.setFollowerId(followersItem.getId());
        requestFollowe.setFollowerUserType(followersItem.getUserType());
        requestFollowe.setLoginUserType("Restaurant");
        viewModel.makeFollower(requestFollowe);
    }

    @Override
    public void onActionItemClick(int pos, FollowersItem followersItem) {
        /*selectedPos = pos;
        Bundle b = new Bundle();
        if (followersItem.getUserType().equalsIgnoreCase("Customer")) {
            b.putInt(Constants.IntentData.CUSTOMER_ID, followersItem.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_followers_to_navigation_user_profile, b);
        } else {
            b.putInt(Constants.IntentData.RESTAURUNT_ID, followersItem.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_followers_to_navigation_restaurant_details, b);
        }*/
    }

    @Override
    public void onItemClick(int pos, FollowersItem followersItem) {
       /* selectedPos = pos;
        Bundle b = new Bundle();
        if (followersItem.getUserType().equalsIgnoreCase("Customer")) {
            b.putInt(Constants.IntentData.CUSTOMER_ID, followersItem.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_followers_to_navigation_user_profile, b);
        } else {
            b.putInt(Constants.IntentData.RESTAURUNT_ID, followersItem.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_followers_to_navigation_restaurant_details, b);
        }*/
    }

}