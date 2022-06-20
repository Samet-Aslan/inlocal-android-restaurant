package com.inlocal.restaurantapp.ui.userdetails.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.CustomerDetails;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ActivityUserDetailsBinding;
import com.inlocal.restaurantapp.databinding.DialogReportStoryBinding;
import com.inlocal.restaurantapp.ui.comment.viewmodel.CommentViewModel;
import com.inlocal.restaurantapp.ui.followers.view.FollowersActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.userdetails.viewmodel.UserDetailsViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class UserDetailsActivity extends BaseFragment<ActivityUserDetailsBinding> implements CustomerPostImagesAdapter.ImagesCallback {

    @Inject
    ViewModelFactory viewModelFactory;
    private UserDetailsViewModel viewModel;
    private int mPage = 0;
    private List<FeedWallItem> postItemList;
    private CustomerPostImagesAdapter mPostAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_user_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(UserDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();
        binding.llFollowers.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("type", 1);
            b.putBoolean("isMyProfile", false);
            b.putString(Constants.IntentData.CUSTOMER_TYPE, "Customer");
            b.putInt(Constants.IntentData.OTHER_USER_ID, viewModel.customerDetails.getValue().getCustomerId());
            NavUtil.ForActivity.navTo(getBaseActivity(), FollowersActivity.class, false, b);
        });
        binding.llFollowings.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("type", 2);
            b.putBoolean("isMyProfile", false);
            b.putString(Constants.IntentData.CUSTOMER_TYPE, "Customer");
            b.putInt(Constants.IntentData.OTHER_USER_ID, viewModel.customerDetails.getValue().getCustomerId());
            NavUtil.ForActivity.navTo(getBaseActivity(), FollowersActivity.class, false, b);
        });

        viewModel.responseCustomerDetails.observe(getViewLifecycleOwner(), new Observer<ResponseCustomerDetails>() {
            @Override
            public void onChanged(ResponseCustomerDetails responseCustomerDetails) {
                if (responseCustomerDetails != null) {
                    /*Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.IntentData.CUSTOMER_DATA, responseCustomerDetails.getCustomerDetails());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_user_details, bundle);*/
                }
            }
        });

       /* binding.btnFollow.setOnClickListener(v -> {
            if (binding.btnFollow.getText().toString().equalsIgnoreCase(getString(R.string.follow))) {
                binding.btnFollow.setText(getString(R.string.unfollow));
                binding.btnFollow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.disabled_corner_radius_button));
            } else {
                binding.btnFollow.setText(getString(R.string.follow));
                binding.btnFollow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.blue_corner_radius_button));

            }
        });*/


        binding.recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (postItemList.size() < viewModel.totalPostListCount.getValue()) {
                        mPage = mPage + 1;
                        viewModel.getCustomerPostList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        viewModel.customerDetails.observe(getViewLifecycleOwner(), customerDetails -> {
            if (customerDetails != null) {
                binding.toolbar.txtTitle.setText(viewModel.customerDetails.getValue().getName());
                viewModel.follow.setValue(customerDetails.getFollow());
                binding.setVm(viewModel);
                GlideApp.with(getActivity())
                        .load(customerDetails.getProfilePicture())
                        .timeout(30000)
                        .into(binding.imgProile);
            }
        });
        viewModel.custPostListResponse.observe(getViewLifecycleOwner(), customerPostListResponse -> {
            if (customerPostListResponse != null) {
                if (mPage == 0) {
                    postItemList.clear();
                    postItemList = customerPostListResponse.getCustomerPostList();
                    mPostAdapter.setItemList(postItemList);
                } else {
                    for (int i = 0; i < customerPostListResponse.getCustomerPostList().size(); i++) {
                        mPostAdapter.addRow(customerPostListResponse.getCustomerPostList().get(i));
                    }
                    postItemList.addAll(customerPostListResponse.getCustomerPostList());
                }
            }
        });
    }

    private void initVar() {
        postItemList = new ArrayList<>();
        binding.toolbar.setActivity(getBaseActivity());
        binding.toolbar.imgOptions.setVisibility(View.INVISIBLE);
        binding.toolbar.imgOptions.setOnClickListener(v -> showBottomDialog());
        mPostAdapter = new CustomerPostImagesAdapter(this);
        binding.recyclerItem.setAdapter(mPostAdapter);
        if(getArguments()!=null && getArguments().containsKey(Constants.IntentData.CUSTOMER_DATA)) {
            viewModel.customerDetails.setValue((CustomerDetails) getArguments().getSerializable(Constants.IntentData.CUSTOMER_DATA));
            mPage = 0;
            viewModel.getCustomerPostList(mPage);
        }else if(getArguments().containsKey(Constants.IntentData.CUSTOMER_ID)){
            viewModel.getCustomerDetails(getArguments().getInt(Constants.IntentData.CUSTOMER_ID));
        }
    }

    @Override
    public void click(int pos, FeedWallItem item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IntentData.FROM, "user");
        bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, item);
        bundle.putSerializable(Constants.IntentData.CUSTOMER_DATA, viewModel.customerDetails.getValue());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_user_details_to_navigation_tagged_photos, bundle);
    }

    @Override
    public void clickForPagerAdapterItem(Integer showType, int pos, FeedWallItem item) {

    }

    private void showBottomDialog() {
        Dialog dialog = new Dialog(getActivity());
        DialogReportStoryBinding dialogLogoutBinding = DialogReportStoryBinding.inflate(LayoutInflater.from(getContext()));
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
        dialogLogoutBinding.txtBlock.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.txtShare.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.txtReport.setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }

}