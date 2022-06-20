package com.inlocal.restaurantapp.ui.viewstory.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ActivityViewStoryBinding;
import com.inlocal.restaurantapp.databinding.DialogReportStoryBinding;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivity;
import com.inlocal.restaurantapp.ui.uploadstory.view.UploadStoryActivity;
import com.inlocal.restaurantapp.ui.viewstory.model.ViewStoryViewModel;
import com.inlocal.restaurantapp.ui.viewstory.viewmodel.RequestDeleteStory;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;
import com.inlocal.restaurantapp.util.storyviewbar.StoriesProgressView;

import java.util.Objects;

import javax.inject.Inject;


public class ViewStoryActivity extends BaseFragment<ActivityViewStoryBinding> {

    private StoryItem storyItem;
    private static final int PROGRESS_COUNT = 1;
    long pressTime = 0L;
    long limit = 500L;
    @Inject
    ViewModelFactory viewModelFactory;
    private ViewStoryViewModel viewModel;


    @Override
    protected int layoutRes() {
        return R.layout.activity_view_story;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ViewStoryViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();
        binding.imgBack.setOnClickListener(v -> getBaseActivity().onBackPressed());
        binding.imgClose.setOnClickListener(v -> getBaseActivity().onBackPressed());
        binding.imgReport.setVisibility((storyItem != null && SharedPrefUtils.getIntData(getActivity(), Constants.RESTAURANT_ID) == storyItem.getUserStoryBy()) ? View.VISIBLE : View.GONE);
        binding.imgReport.setOnClickListener(v -> showBottomDialog());

        binding.imgResLogo.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentData.MENU_ID, storyItem.getMenuItemId());
            bundle.putInt(Constants.IntentData.RESTAURUNT_ID, storyItem.getRestaurantId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_view_story_to_navigation_restaurant_menu_details, bundle);

            /*Bundle bundle = new Bundle();
            bundle.putInt(Constants.IntentData.RESTAURUNT_ID, storyItem.getRestaurantId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_view_story_to_navigation_restaurant_details, bundle);*/
        });
        binding.imgUser.setOnClickListener(v -> {
            if (storyItem.getStoryUserType().equalsIgnoreCase("Restaurant")) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.IntentData.RESTAURUNT_ID, storyItem.getUserStoryBy());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_view_story_to_navigation_restaurant_details, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentData.CUSTOMER_ID, storyItem.getUserStoryBy());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_view_story_to_navigation_user_details, bundle);
                //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_user_details, bundle);
            }

        });

        viewModel.deleteResponse.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    KeyboardUtil.hideSoftKeyboard(getActivity());
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });


      /*  binding.ivStoryPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        binding.stories.pause();
                        return false;
                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        binding.stories.resume();
                        return limit < now - pressTime;
                }
                return false;

            }
        });*/
        binding.stories.setStoriesListener(new StoriesProgressView.StoriesListener() {
            @Override
            public void onNext() {

            }

            @Override
            public void onPrev() {

            }

            @Override
            public void onComplete() {
                getBaseActivity().onBackPressed();
            }
        });
    }

    private void initVar() {
        //binding.progressbar.setProgress(currentSecond);
        storyItem = (StoryItem) getArguments().getSerializable(Constants.IntentData.DATA);
        binding.setData(storyItem);
        if(storyItem.getMenuImage()!=null) {
            binding.imgResLogo.setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .load(storyItem.getMenuImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .centerCrop()
                    .into(binding.imgResLogo);
        }else{
            binding.imgResLogo.setVisibility(View.INVISIBLE);
        }
       /* if (storyItem.getStoryUserType().equalsIgnoreCase("Restaurant")) {
            binding.imgResLogo.setVisibility(View.INVISIBLE);
        } else {
            binding.imgResLogo.setVisibility(View.INVISIBLE);

            GlideApp.with(getContext())
                    .load("")
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(binding.imgResLogo);
        }
*/
        GlideApp.with(getContext())
                .load(storyItem.getProfileImage())
                .error(R.drawable.profile)
                .timeout(30000)
                .into(binding.imgUser);

        GlideApp.with(getContext())
                .load(storyItem.getStoryImage())
                .timeout(30000)
                .into(binding.ivStoryPhoto);

        binding.stories.setStoriesCount(PROGRESS_COUNT);
        binding.stories.setStoryDuration(10000L);
        binding.stories.startStories(0);


    }

   /* private void loadTimer(int startTime){
        binding.progressbar.setMax(seconds);
        timer = new CountDownTimer(seconds, 50) {
            public void onTick(long millisUntilFinished) {
                currentSecond=currentSecond+50;
                Log.e("timerTick",(millisUntilFinished/1000)+", CurSecon: "+currentSecond);

                //binding.progressbar.setProgress((int)millisUntilFinished/1000);
                binding.progressbar.setProgress(currentSecond);
            }

            public void onFinish() {
                //currentSecond++;
                Log.e("timerTick","finish");
                binding.progressbar.setProgress(0);
                getBaseActivity().onBackPressed();
            }
        }.start();

    }*/

    private void showBottomDialog() {
        binding.stories.pause();
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
        dialogLogoutBinding.txtReport.setText("Delete");
        dialogLogoutBinding.txtShare.setText("Edit");
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> {
            binding.stories.resume();
            dialog.cancel();
        });
        dialogLogoutBinding.txtShare.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.IntentData.STORY_ITEM, storyItem);
            bundle.putString("type", "edit_story");
            NavUtil.ForActivity.navTo(getActivity(), UploadStoryActivity.class, false, bundle);
            dialog.cancel();
        });
        dialogLogoutBinding.txtReport.setOnClickListener(v -> {
            RequestDeleteStory request = new RequestDeleteStory();
            request.setId(storyItem.getStoryId());
            request.setStoryUserType("Restaurant");
            viewModel.callDeleteStory(request);
            dialog.cancel();
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                binding.stories.resume();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        binding.stories.destroy();
        //timer.onFinish();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.stories.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.stories.resume();
    }


}