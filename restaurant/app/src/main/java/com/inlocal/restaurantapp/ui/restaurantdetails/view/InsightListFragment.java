package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.FragmentPostListBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.restaurantdetails.viewmodel.RestroDetailsViewModel;
import com.inlocal.restaurantapp.ui.userdetails.view.CustomerPostImagesAdapter;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InsightListFragment extends BaseFragment<FragmentPostListBinding> implements ImagesAdapter.ImagesCallback, StoryImagesAdapter.StoryImagesCallback, CustomerPostImagesAdapter.ImagesCallback {
    private boolean isMyProfile = false;
    @Inject
    ViewModelFactory viewModelFactory;
    private RestroDetailsViewModel viewModel;
    private int mPage = 0, mPageStory = 0;
    private List<FeedWallItem> myPost, insightList;
    private List<StoryItem> myStoryList;
    private CustomerPostImagesAdapter adapter;
    private StoryImagesAdapter imageAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_post_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestroDetailsViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        init();
        Log.e("frag","insight_fragment");

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), this::showSnackbar);

        viewModel.postListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (isMyProfile) {
                    if (mPage == 0) {
                        myPost.clear();
                        myPost = response.getMyPostList();
                        adapter.setItemList(myPost);
                    } else {
                        for (int i = 0; i < response.getMyPostList().size(); i++) {
                            adapter.addRow(response.getMyPostList().get(i));
                        }
                        myPost.addAll(response.getMyPostList());
                    }
                } else {
                    if (mPage == 0) {
                        myPost.clear();
                        myPost = response.getRestaruntPostList();
                        adapter.setItemList(myPost);
                    } else {
                        for (int i = 0; i < response.getRestaruntPostList().size(); i++) {
                            adapter.addRow(response.getRestaruntPostList().get(i));
                        }
                        myPost.addAll(response.getRestaruntPostList());
                    }
                }
            }
            displayPostList();
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


       /* viewModel.insightListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (isMyProfile) {
                    if (mPageStory == 0) {
                        myStoryList.clear();
                        insightList = response.getMyPostList();
                        imageAdapter.setStoryItemList(insightList);
                    } else {
                        for (int i = 0; i < response.getMyPostList().size(); i++) {
                            imageAdapter.addRow(response.getMyPostList().get(i));
                        }
                        insightList.addAll(response.getMyPostList());
                    }
                }
            }
        });*/

        viewModel.bloackReponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), getResources().getString(R.string.User_Blocked), Toast.LENGTH_SHORT).show();
                getBaseActivity().onBackPressed();
            }
        });

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
        });

        binding.tvNoRecord.setVisibility(View.VISIBLE);
        binding.tvNoRecord.setText("Fragment Insight");
    }

    private void init() {
        myPost = new ArrayList<>();
        insightList = new ArrayList<>();
        myStoryList = new ArrayList<>();
        adapter = new CustomerPostImagesAdapter(this);
        adapter.setItemList(myPost);
        imageAdapter = new StoryImagesAdapter(this);
        imageAdapter.setStoryItemList(myStoryList);
        binding.recyclerItem.setAdapter(adapter);
    }

    @Override
    public void click(int pos, PostItem item) {

    }



    @Override
    public void onResume() {
        super.onResume();
        if (isMyProfile) {
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
            viewModel.getMyInsightsList(mPageStory);
            //viewModel.getRestStoryList(mPageStory);
        }
    }

    @Override
    public void onStoryItemClick(int pos, StoryItem storyItem) {

    }

    private void displayPostList() {
        viewModel.gridType.setValue(1);
        binding.recyclerItem.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        binding.tvNoRecord.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
        binding.tvNoRecord.setVisibility(View.VISIBLE);
        binding.tvNoRecord.setText("Fragment Insight");
    }

    @Override
    public void click(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "rest");
        bundle.putBoolean("isMyProfile", isMyProfile);
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
}