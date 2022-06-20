package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ActivityRestaurantDetailsBinding;
import com.inlocal.restaurantapp.databinding.DialogReportStoryBinding;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.editprofile.view.EditProfileActivity;
import com.inlocal.restaurantapp.ui.favorites.view.FavoritesActivity;
import com.inlocal.restaurantapp.ui.followers.view.FollowersActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.PagerModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestBlockUser;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ViewPageModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.viewmodel.RestroDetailsViewModel;
import com.inlocal.restaurantapp.ui.userdetails.view.CustomerPostImagesAdapter;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RestaurantDetailsActivity extends BaseFragment<ActivityRestaurantDetailsBinding> implements ImagesAdapter.ImagesCallback, StoryImagesAdapter.StoryImagesCallback,
        CustomerPostImagesAdapter.ImagesCallback, InsightPostImagesAdapter.ImagesCallback,ViewsSliderAdapter.ViewPagerItemClickListener
        /* ListingViewPagerAdapter.ListPagerItemClick*/ {
    private boolean isMyProfile = false;
    @Inject
    ViewModelFactory viewModelFactory;
    private RestroDetailsViewModel viewModel;
    private int mPage = 0, mPageStory = 0;
    private List<FeedWallItem> myPost, insightList;
    private List<StoryItem> myStoryList;
    //private CustomerPostImagesAdapter adapter;
    private StoryImagesAdapter imageAdapter;
    private InsightPostImagesAdapter insightAdapter;
    private boolean postReceived=false, insightReceived=false,pagenated=true,isBothListLoaded = false,onResume=false;
    private ListingViewPagerAdapter pagerAdapter;
    private ViewsSliderAdapter sliderAdapter;
    private List<ViewPageModel> pageModelList = new ArrayList<>();

    @Override
    protected int layoutRes() {
        return R.layout.activity_restaurant_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            if(position == 0){
                viewModel.gridType.setValue(1);
            }else if(position==1){
                viewModel.gridType.setValue(2);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestroDetailsViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        init();

        viewModel.bothListSet.observe(getViewLifecycleOwner(), response->{
            if (response != null) {
                pageModelList.clear();
                if(myPost.size()==0){
                    myPost.add(new FeedWallItem());
                }
                if(insightList.size()==0){
                    insightList.add(new FeedWallItem());
                }
                pageModelList.add(new ViewPageModel(0, myPost,null));
                pageModelList.add(new ViewPageModel(1, null,insightList));
                sliderAdapter = new ViewsSliderAdapter(pageModelList,this::onPagerItemClick);
                binding.viewPager2.setAdapter(sliderAdapter);
                binding.viewPager2.registerOnPageChangeCallback(pageChangeCallback);
                if(pagenated){
                    if(viewModel.gridType.getValue()==1){
                        binding.viewPager2.setCurrentItem(0,false);
                    }else{
                        binding.viewPager2.setCurrentItem(1,false);
                    }
                    pagenated=false;
                }
            }
        });

        binding.nvScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nvScroll.getChildAt(binding.nvScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.nvScroll.getHeight() + binding.nvScroll.getScrollY()));

                if (diff == 0) {

                    if(viewModel.gridType.getValue()==1) {
                        if (myPost.size() < viewModel.totalPostListCount.getValue()) {
                            mPage = mPage + 1;
                            if (isMyProfile) {
                                viewModel.getMyPostList(mPage);
                            } else {
                                viewModel.getRestPostList(mPage);
                            }
                            pagenated=true;

                        }
                    }else{
                        if (!onResume && insightList.size() < viewModel.totalInsightListCount.getValue()) {
                            mPageStory = mPageStory + 1;
                            viewModel.getMyInsightsList(mPageStory);
                            pagenated=true;
                        }
                    }
                    Log.e("run", "run");
                    // your pagination code
                }
            }
        });
