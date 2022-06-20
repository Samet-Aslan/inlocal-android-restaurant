package com.inlocal.restaurantapp.ui.taggedphotos.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.CustomerDetails;
import com.inlocal.restaurantapp.databinding.ActivityTaggedPhotosBinding;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.view.FeedAdapter;
import com.inlocal.restaurantapp.ui.taggedphotos.viewmodel.TaggedPhotoViewModel;
import com.inlocal.restaurantapp.ui.userdetails.viewmodel.UserDetailsViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TaggedPhotosActivity extends BaseFragment<ActivityTaggedPhotosBinding> implements FeedAdapter.FeedCallBack {

    @Inject
    ViewModelFactory viewModelFactory;
    private TaggedPhotoViewModel viewModel;
    private FeedAdapter mFeedAdapter;
    private String from = "";
    private FeedWallItem selctedFeedwal, previousFeedWall;
    private List<FeedWallItem> feedWallItemList;
    private int mPage = 0, listType = 0;
    private boolean isMyProfile = false;

    @Override
    protected int layoutRes() {
        return R.layout.activity_tagged_photos;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding.imgUser.setOnClickListener(v->NavUtil.ForActivity.navTo(this, UserDetailsActivity.class, false, null));
//        binding.txtUseName.setOnClickListener(v->NavUtil.ForActivity.navTo(this, UserDetailsActivity.class, false, null));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(TaggedPhotoViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();


        binding.nvScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideAllExpandedViews();
                return false;
            }
        });

        binding.nvScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nvScroll.getChildAt(binding.nvScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.nvScroll.getHeight() + binding.nvScroll.getScrollY()));

                if (diff == 0) {
                    if (from.equalsIgnoreCase("rest")) {
                        mPage = mPage + 1;
                        if(listType==Constants.ListShowType.INSIGHT){
                            viewModel.getInsightList(mPage, previousFeedWall.getPostId(), "Customer");
                        }else if (isMyProfile) {
                            //viewModel.getMyPostList(mPage);
                            viewModel.getPostListById(mPage, previousFeedWall.getPostId(), "Restaurant");
                        } else {
                            viewModel.getPostListById(mPage, previousFeedWall.getPostId(), previousFeedWall.getPostUserType());//"Restaurant");
                        }
                    } else if (from.equalsIgnoreCase("rest_menu")) {
                        mPage = mPage + 1;
                        viewModel.getRestMenuPostViewList(mPage, previousFeedWall.getPostId(), previousFeedWall.getPostUserType());
                    } else {
                        mPage = mPage + 1;
                        //viewModel.getCustomerPostList(mPage);
                        viewModel.getCustomerTopPostList(mPage, previousFeedWall.getPostId(), "Customer");
                    }
                    Log.e("run", "run");
                    // your pagination code
                }
            }
        });

        viewModel.customerDetails.observe(getViewLifecycleOwner(), customerDetails -> {
            if (customerDetails != null) {
                binding.txtUseName.setText(viewModel.customerDetails.getValue().getName());
                GlideApp.with(getActivity())
                        .load(customerDetails.getProfilePicture())
                        .timeout(30000)
                        .into(binding.imgUser);
            }
        });

        viewModel.restaurantDetails.observe(getViewLifecycleOwner(), restaurantDetails -> {
            if (restaurantDetails != null) {
                binding.txtUseName.setText(restaurantDetails.getName());
                GlideApp.with(getActivity())
                        .load(restaurantDetails.getProfilePicture())
                        .timeout(30000)
                        .into(binding.imgUser);
            }
        });

        viewModel.custPostListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {

                if (mPage == 0) {
                    feedWallItemList.clear();
                    feedWallItemList = response.getCustomerPostList();
                    mFeedAdapter.setList(feedWallItemList);
                } else {
                    for (int i = 0; i < response.getCustomerPostList().size(); i++) {
                        mFeedAdapter.addRow(response.getCustomerPostList().get(i));
                    }
                    feedWallItemList.addAll(response.getCustomerPostList());
                }

            }
        });

        viewModel.menuDtailsResponse.observe(getViewLifecycleOwner(), reseponItemDetails -> {
            if (reseponItemDetails != null) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentData.RESTAURUNT_ID, selctedFeedwal.getRestaurantId());
                bundle.putSerializable(Constants.IntentData.DATA, reseponItemDetails);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_menuDetails, bundle);
            }
        });

        viewModel.postListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (isMyProfile) {
                    if (mPage == 0) {
                        feedWallItemList.clear();
                        feedWallItemList = response.getMyPostList();
                        mFeedAdapter.setList(feedWallItemList);
                    } else {
                        for (int i = 0; i < response.getMyPostList().size(); i++) {
                            mFeedAdapter.addRow(response.getMyPostList().get(i));
                        }
                        feedWallItemList.addAll(response.getMyPostList());
                    }
                } else {
                    if (mPage == 0) {
                        feedWallItemList.clear();
                        feedWallItemList = response.getRestaruntPostList();
                        mFeedAdapter.setList(feedWallItemList);
                    } else {
                        for (int i = 0; i < response.getRestaruntPostList().size(); i++) {
                            mFeedAdapter.addRow(response.getRestaruntPostList().get(i));
                        }
                        feedWallItemList.addAll(response.getRestaruntPostList());
                    }
                }

            }
        });

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), response -> {
            showSnackbar(response);
        });

        viewModel.isProgressEnabled.observe(getViewLifecycleOwner(), booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );
    }

    private void initVar() {
        if (getArguments() != null) {
            if (getArguments().getString(Constants.IntentData.FROM) != null) {
                from = getArguments().getString(Constants.IntentData.FROM);
            }

            if (getArguments().containsKey("isMyProfile")) {
                isMyProfile = getArguments().getBoolean("isMyProfile", false);
            }

            if (getArguments().containsKey("list_type")) {
                listType = getArguments().getInt("list_type");
            }

            if (getArguments().containsKey(Constants.IntentData.FEED_WALL_DATA)) {
                previousFeedWall = (FeedWallItem) getArguments().getSerializable(Constants.IntentData.FEED_WALL_DATA);
            }
        }
        binding.topBar.setActivity(getBaseActivity());
        binding.topBar.txtTitle.setText("Posts");
        if (from.equalsIgnoreCase("rest")) {
            binding.topBar.txtTitle.setText(getResources().getString(R.string.taged_photos));
            binding.imgUser.setImageResource(R.drawable.ic_res_logo);
            viewModel.restaurantDetails.setValue((RestaurantDetails) getArguments().getSerializable(Constants.IntentData.RESTAURUNT_DETAILS));
//            Log.e("restId", viewModel.restaurantDetails.getValue().getRestaurantId() + "");
            mPage = 0;
            if(listType==Constants.ListShowType.INSIGHT){
                viewModel.getInsightList(mPage, previousFeedWall.getPostId(), "Customer");
            }else if (isMyProfile) {
                //viewModel.getMyPostList(mPage);
                viewModel.getPostListById(mPage, previousFeedWall.getPostId(), "Restaurant");
            } else {
                //viewModel.getRestPostList(mPage);
                viewModel.getPostListById(mPage, previousFeedWall.getPostId(), previousFeedWall.getPostUserType());
            }
        } else if (from.equalsIgnoreCase("rest_menu")) {
            mPage = 0;
            binding.topBar.txtTitle.setText(getResources().getString(R.string.taged_photos));
            if (getArguments().containsKey(Constants.IntentData.MENU_NAME)) {
                binding.txtUseName.setText(getArguments().getString(Constants.IntentData.MENU_NAME));
                GlideApp.with(getActivity())
                        .load(getArguments().getString(Constants.IntentData.MENU_IMAGE))
                        .timeout(30000)
                        .into(binding.imgUser);
            }
            viewModel.getRestMenuPostViewList(mPage, previousFeedWall.getPostId(), previousFeedWall.getPostUserType());
        } else {
            viewModel.customerDetails.setValue((CustomerDetails) getArguments().getSerializable(Constants.IntentData.CUSTOMER_DATA));
            mPage = 0;
            //viewModel.getCustomerPostList(mPage);
            viewModel.getCustomerTopPostList(mPage, previousFeedWall.getPostId(), "Customer");
        }
        feedWallItemList = new ArrayList<>();
        mFeedAdapter = new FeedAdapter(this, "tagged");

        binding.recyclerViewFeed.setAdapter(mFeedAdapter);
        mFeedAdapter.setIsMyProfile(isMyProfile);
    }

    @Override
    public void viewComment(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", item);
        bundle.putBoolean("isMyProfile", isMyProfile);
        if(item.getPostUserType().equalsIgnoreCase("Restaurant") && item.getUserPostBy() == SharedPrefUtils.getIntData(getContext(),Constants.RESTAURANT_ID)){
            bundle.putBoolean("showOwnerMenu", true);
        }else{
            bundle.putBoolean("showOwnerMenu", false);
        }

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_navigation_comment, bundle);
    }

    @Override
    public void viewRestro(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == item.getRestaurantId()) {
            bundle.putBoolean("isMyProfile", true);
        } else {
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_navigation_restaurant_details, bundle);

    }

    @Override
    public void onPostLikeClick(int pos, FeedWallItem item) {
        feedWallItemList.get(pos).setLiked(item.getLiked());
        feedWallItemList.get(pos).setLikeCounter(item.getLikeCounter());
        viewModel.makeLike(item);
    }

    @Override
    public void onPostFavClick(int pos, FeedWallItem item) {
        feedWallItemList.get(pos).setFavorite(item.getFavorite());
        viewModel.makeFavourites(item);
    }

    @Override
    public void viewMenu(int pos, FeedWallItem item) {
        selctedFeedwal = item;
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentData.RESTAURUNT_ID, selctedFeedwal.getRestaurantId());
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_menuDetails, bundle);
        /*if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == item.getRestaurantId()) {
            viewModel.callMenuDetails(item.getMenuItemId());
        } else {
            viewModel.callOtherRestMenu(item.getRestaurantId(), item.getMenuItemId());
        }*/
    }

    @Override
    public void viewUser(int pos, FeedWallItem item) {
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_navigation_view_story);
    }

    /*@Override
    public void viewMenu(int pos, FeedWallItem item) {
     //   Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_tagged_photos_to_navigation_res_menu);
    }*/

    private void hideAllExpandedViews() {
        for (int i = 0; i < mFeedAdapter.getItemCount(); i++) {
            View view = binding.recyclerViewFeed.getChildAt(i).findViewById(R.id.llIcons);
            View view1 = binding.recyclerViewFeed.getChildAt(i).findViewById(R.id.txtDescMore);
            View view2 = binding.recyclerViewFeed.getChildAt(i).findViewById(R.id.txtDesc);
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(outToRightAnimation());
                view.setVisibility(View.INVISIBLE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
            }
        }
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(250);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }
}