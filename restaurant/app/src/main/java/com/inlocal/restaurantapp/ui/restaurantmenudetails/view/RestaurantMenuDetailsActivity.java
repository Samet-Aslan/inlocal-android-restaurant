package com.inlocal.restaurantapp.ui.restaurantmenudetails.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.ActivityRestaurantMenuDetailsBinding;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.viewmodel.RestaurantMenuDetailsViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RestaurantMenuDetailsActivity extends BaseFragment<ActivityRestaurantMenuDetailsBinding> implements ImagesAdapter.ImagesCallback {
    @Inject
    ViewModelFactory viewModelFactory;
    private RestaurantMenuDetailsViewModel viewModel;
    int count = 1;
    private String from = "";
    private Context mContext;
    private ImagesAdapter adapter;
    private int mPage = 0, restId = 0;
    private List<FeedWallItem> postList;
    private MenuDetailsResponse menuDetailsResponse;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_restaurant_menu_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestaurantMenuDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(getBaseActivity());
        initVar();
        /*binding.ivBack.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).popBackStack();
        });*/

        viewModel.menuDtailsResponse.observe(getViewLifecycleOwner(), reseponItemDetails -> {
            if (reseponItemDetails != null) {
                menuDetailsResponse = reseponItemDetails;
                binding.setData(menuDetailsResponse);
                restId = getArguments().getInt(Constants.IntentData.RESTAURUNT_ID);
                binding.tvTitle.setText(menuDetailsResponse.getMenuItemDetails().getName());
                binding.tvDesc.setText("€ "+reseponItemDetails.getMenuItemDetails().getPrice());
                if(menuDetailsResponse!=null) {
                    GlideApp.with(getContext())
                            .load(menuDetailsResponse.getMenuItemDetails().getImage())
                            .timeout(30000)
                            .error(R.drawable.food)
                            .into(binding.ivCoverImage);
                }
            }

            if(menuDetailsResponse!=null) {
                mPage = 0;
                viewModel.getMenuPostList(menuDetailsResponse.getMenuItemDetails().getId(), restId, mPage);
            }
        });

        viewModel.menuListResponse.observe(getViewLifecycleOwner(), response -> {

            if (response != null) {
                if (mPage == 0) {
                    postList.clear();
                    postList = response.getMenuItemDetails().getPostList();
                    adapter.setList(postList);
                } else {
                    for (int i = 0; i < response.getMenuItemDetails().getPostList().size(); i++) {
                        adapter.addRow(response.getMenuItemDetails().getPostList().get(i));
                    }
                    postList.addAll(response.getMenuItemDetails().getPostList());
                }
            }

        });

        binding.rvGridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (postList.size() < viewModel.totalPostCount.getValue()) {
                        mPage = mPage + 1;
                        viewModel.getMenuPostList(menuDetailsResponse.getMenuItemDetails().getId(), restId, mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //binding.rvGridView.setExpanded(true);


      /*  binding.llPlus.setOnClickListener(v -> {
            count = count + 1;
            binding.tvCount.setText(String.valueOf(count));

        });
*/

        binding.layoutIncrement.setOnClickListener(v -> {
//            NavUtil.ForActivity.navTo(getActivity(), CustomizationActivity.class, false, null);
        });
       /* binding.llMinus.setOnClickListener(v -> {
            if (count > 0) {
                count = count - 1;
                binding.tvCount.setText(String.valueOf(count));
            } else {
                count = 0;
                binding.tvCount.setText("0");
            }
        });*/
        binding.tvCustomization.setOnClickListener(v -> {
//            NavUtil.ForActivity.navTo(getActivity(), CustomizationActivity.class, false, null);
        });


//        binding.rvGridView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//
//        });
    }

    private void initVar() {
        //binding.setActivity(getBaseActivity());
        postList = new ArrayList<>();
        adapter = new ImagesAdapter(this);
        binding.rvGridView.setAdapter(adapter);
        binding.rvGridView.setNestedScrollingEnabled(false);

        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.IntentData.DATA)) {
                menuDetailsResponse = (MenuDetailsResponse) getArguments().getSerializable(Constants.IntentData.DATA);
                binding.setData(menuDetailsResponse);
                restId = getArguments().getInt(Constants.IntentData.RESTAURUNT_ID);
                binding.tvTitle.setText(menuDetailsResponse.getMenuItemDetails().getName());
                binding.tvDesc.setText("€ "+menuDetailsResponse.getMenuItemDetails().getPrice());
                if(menuDetailsResponse!=null) {
                    GlideApp.with(getContext())
                            .load(menuDetailsResponse.getMenuItemDetails().getImage())
                            .timeout(30000)
                            .error(R.drawable.food)
                            .into(binding.ivCoverImage);
                }
            }

            if (getArguments().containsKey(Constants.IntentData.FEED_WALL_DATA)) {
                FeedWallItem feedWallItem = (FeedWallItem) getArguments().getSerializable(Constants.IntentData.FEED_WALL_DATA);
                if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == feedWallItem.getRestaurantId()) {
                    viewModel.callMenuDetails(feedWallItem.getMenuItemId());
                } else {
                    viewModel.callOtherRestMenu(feedWallItem.getRestaurantId(), feedWallItem.getMenuItemId());
                }
            }

            if (getArguments().containsKey(Constants.IntentData.MENU_ID)) {
                viewModel.callOtherRestMenu(getArguments().getInt(Constants.IntentData.RESTAURUNT_ID),getArguments().getInt(Constants.IntentData.MENU_ID));
            }
        }
        binding.layoutIncrement.setVisibility(View.GONE);
    }


    @Override
    public void click(int pos, FeedWallItem item) {

        Bundle bundle = new Bundle();
        bundle.putString("from", "rest_menu");
        bundle.putBoolean("isMyProfile", false);
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        RestaurantDetails restaurantDetails = new RestaurantDetails();
        restaurantDetails.setRestaurantId(item.getRestaurantId());
        restaurantDetails.setName(item.getRestaurantName());
        restaurantDetails.setProfilePicture(item.getRestauranImg());
        bundle.putString(Constants.IntentData.MENU_NAME, menuDetailsResponse.getMenuItemDetails().getName());
        bundle.putString(Constants.IntentData.MENU_IMAGE, menuDetailsResponse.getMenuItemDetails().getImage());
        bundle.putSerializable(Constants.IntentData.RESTAURUNT_DETAILS, restaurantDetails);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_menu_details_to_taggedFragment,bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(menuDetailsResponse!=null) {
            mPage = 0;
            viewModel.getMenuPostList(menuDetailsResponse.getMenuItemDetails().getId(), restId, mPage);
        }
    }
}