/*
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        viewModel.gridType.setValue(1);
                        break;
                    case 1:
                        viewModel.gridType.setValue(2);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/


        binding.llPost.setOnClickListener(v -> {
            displayPostList();
        });


        binding.llInsight.setOnClickListener(v -> {
            viewModel.gridType.setValue(2);
            /*binding.recyclerItem.setVisibility(View.GONE);
            binding.viewpager.setCurrentItem(1);*/
            binding.viewPager2.setCurrentItem(1,true);
        });
        viewModel.errorFromServer.observe(getViewLifecycleOwner(), this::showSnackbar);

        viewModel.postListResponse.observe(getViewLifecycleOwner(), response -> {
            postReceived = true;
            if (response != null) {
                if (isMyProfile) {
                    if (mPage == 0) {
                        myPost.clear();
                        myPost = response.getMyPostList();
                        //  adapter.setItemList(myPost);
                    } else {
                        /*for (int i = 0; i < response.getMyPostList().size(); i++) {
                            adapter.addRow(response.getMyPostList().get(i));
                        }*/
                        myPost.addAll(response.getMyPostList());

                    }
                } else {
                    if (mPage == 0) {
                        myPost.clear();
                        myPost = response.getRestaruntPostList();
                        //adapter.setItemList(myPost);
                    } else {
                        /*for (int i = 0; i < response.getRestaruntPostList().size(); i++) {
                            adapter.addRow(response.getRestaruntPostList().get(i));
                        }*/
                        myPost.addAll(response.getRestaruntPostList());
                    }
                }
            }
            postReceived=true;
            if(postReceived && insightReceived){
                viewModel.bothListSet.setValue(true);
            }
            displayPostList();
            if (postReceived && insightReceived) {
                isBothListLoaded = true;
            }

            if (isBothListLoaded) {
                List<PagerModel> pagerModelList = new ArrayList<>();
                pagerModelList.add(new PagerModel(Constants.ListShowType.POST, myPost, viewModel.totalPostListCount.getValue()!=null?viewModel.totalPostListCount.getValue():0));
                pagerModelList.add(new PagerModel(Constants.ListShowType.INSIGHT, insightList, viewModel.totalInsightListCount.getValue()!=null?viewModel.totalInsightListCount.getValue():0));
              //  pagerAdapter = new ListingViewPagerAdapter(getContext(), pagerModelList, this);
                // Adding the Adapter to the ViewPager
                //binding.viewpager.setAdapter(pagerAdapter);
            }
        });

        viewModel.restauruntDetails.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                binding.setData(response);
                GlideApp.with(RestaurantDetailsActivity.this)
                        .load(response.getCoverImage())
                        .timeout(30000)
                        .error(R.drawable.ic_cover)
                        .into(binding.ivCoverImage);

                GlideApp.with(RestaurantDetailsActivity.this)
                        .load(response.getProfilePicture())
                        .timeout(30000)
                        .error(R.drawable.ic_cover)
                        .into(binding.ivProfielImg);


                binding.toolbar.imgOptions.setVisibility(isMyProfile ? View.GONE : View.VISIBLE);
                binding.toolbar.txtTitle.setText(response.getName());
                binding.unfollow.setVisibility(View.VISIBLE);
                binding.editProfile.setVisibility(View.GONE);

                binding.setData(response);
                displayPostList();
            }
        });

        viewModel.storyListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (isMyProfile) {
                    if (mPageStory == 0) {
                        myStoryList.clear();
                        myStoryList = response.getMyStoryList();
                        imageAdapter.setStoryItemList(myStoryList);
                    } else {
                        for (int i = 0; i < response.getMyStoryList().size(); i++) {
                            imageAdapter.addRow(response.getMyStoryList().get(i));
                        }
                        myStoryList.addAll(response.getMyStoryList());
                    }
                } else {
                    if (mPageStory == 0) {
                        myStoryList.clear();
                        myStoryList = response.getMyRestauruntStoryList();
                        imageAdapter.setStoryItemList(myStoryList);
                    } else {
                        for (int i = 0; i < response.getMyRestauruntStoryList().size(); i++) {
                            imageAdapter.addRow(response.getMyRestauruntStoryList().get(i));
                        }
                        myStoryList.addAll(response.getMyRestauruntStoryList());
                    }
                }
            }
        });

        viewModel.bloackReponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), getResources().getString(R.string.User_Blocked), Toast.LENGTH_SHORT).show();
                getBaseActivity().onBackPressed();
            }
        });

        /*viewModel.isProgressEnabled.observe(getViewLifecycleOwner(), booleanEvent -> {

                    if (booleanEvent.getContentIfNotHandled() != null && booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }

                }
        );*/
