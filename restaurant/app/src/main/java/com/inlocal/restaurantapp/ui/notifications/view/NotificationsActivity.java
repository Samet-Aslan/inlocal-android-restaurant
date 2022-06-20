package com.inlocal.restaurantapp.ui.notifications.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.ActivityNotificationsBinding;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivity;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.notifications.model.NotificationsModel;
import com.inlocal.restaurantapp.ui.notifications.viewmodel.NotificationViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.SharedPrefUtils;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NotificationsActivity extends BaseFragment<ActivityNotificationsBinding> implements NotificationsAdapter.OnNotificationClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    private NotificationViewModel viewModel;
    private List<NotificationsModel> mData;
    private NotificationsModel selectedNotification;
    private NotificationsAdapter adapter;
    private int mPage = 0, selectedPosition = 0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_notifications;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(NotificationViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), response -> {
            showSnackbar(response);
        });

        viewModel.notificationListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (mPage == 0) {
                    mData.clear();
                    mData = response.getNotificationList();
                    adapter.setList(mData);
                } else {
                    for (int i = 0; i < response.getNotificationList().size(); i++) {
                        adapter.addRow(response.getNotificationList().get(i));
                    }
                    mData.addAll(response.getNotificationList());
                }
            }
        });

        viewModel.recodFound.observe(getViewLifecycleOwner(), response -> {
            binding.recyclerItem.setVisibility(viewModel.recodFound.getValue() ? View.VISIBLE : View.GONE);
            binding.tvNoRecord.setVisibility(viewModel.recodFound.getValue() ? View.GONE : View.VISIBLE);
        });

        viewModel.notifictionReadResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                mData.get(selectedPosition).setReadStatus(1);
                adapter.notifyItemChanged(selectedPosition);
                moveToNextScreen();
            }
        });

        binding.recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollHorizontally(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mData.size() < viewModel.totalListRecord.getValue()) {
                        mPage = mPage + 1;
                        viewModel.getNotificationList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        viewModel.isProgressEnabled.observe(getViewLifecycleOwner(), booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled() != null) {
                        String s = booleanEvent.getContentIfNotHandled() + "";
                        if (s.trim().equalsIgnoreCase("true")) {
                            showLoading();
                        } else {
                            hideLoading();
                        }
                    }
                }
        );

    }

    private void initVar() {
        binding.toolbar.setActivity(getBaseActivity());
        binding.toolbar.txtTitle.setText("Notifications");
        mData = new ArrayList<>();
        adapter = new NotificationsAdapter(this);
        adapter.setList(mData);
        binding.recyclerItem.setAdapter(adapter);
        viewModel.getNotificationList(mPage);
    }

    @Override
    public void OnNotificationClick(NotificationsModel item, int pos) {
        this.selectedNotification = item;
        this.selectedPosition = pos;
        if (item.getReadStatus() == 0) {
            viewModel.readNotification(item.getId());
        } else {
            moveToNextScreen();
        }
    }

    private void moveToNextScreen() {
        Bundle bundle = new Bundle();
        switch (selectedNotification.getModuleType()) {
            case "new_post_created_type":
            case "customer_post_liked_type":
            case "customer_post_commented_type":
            case "user_follower_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.POST_ID, selectedNotification.getRedirectionId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_notification_to_navigation_comment, bundle);
                break;
           /* case "new_story_created_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.ORDER_ID, selectedNotification.getRedirectionId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_comment_to_navigation_menu_details, bundle);
                break;*/
            case "new_order_received_type":
            case "order_status_update_type":
            case "payment_received_type":
            case "new_dinein_item_order_received_type":
            //case "payment_received_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.ORDER_ID, selectedNotification.getRedirectionId());
                NavUtil.ForActivity.navTo(getBaseActivity(), BookingDetailsActivity.class, false, bundle);
                break;
            case "new_booking_received_type":
            case "booking_status_update_type":
                bundle = new Bundle();
                bundle.putInt(Constants.IntentData.ORDER_ID, selectedNotification.getRedirectionId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_notification_to_navigation_bookings, bundle);
                break;
        }
    }
}