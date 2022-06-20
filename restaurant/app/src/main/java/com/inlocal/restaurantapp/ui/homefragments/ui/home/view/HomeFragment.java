package com.inlocal.restaurantapp.ui.homefragments.ui.home.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.FragmentHomeBinding;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.auth.splash.view.SplashActivity;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivity;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.UsersFollowModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.viewmodel.HomeViewModel;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements FeedAdapter.FeedCallBack, UsersFollowAdapter.UserCallback {
    @Inject
    ViewModelFactory viewModelFactory;
    private HomeViewModel vm;
    private UsersFollowAdapter mUsersFollowAdapter;
    private FeedAdapter mFeedAdapter;
    private List<FeedWallItem> feedWallItemList;
    private List<StoryItem> storyList;
    private FeedWallItem selctedFeedwal;
    private int mPage = 0, mStoryPage = 0;
    private static final int PERMISSION_CODE = 401;
    private HomeActivityUpdateListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if ( context instanceof HomeActivityUpdateListener ) {
            listener = (HomeActivityUpdateListener) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName()
                    + " must implement Callback");
        }*/
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        init();
    }

    private void init() {
        feedWallItemList = new ArrayList<>();
        storyList = new ArrayList<>();
        mUsersFollowAdapter = new UsersFollowAdapter(this);
        mUsersFollowAdapter.setHasStableIds(true);
        mUsersFollowAdapter.setList(storyList);
        binding.recyclerViewUsers.setAdapter(mUsersFollowAdapter);
        binding.recyclerViewUsers.setItemAnimator(null);
        binding.recyclerViewUsers.setAnimation(null);
        //addDataToTopList();
        mFeedAdapter = new FeedAdapter(this, "");
        mFeedAdapter.setHasStableIds(true);
        binding.recyclerViewFeed.setAdapter(mFeedAdapter);
        binding.recyclerViewFeed.setItemAnimator(null);
        mFeedAdapter.setList(feedWallItemList);
        binding.imgCamera.setOnClickListener(v -> checkPermission());
        binding.layoutNotification.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_notifications));

        if (getArguments()!=null && getArguments().containsKey(Constants.IntentKey.RedirectionTarget) && getArguments().containsKey(Constants.IntentKey.RedirectionExtra)) {
            /*Bundle bundle = new Bundle();
            bundle.putString(Constants.IntentKey.RedirectionTarget, getArguments().getString(Constants.IntentKey.RedirectionTarget));
            bundle.putInt(Constants.IntentKey.RedirectionExtra, getArguments().getInt(Constants.IntentKey.RedirectionExtra, 0));*/
            Log.e("data_redi",getArguments().getString(Constants.IntentKey.RedirectionTarget)+", "+getArguments().getInt(Constants.IntentKey.RedirectionExtra, 0));
            moveToNextScreen(getArguments().getString(Constants.IntentKey.RedirectionTarget),getArguments().getInt(Constants.IntentKey.RedirectionExtra, 0));
        }

        binding.nvScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideAllExpandedViews();
                return false;
            }
        });


        binding.layoutReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.layoutReferesh.setRefreshing(false);
                mPage = 0;
                vm.getFeedWallList(mPage);
            }
        });

        binding.recyclerViewUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (storyList.size() < vm.totalStoryListCount.getValue()) {
                        mStoryPage = mStoryPage + 1;
                        vm.getStoryList(mStoryPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.nvScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.nvScroll.getChildAt(binding.nvScroll.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.nvScroll.getHeight() + binding.nvScroll.getScrollY()));

                if (diff == 0) {

                        if (feedWallItemList.size() < vm.totalFeedWalListCoun.getValue()) {
                            mPage = mPage + 1;
                            vm.getFeedWallList(mPage);
                        }
                    Log.e("run", "run");
                    // your pagination code
                }
            }
        });

        /*binding.recyclerViewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/

        vm.errorFromServer.observe(getViewLifecycleOwner(), msg -> {
            showSnackbar(msg);
        });

        vm.getGlobalConfig.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.getNotificationCount() != null) {
                    if (response.getNotificationCount() > 0) {
                        // binding.ivNotification.setImageResource(R.drawable.ic_notification_unread);
                        if (response.getNotificationCount() > 10) {
                            binding.tvNotiCount.setText("10+");
                        } else {
                            binding.tvNotiCount.setText(response.getNotificationCount()+"");
                        }
                        binding.tvNotiCount.setVisibility(View.VISIBLE);
                    } else {
                        //binding.ivNotification.setImageResource(R.drawable.ic_notification_read);
                        binding.tvNotiCount.setVisibility(View.GONE);
                    }
                    /*if(listener!=null) {
                        listener.onNotificationCountUpdate(response.getNotificationCount());
                    }*/
                } else {
                    binding.ivNotification.setImageResource(R.drawable.ic_notification_read);
                }
            }
        });

        vm.responseCustomerDetails.observe(getViewLifecycleOwner(), new Observer<ResponseCustomerDetails>() {
            @Override
            public void onChanged(ResponseCustomerDetails responseCustomerDetails) {
                if (responseCustomerDetails != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.IntentData.CUSTOMER_DATA, responseCustomerDetails.getCustomerDetails());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_user_details, bundle);
                }
            }
        });

        vm.feedWallListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (mPage == 0) {
                    feedWallItemList.clear();
                    feedWallItemList = response.getMyFeedWallPosts();
                    mFeedAdapter.setList(feedWallItemList);
                } else {
                    for (int i = 0; i < response.getMyFeedWallPosts().size(); i++) {
                        mFeedAdapter.addRow(response.getMyFeedWallPosts().get(i));
                    }
                    feedWallItemList.addAll(response.getMyFeedWallPosts());
                }
            }

            if (mFeedAdapter.getItemCount() > 0) {
                binding.recyclerViewFeed.setVisibility(View.VISIBLE);
                binding.tvNoRecord.setVisibility(View.GONE);
            } else {
                binding.recyclerViewFeed.setVisibility(View.GONE);
                binding.tvNoRecord.setVisibility(View.VISIBLE);
            }
        });

        vm.storyListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (mStoryPage == 0) {
                    storyList.clear();
                    storyList = response.getMyStoryList();
                    mUsersFollowAdapter.setList(storyList);
                } else {
                    for (int i = 0; i < response.getMyStoryList().size(); i++) {
                        mUsersFollowAdapter.addRow(response.getMyStoryList().get(i));
                    }
                    storyList.addAll(response.getMyStoryList());
                }
            }
        });

        vm.getStoryList(mStoryPage);
        vm.getFeedWallList(mPage);
    }

    private void addDataToTopList() {
        /*mUsersFollowAdapter.mData.add(new UsersFollowModel("John Deo", "https://i.pravatar.cc/300"));
        mUsersFollowAdapter.mData.add(new UsersFollowModel("Sonnie Hiles", "https://i.pravatar.cc/200"));
        mUsersFollowAdapter.mData.add(new UsersFollowModel("Petrik Velich", "https://i.pravatar.cc/250"));
        mUsersFollowAdapter.mData.add(new UsersFollowModel("Michalina", "https://i.pravatar.cc/500"));
        mUsersFollowAdapter.mData.add(new UsersFollowModel("Krutika Chotara", "https://i.pravatar.cc/350"));
        mUsersFollowAdapter.mData.add(new UsersFollowModel("Dmitry Vechorko", "https://i.pravatar.cc/400"));*/
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            NavUtil.ForActivity.navTo(getBaseActivity(), CameraActivity.class, false, null);
        } else {
            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE);
        }
    }

    @Override
    public void viewComment(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", item);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_comment, bundle);
    }

    @Override
    public void viewRestro(int pos, FeedWallItem item) {

        if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == item.getRestaurantId()) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isMyProfile", true);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_restaurant_details, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_restaurant_details, bundle);
        }
    }

    @Override
    public void onPostLikeClick(int pos, FeedWallItem item) {
        feedWallItemList.get(pos).setLiked(item.getLiked());
        feedWallItemList.get(pos).setLikeCounter(item.getLikeCounter());
        vm.makeLike(item);
    }

    @Override
    public void onPostFavClick(int pos, FeedWallItem item) {
        feedWallItemList.get(pos).setFavorite(item.getFavorite());
        vm.makeFavourites(item);
    }

    @Override
    public void viewUser(int pos, FeedWallItem item) {
        if (item.getPostUserType().equalsIgnoreCase("Restaurant")) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_restaurant_details, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentData.CUSTOMER_ID, item.getUserPostBy());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_user_details, bundle);
            //vm.getCustomerDetails(item.getUserPostBy());
        }
    }

    @Override
    public void viewMenu(int pos, FeedWallItem item) {
        selctedFeedwal = item;
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentData.RESTAURUNT_ID, selctedFeedwal.getRestaurantId());
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_menuDetails, bundle);
        /*if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == item.getRestaurantId()) {
            vm.callMenuDetails(item.getMenuItemId());
        } else {
            vm.callOtherRestMenu(item.getRestaurantId(), item.getMenuItemId());
        }*/
    }

    @Override
    public void onStoryClick(int pos, StoryItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DATA, item);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_view_story, bundle);
    }

    private void hideAllExpandedViews() {
        for (int i = 0; i < mFeedAdapter.getItemCount(); i++) {
            if (binding.recyclerViewFeed.getChildAt(i) != null) {
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
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +0.5f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(250);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.gloablConfig();
    }

    public interface HomeActivityUpdateListener {
        void onNotificationCountUpdate(int count);
    }

    private void moveToNextScreen(String key, Integer redirectionId) {
        Bundle bundle = new Bundle();
        switch (key) {
            case "new_post_created_type":
            case "customer_post_liked_type":
            case "customer_post_commented_type":
            case "user_follower_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.POST_ID, redirectionId);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_comment, bundle);
                break;
            case "new_order_received_type":
            case "order_status_update_type":
                //case "payment_received_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.ORDER_ID, redirectionId);
                NavUtil.ForActivity.navTo(getBaseActivity(), BookingDetailsActivity.class, false, bundle);
                break;
            case "new_booking_received_type":
            case "booking_status_update_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.ORDER_ID, redirectionId);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_bookings, bundle);
                break;
        }
    }
}