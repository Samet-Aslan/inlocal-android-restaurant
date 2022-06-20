package com.inlocal.restaurantapp.ui.comment.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.ActivityCommentBinding;
import com.inlocal.restaurantapp.databinding.DialogReportStoryBinding;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivity;
import com.inlocal.restaurantapp.ui.comment.model.CommentData;
import com.inlocal.restaurantapp.ui.comment.model.CommentRequestBody;
import com.inlocal.restaurantapp.ui.comment.model.CommentsItem;
import com.inlocal.restaurantapp.ui.comment.model.RequestReport;
import com.inlocal.restaurantapp.ui.comment.viewmodel.CommentViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class CommentActivity extends BaseFragment<ActivityCommentBinding> implements CommentAdapter.OnCommentItemListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private CommentViewModel viewModel;
    private FeedWallItem feedWallItem;
    private int mPage = 0;
    private CommentData data;
    private List<CommentsItem> commentsItemList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private boolean fromComment = false, isMyProfile = false, showOwnerMenu=false;
    private LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected int layoutRes() {
        return R.layout.activity_comment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(CommentViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        binding.toolbar.setActivity(getBaseActivity());
        init();

        binding.rvNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) binding.rvNestedScrollView.getChildAt(binding.rvNestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (binding.rvNestedScrollView.getHeight() + binding.rvNestedScrollView
                        .getScrollY()));

                if (diff == 0) {
                   /* if (commentsItemList.size() < viewModel.totalFeedWalListCoun.getValue()) {
                        mPage = mPage + 1;
                        viewModel.currentPage.setValue(mPage);
                        viewModel.getCommentList(mPage, feedWallItem.getPostId());
                    }*/
                    Log.e("run", "run");
                    // your pagination code
                }
            }
        });


        binding.toolbar.imgOptions.setOnClickListener(v -> {
            showBottomDialog();
        });

        viewModel.menuDtailsResponse.observe(getViewLifecycleOwner(), reseponItemDetails -> {
            if (reseponItemDetails != null) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentData.RESTAURUNT_ID, feedWallItem.getRestaurantId());
                bundle.putSerializable(Constants.IntentData.DATA, reseponItemDetails);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_menu_details, bundle);
            }
        });

        viewModel.reportResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.Reported), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.deleteResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.post_deleted), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        binding.imgUser.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_restaurant_details);
        });
        binding.txtName.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_restaurant_details, bundle);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_restaurant_details, bundle);
        });
        binding.flSpoon.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentData.RESTAURUNT_ID, feedWallItem.getRestaurantId());
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_menu_details, bundle);


            /*if (SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID) == feedWallItem.getRestaurantId()) {
                viewModel.callMenuDetails(feedWallItem.getMenuItemId());
            } else {
                viewModel.callOtherRestMenu(feedWallItem.getRestaurantId(), feedWallItem.getMenuItemId());
            }*/

        });
        binding.imgLogo1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_restaurant_details, bundle);

        });
        binding.imgLogo.setOnClickListener(v -> {
            if (feedWallItem.getPostUserType().equalsIgnoreCase("Restaurant")) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_restaurant_details, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentData.CUSTOMER_ID, feedWallItem.getUserPostBy());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_user_details, bundle);
                //viewModel.getCustomerDetails(feedWallItem.getUserPostBy());
            }

        });
        binding.imgTag.setOnClickListener(v -> {
            if (binding.imgTag.getDrawable().getConstantState() == v.getContext().getResources().getDrawable(R.drawable.ic_tag).getConstantState()) {
                binding.imgTag.setImageResource(R.drawable.ic_tag_blue);
                Toast.makeText(v.getContext(), "Post saves to favorites.", Toast.LENGTH_SHORT).show();
            } else {
                binding.imgTag.setImageResource(R.drawable.ic_tag);
            }

            feedWallItem.setFavorite(!feedWallItem.getFavorite());
            viewModel.makeFavourites(feedWallItem);
        });

        binding.imgLike.setOnClickListener(v -> {
            if (Objects.equals(binding.imgLike.getDrawable().getConstantState(), v.getContext().getResources().getDrawable(R.drawable.ic_like).getConstantState())) {
                binding.imgLike.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_unlike));
                feedWallItem.setLiked(false);
            } else {
                binding.imgLike.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_like));
                feedWallItem.setLiked(true);
            }
            feedWallItem.setLikeCounter(feedWallItem.getLiked() ? feedWallItem.getLikeCounter() + 1 : feedWallItem.getLikeCounter() - 1);

            viewModel.likeCount.setValue(feedWallItem.getLikeCounter().toString());

            viewModel.makeLike(feedWallItem);
        });

       /* viewModel.isProgressEnabled.observe(requireActivity(), booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );*/

        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(viewModel.message.getValue()).equals("")) {
                    KeyboardUtil.hideSoftKeyboard(requireActivity());
                    CommentRequestBody commentRequestBody = new CommentRequestBody();
                    commentRequestBody.setCommentUserType("Restaurant");
                    commentRequestBody.setPostId(feedWallItem.getPostId());
                    commentRequestBody.setMessage(viewModel.message.getValue());
                    viewModel.createComment(commentRequestBody, feedWallItem);
                    viewModel.message.setValue("");
                }
            }
        });

        viewModel.response.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    fromComment = true;
                }
            }
        });

        binding.flImage.setOnClickListener(v -> {
            if (binding.llIcons.getVisibility() == View.INVISIBLE) {
                binding.llIcons.startAnimation(inFromRightAnimation());
                binding.llIcons.setVisibility(View.VISIBLE);
                binding.txtDesc.setVisibility(View.GONE);
                binding.txtDescMore.setVisibility(View.VISIBLE);
            } else {
                binding.llIcons.startAnimation(outToRightAnimation());
                binding.llIcons.setVisibility(View.INVISIBLE);
                binding.txtDesc.setVisibility(View.VISIBLE);
                binding.txtDescMore.setVisibility(View.GONE);
            }

        });

        viewModel.responseCustomerDetails.observe(getViewLifecycleOwner(), new Observer<ResponseCustomerDetails>() {
            @Override
            public void onChanged(ResponseCustomerDetails responseCustomerDetails) {
                if (responseCustomerDetails != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.IntentData.CUSTOMER_DATA, responseCustomerDetails.getCustomerDetails());
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_user_details, bundle);
                }
            }
        });

        viewModel.postResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                feedWallItem = response.getPostDetails();
                loadData(true);
            }
        });
    }

    private void init() {
        binding.toolbar.imgOptions.setVisibility(View.VISIBLE);
        binding.toolbar.txtTitle.setText(getResources().getString(R.string.comment));
        commentAdapter = new CommentAdapter(this);
        linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclerComments.setLayoutManager(linearLayoutManager);
        binding.recyclerComments.setAdapter(commentAdapter);
        if (getArguments().containsKey("feed")) {
            feedWallItem = (FeedWallItem) requireArguments().getSerializable("feed");
            loadData(false);
        } else if (getArguments().containsKey(Constants.IntentData.POST_ID)) {
            viewModel.getPostDetails(getArguments().getInt(Constants.IntentData.POST_ID));
        }

        if (getArguments().containsKey("isMyProfile")) {
            isMyProfile = getArguments().getBoolean("isMyProfile");
        }

        if (getArguments().containsKey("showOwnerMenu")) {
            showOwnerMenu = getArguments().getBoolean("showOwnerMenu");
        }

        viewModel.commentDataReponse.observe(requireActivity(), data -> {
            if (data != null) {
                this.data = data;
                if (mPage == 0) {
                    commentsItemList.clear();
                    commentsItemList = data.getCommentList().getComments();
                    commentAdapter.setList(commentsItemList);
                } else {
                    for (int i = 0; i < data.getCommentList().getComments().size(); i++) {
                        commentAdapter.addRow(data.getCommentList().getComments().get(i));
                    }
                    commentsItemList.addAll(data.getCommentList().getComments());
                }
                binding.recyclerComments.setVisibility(View.VISIBLE);

                if (fromComment) {
                    fromComment = false;
                    binding.rvNestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }

            }

        });


      /*  binding.recyclerComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                *//*if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (viewModel.currentPage.getValue() < viewModel.totalFeedWalListCoun.getValue()) {

                    }
                }*//*
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                            mPage = mPage + 1;
                            viewModel.getCommentList(mPage, feedWallItem.getPostId());

                            loading = true;
                        }
                    }
                }
            }
        });*/
//        viewModel.errorFromServer.observe(getViewLifecycleOwner(), this::showSnackbar);
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +0.3f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(250);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (feedWallItem != null) {
            mPage = 0;
            viewModel.getCommentList(mPage, feedWallItem.getPostId());
        }
    }*/

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
    public void onCommentUserClick(int pos, CommentsItem item) {
        if (item.getCommentUserType().equalsIgnoreCase("Restaurant")) {
            Bundle bundle = new Bundle();
            if (item.getUserCommentBy() == SharedPrefUtils.getIntData(getContext(), Constants.RESTAURANT_ID)) {
                bundle.putBoolean("isMyProfile", true);
                bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
            } else {
                bundle.putInt(Constants.IntentData.RESTAURUNT_ID, item.getUserCommentBy());
            }
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_restaurant_details, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentData.CUSTOMER_ID, item.getUserCommentBy());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_user_details, bundle);
            //viewModel.getCustomerDetails(item.getUserCommentBy());
        }
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
        if (showOwnerMenu) {
            dialogLogoutBinding.txtBlock.setVisibility(View.VISIBLE);
            dialogLogoutBinding.viewBlock.setVisibility(View.VISIBLE);
            dialogLogoutBinding.txtShare.setVisibility(View.VISIBLE);
            dialogLogoutBinding.viewShare.setVisibility(View.VISIBLE);
            dialogLogoutBinding.txtReport.setVisibility(View.GONE);
            dialogLogoutBinding.viewReport.setVisibility(View.GONE);
        } else {
            dialogLogoutBinding.txtBlock.setVisibility(View.GONE);
            dialogLogoutBinding.viewBlock.setVisibility(View.GONE);
            dialogLogoutBinding.txtShare.setVisibility(View.GONE);
            dialogLogoutBinding.viewShare.setVisibility(View.GONE);
            dialogLogoutBinding.txtReport.setVisibility(View.VISIBLE);
            dialogLogoutBinding.viewReport.setVisibility(View.VISIBLE);
        }
        dialogLogoutBinding.txtBlock.setText(getResources().getString(R.string.edit));
        dialogLogoutBinding.txtShare.setText(getResources().getString(R.string.delete));
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialogLogoutBinding.txtBlock.setOnClickListener(v -> {
            dialog.cancel();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.FEED_WALL_DATA, feedWallItem);
            bundle.putString("type", "edit_post");
            NavUtil.ForActivity.navTo(getActivity(), AddPostActivity.class, false, bundle);
        });
        dialogLogoutBinding.txtShare.setOnClickListener(v -> {
            dialog.cancel();
            viewModel.deletePost(feedWallItem.getPostId(),feedWallItem.getPostUserType());
        });
        dialogLogoutBinding.txtReport.setOnClickListener(v -> {
            dialog.cancel();
            viewModel.callReport(new RequestReport("Restaurant", feedWallItem.getPostId(), "Spam"));
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void loadData(boolean fromPostDetails) {
        binding.setData(feedWallItem);
        GlideApp.with(getContext())
                .load(feedWallItem.getPostImage())
                .timeout(30000)
                .into(binding.imgPost);

        GlideApp.with(getContext())
                .load(feedWallItem.getProfileImage())
                .timeout(30000)
                .error(R.drawable.profile)
                .into(binding.imgLogo);

        viewModel.likeCount.setValue(feedWallItem.getLikeCounter() + "");

        GlideApp.with(getContext())
                .load(feedWallItem.getRestauranImg())
                .timeout(30000)
                .error(R.drawable.profile)
                .into(binding.imgLogo1);

        GlideApp.with(getContext())
                .load(SharedPrefUtils.getStringData(getContext(), Constants.SharePref.PROFILE_PIC))
                .timeout(30000)
                .error(R.drawable.profile)
                .into(binding.imgUser);
        binding.flSpoon.setVisibility(feedWallItem.getMenuItemId()!=null?View.VISIBLE:View.GONE);
        //if (fromPostDetails) {
        mPage = 0;
        viewModel.getCommentList(mPage, feedWallItem.getPostId());
        //}
    }

}