/*
        binding.recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (myPost.size() < viewModel.totalPostListCount.getValue()) {
                        mPage = mPage + 1;
                        if (isMyProfile) {
                            viewModel.getMyPostList(mPage);
                        } else {
                            viewModel.getRestPostList(mPage);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/

        viewModel.totalInsightListCount.observe(getViewLifecycleOwner(),response->{
            if(response==null) {
                insightReceived=true;
                if (postReceived && insightReceived) {
                    isBothListLoaded = true;
                }

                if (isBothListLoaded) {
                    List<PagerModel> pagerModelList = new ArrayList<>();
                    pagerModelList.add(new PagerModel(Constants.ListShowType.POST, myPost,viewModel.totalPostListCount.getValue()!=null?viewModel.totalPostListCount.getValue():0));
                    pagerModelList.add(new PagerModel(Constants.ListShowType.INSIGHT, insightList,viewModel.totalInsightListCount.getValue()!=null?viewModel.totalInsightListCount.getValue():0));
                    //pagerAdapter = new ListingViewPagerAdapter(getContext(), pagerModelList, this);
                    // Adding the Adapter to the ViewPager
                  //  binding.viewpager.setAdapter(pagerAdapter);
                }
            }
        });

        viewModel.insightListResponse.observe(getViewLifecycleOwner(), response -> {

            if (response != null) {
                //if (isMyProfile) {
                if (mPageStory == 0) {
                    insightList.clear();
                    insightList = response.getInsightPostList();
                    insightAdapter.setItemList(insightList);
                } else {
                    for (int i = 0; i < response.getInsightPostList().size(); i++) {
                        if(response.getInsightPostList().get(i)!=null) {
                            insightAdapter.addRow(response.getInsightPostList().get(i));
                        }
                        //  pagerAdapter.addRow(Constants.ListShowType.INSIGHT,response.getInsightPostList().get(i));
                    }
                    //insightList.addAll(response.getInsightPostList());
                }
                onResume=false;
                // }
            }



            insightReceived=true;
            if(postReceived && insightReceived){
                viewModel.bothListSet.setValue(true);
            }

            if (postReceived && insightReceived) {
                isBothListLoaded = true;
            }

            if (isBothListLoaded) {
                List<PagerModel> pagerModelList = new ArrayList<>();
                pagerModelList.add(new PagerModel(Constants.ListShowType.POST, myPost,viewModel.totalPostListCount.getValue()!=null?viewModel.totalPostListCount.getValue():0));
                pagerModelList.add(new PagerModel(Constants.ListShowType.INSIGHT, insightList,viewModel.totalInsightListCount.getValue()!=null?viewModel.totalInsightListCount.getValue():0));
                //pagerAdapter = new ListingViewPagerAdapter(getContext(), pagerModelList, this);
                // Adding the Adapter to the ViewPager
                //binding.viewpager.setAdapter(pagerAdapter);
            }

        });

       /* binding.recyclerInSight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //if (myStoryList.size() < viewModel.totalStoryListCount.getValue()) {
                    if (myStoryList.size() < viewModel.totalInsightListCount.getValue()) {
                        mPageStory = mPageStory + 1;
                        viewModel.getMyInsightsList(mPageStory);
                        *//*if (isMyProfile) {
                            viewModel.getMyStoryList(mPageStory);
                        } else {
                            viewModel.getRestStoryList(mPageStory);
                        }*//*
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
    }

    private void init() {
        myPost = new ArrayList<>();
        insightList = new ArrayList<>();
        myStoryList = new ArrayList<>();
        //adapter = new CustomerPostImagesAdapter(this);
        //adapter.setItemList(myPost);
        insightAdapter = new InsightPostImagesAdapter(this);
        imageAdapter = new StoryImagesAdapter(this);
        imageAdapter.setStoryItemList(myStoryList);
        //binding.recyclerItem.setAdapter(adapter);
        // binding.recyclerInSight.setAdapter(imageAdapter);
        binding.recyclerInSight.setAdapter(insightAdapter);
       // binding.toolbar.imgOptions.setVisibility(View.VISIBLE);
        binding.toolbar.setActivity(getBaseActivity());
       // binding.tablayout.setupWithViewPager(binding.viewpager);

        /*ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        // add your fragments
        adapter.addFrag(new PostListFragment(), "");
        adapter.addFrag(new InsightListFragment(), "");
        // set adapter on viewpager
        binding.viewpager.setAdapter(adapter);*/
        if (getArguments() != null && getArguments().getBoolean("isMyProfile", false)) {
            isMyProfile = getArguments().getBoolean("isMyProfile", false);
            viewModel.restId.setValue(SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID));
        }

        if (getArguments().containsKey(Constants.IntentData.FEED_WALL_DATA)) {
            FeedWallItem feedWallItem = (FeedWallItem) getArguments().getSerializable(Constants.IntentData.FEED_WALL_DATA);
            if (feedWallItem.getRestaurantId() != null) {
                viewModel.restId.setValue(feedWallItem.getRestaurantId());
            }
        }

        if (getArguments().containsKey(Constants.IntentData.RESTAURUNT_ID)) {
            viewModel.restId.setValue(getArguments().getInt(Constants.IntentData.RESTAURUNT_ID, 0));
        }

        if (isMyProfile) {
            binding.toolbar.imgOptions.setVisibility(View.GONE);
            binding.toolbar.txtTitle.setText(SharedPrefUtils.getStringData(getContext(), Constants.SharePref.USER_NAME));
            binding.unfollow.setVisibility(View.GONE);
            binding.editProfile.setVisibility(View.VISIBLE);
        }

        binding.toolbar.imgOptions.setOnClickListener(v -> {
            showBottomDialog();
        });

        binding.editProfile.setOnClickListener(v -> {
            NavUtil.ForActivity.navTo(getBaseActivity(), EditProfileActivity.class, false, null);
        });
        binding.llFollowers.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("type", 1);
            b.putBoolean("isMyProfile", isMyProfile);
            if (!isMyProfile) {
                b.putString(Constants.IntentData.CUSTOMER_TYPE, "Restaurant");
                b.putInt(Constants.IntentData.OTHER_USER_ID, viewModel.restId.getValue());
            }
            NavUtil.ForActivity.navTo(getBaseActivity(), FollowersActivity.class, false, b);
        });
        binding.llFollowings.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("type", 2);
            b.putBoolean("isMyProfile", isMyProfile);
            if (!isMyProfile) {
                b.putString(Constants.IntentData.CUSTOMER_TYPE, "Restaurant");
                b.putInt(Constants.IntentData.OTHER_USER_ID, viewModel.restId.getValue());
            }
            NavUtil.ForActivity.navTo(getBaseActivity(), FollowersActivity.class, false, b);
        });

        binding.menu.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            bundle.putInt(Constants.IntentData.RESTAURUNT_ID, viewModel.restId.getValue());
            if(isMyProfile) {
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails());
            }else{
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
            }
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_res_menu,bundle);
        });
        binding.delivery.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            bundle.putInt(Constants.IntentData.RESTAURUNT_ID, viewModel.restId.getValue());
            if(isMyProfile) {
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails());
            }else{
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
            }
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_res_menu,bundle);

        });
        binding.info.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            if (isMyProfile) {
                bundle.putSerializable(Constants.IntentData.DATA, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue());
            } else {
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
            }
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_restro_info, bundle);
        });

        viewModel.restaurantProfileDetailsResponseMutableLiveData.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                GlideApp.with(RestaurantDetailsActivity.this)
                        .load(response.getRestaurantDetails().getCoverImage())
                        .timeout(30000)
                        .error(R.drawable.ic_cover)
                        .into(binding.ivCoverImage);

                GlideApp.with(RestaurantDetailsActivity.this)
                        .load(response.getRestaurantDetails().getProfilePicture())
                        .timeout(30000)
                        .error(R.drawable.ic_cover)
                        .into(binding.ivProfielImg);

                binding.setData(response.getRestaurantDetails());
            }

        });
    }

    @Override
    public void click(int pos, PostItem item) {
        /*Bundle bundle = new Bundle();
        bundle.putString("from", "rest");
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        bundle.putSerializable(Constants.IntentData.CUSTOMER_DATA, viewModel.customerDetails.getValue());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_tagged_photos, bundle);*/
    }

    private void showBottomDialog() {
        Dialog dialog = new Dialog(getBaseActivity());
        DialogReportStoryBinding dialogLogoutBinding = DialogReportStoryBinding.inflate(LayoutInflater.from(getBaseActivity()));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialogLogoutBinding.txtBlock.setVisibility(View.VISIBLE);
        dialogLogoutBinding.viewBlock.setVisibility(View.VISIBLE);
        dialogLogoutBinding.txtReport.setVisibility(View.GONE);
        dialogLogoutBinding.viewBlock.setVisibility(View.GONE);
        dialogLogoutBinding.txtShare.setVisibility(View.GONE);
        dialogLogoutBinding.viewShare.setVisibility(View.GONE);
        dialogLogoutBinding.txtBlock.setOnClickListener(v -> {
            dialog.cancel();
            viewModel.callBlockUser(new RequestBlockUser(viewModel.restauruntDetails.getValue().getRestaurantId(), "Restaurant", "Restaurant"));
        });
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.txtShare.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.txtReport.setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMyProfile) {
            binding.toolbar.txtTitle.setText(SharedPrefUtils.getStringData(getContext(), Constants.SharePref.USER_NAME));
            viewModel.getProfileDetails();
            mPage = 0;
            viewModel.getMyPostList(mPage);
            mPageStory = 0;
            //viewModel.getMyStoryList(mPageStory);
            viewModel.getMyInsightsList(mPageStory);
        } else {
            viewModel.getRestauruntDetails(viewModel.restId.getValue());
            mPage = 0;
            viewModel.getRestPostList(mPage);
            mPageStory = 0;
            //viewModel.getRestStoryList(mPageStory);
            onResume=true;
            viewModel.getMyInsightsList(mPageStory);
        }
    }

   /* @Override
    public void onStoryItemClick(int pos, FeedWallItem storyItem) {

    }*/

    private void displayPostList() {
        viewModel.gridType.setValue(1);
        binding.viewPager2.setCurrentItem(0,true);
        //binding.recyclerInSight.setVisibility(View.GONE);
        //viewModel.gridType.setValue(1);

        //binding.viewpager.setCurrentItem(0);
        // binding.recyclerItem.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        //binding.tvNoRecord.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void click(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "rest");
        bundle.putBoolean("isMyProfile", isMyProfile);
        bundle.putInt("list_type", Constants.ListShowType.POST);
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        if (isMyProfile) {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails());
        } else {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_tagged_photos, bundle);
    }

    @Override
    public void clickForPagerAdapterItem(Integer showType, int pos, FeedWallItem item) {

    }

    @Override
    public void onInsightClick(int pos, FeedWallItem item) {
        pagenated=false;
        Bundle bundle = new Bundle();
        bundle.putString("from", "rest");
        bundle.putBoolean("isMyProfile", isMyProfile);
        bundle.putInt("list_type", Constants.ListShowType.INSIGHT);
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        if (isMyProfile) {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails());
        } else {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_tagged_photos, bundle);
    }

  /*  @Override
    public void onPagerItemClick(Integer showType, int listItemPos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "rest");
        bundle.putInt("list_type", showType);
        bundle.putBoolean("isMyProfile", isMyProfile);
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);

        if (isMyProfile) {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails());
        } else {
            bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, viewModel.restauruntDetails.getValue());
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_restaurant_details_to_navigation_tagged_photos, bundle);
    }*/

    /*@Override
    public void onPagerListScroll(Integer mPage, int showType) {
        if(showType == Constants.ListShowType.POST) {
            if (isMyProfile) {
                viewModel.getMyPostList(mPage);
            } else {
                viewModel.getRestPostList(mPage);
            }
        }else{
            viewModel.getMyInsightsList(mPage);
        }
    }*/

    @Override
    public void onStoryItemClick(int pos, StoryItem storyItem) {

    }


    @Override
    public void onPagerItemClick(int pagePosition, ViewPageModel item, int itemPosition) {
        if(pagePosition==0){
            click(itemPosition,item.getMyPost().get(itemPosition));
        }else{
            onInsightClick(itemPosition,item.getMyInsight().get(itemPosition));
        }
    }
}