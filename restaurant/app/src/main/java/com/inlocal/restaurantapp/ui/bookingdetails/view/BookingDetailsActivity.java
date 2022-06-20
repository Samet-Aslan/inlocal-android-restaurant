package com.inlocal.restaurantapp.ui.bookingdetails.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityBookingDetailsBinding;
import com.inlocal.restaurantapp.databinding.DialogRejectBinding;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderItems;
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestUpdateOrderStatus;
import com.inlocal.restaurantapp.ui.bookingdetails.model.ReuqestItemStatus;
import com.inlocal.restaurantapp.ui.bookingdetails.viewmodel.BookingDetailsViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Inject;

public class BookingDetailsActivity extends BaseActivity<ActivityBookingDetailsBinding> implements BookingDetailsChildAdapter.OnOrderStatusListener, DeliveryBookingItemAdapter.ItemStatusUpdateListener {

    @Inject
    ViewModelFactory viewModelFactory;
    private BookingDetailsViewModel viewModel;
    private DeliveryOrderItem deliveryOrderItem;
    private BookingDetailsChildAdapter bookingDetailsAdapter;
    private DeliveryBookingItemAdapter deliveryItemAdapter;
    private int orderId=0;

    @Override
    protected int layoutRes() {
        return R.layout.activity_booking_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(BookingDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.toolbar.setActivity(this);
        initVar();

        viewModel.isProgressEnabled.observe(this, booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );

        viewModel.odrderDetails.observe(this, responseOrderDetails -> {
            if (responseOrderDetails != null) {
                binding.layoutOrderDetails.setVisibility(View.VISIBLE);
                binding.setData(responseOrderDetails);
                binding.txtBookingId.setVisibility(View.VISIBLE);
                deliveryItemAdapter.setList(responseOrderDetails.getOrderItems(), responseOrderDetails.getOrderType());
                if (responseOrderDetails.getOrderType().equalsIgnoreCase(Constants.OrderType.DINE_IN)) {
                    binding.toolbar.txtTitle.setText(getResources().getString(R.string.table) + " " + ((deliveryOrderItem!=null&&deliveryOrderItem.getTableNo()!=null)?deliveryOrderItem.getTableNo():responseOrderDetails.getTableNo()));
                } else if (responseOrderDetails.getOrderType().equalsIgnoreCase(Constants.OrderType.DELIVERY)) {
                    binding.toolbar.txtTitle.setText(getResources().getString(R.string.delivery));
                } else if (responseOrderDetails.getOrderType().equalsIgnoreCase(Constants.OrderType.STORE_PICKUP)) {
                    binding.toolbar.txtTitle.setText(getResources().getString(R.string.store_pickup));
                }
                if (responseOrderDetails.getOrderType().equalsIgnoreCase(Constants.OrderType.DINE_IN)) {
                    binding.layoutButton.setVisibility(View.GONE);
                } else {
                    binding.txtReject.setVisibility(View.VISIBLE);
                    binding.txtAccpet.setVisibility(View.VISIBLE);
                    switch (responseOrderDetails.getOrderStatus()) {
                        case Constants.OrderStatus.PENDING:
                            binding.layoutButton.setVisibility(View.VISIBLE);
                            binding.txtReject.setVisibility(View.VISIBLE);
                            binding.txtAccpet.setText(responseOrderDetails.getOrderStatus());
                            break;
                        case Constants.OrderStatus.ACCEPTED:
                            binding.layoutButton.setVisibility(View.VISIBLE);
                            binding.txtReject.setVisibility(View.GONE);
                            binding.txtAccpet.setText(responseOrderDetails.getOrderStatus());
                            break;
                        case Constants.OrderStatus.IN_PROGRESS:
                            binding.layoutButton.setVisibility(View.VISIBLE);
                            binding.txtReject.setVisibility(View.GONE);
                            binding.txtAccpet.setText(responseOrderDetails.getOrderStatus());
                            break;
                        case Constants.OrderStatus.DELIVERED:
                            binding.layoutButton.setVisibility(View.GONE);
                            binding.txtAccpet.setText(responseOrderDetails.getOrderStatus());
                            break;
                        case Constants.OrderStatus.FAILED:
                            binding.layoutButton.setVisibility(View.GONE);
                            break;
                        case Constants.OrderStatus.CANCELED:
                            binding.layoutButton.setVisibility(View.GONE);
                            break;
                        case Constants.OrderStatus.REJECTED:
                            binding.layoutButton.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        viewModel.updateOrderStatusResponse.observe(this, response -> {
            if (response != null) {
                showSnackbar(response);
                //viewModel.getOrderDetails(deliveryOrderItem.getId());
                viewModel.getOrderDetails(orderId);
            }
        });


        binding.txtAccpet.setOnClickListener(v -> {

            RequestUpdateOrderStatus requestUpdateOrderStatus = new RequestUpdateOrderStatus();
            requestUpdateOrderStatus.setOrderId(viewModel.odrderDetails.getValue().getId());

            switch (viewModel.odrderDetails.getValue().getOrderStatus()) {
                case Constants.OrderStatus.PENDING:
                    requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.ACCEPTED);
                    break;
                case Constants.OrderStatus.ACCEPTED:
                    requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.IN_PROGRESS);
                    break;
                case Constants.OrderStatus.IN_PROGRESS:
                    requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.DELIVERED);
                    break;
            }
            viewModel.updateOrderStatus(requestUpdateOrderStatus);
        });


        binding.txtReject.setOnClickListener(v -> {
            showRejectionDialog(this, viewModel.odrderDetails.getValue());
        });

    }

    private void initVar() {
        bookingDetailsAdapter = new BookingDetailsChildAdapter(this);
        deliveryItemAdapter = new DeliveryBookingItemAdapter(this);
        binding.recyclerItem.setAdapter(deliveryItemAdapter);
        binding.recyclerItem.setNestedScrollingEnabled(false);
        if (getIntent().hasExtra(Constants.IntentData.DELIVER_DATA)) {
            binding.toolbar.txtTitle.setText(getResources().getString(R.string.delivery));
            deliveryOrderItem = (DeliveryOrderItem) getIntent().getSerializableExtra(Constants.IntentData.DELIVER_DATA);
            if (deliveryOrderItem.getOrderType().equalsIgnoreCase(Constants.OrderType.DINE_IN)) {
                binding.toolbar.txtTitle.setText(getResources().getString(R.string.table) + " " + deliveryOrderItem.getTableNo());
            } else if (deliveryOrderItem.getOrderType().equalsIgnoreCase(Constants.OrderType.DELIVERY)) {
                binding.toolbar.txtTitle.setText(getResources().getString(R.string.delivery));
            } else if (deliveryOrderItem.getOrderType().equalsIgnoreCase(Constants.OrderType.STORE_PICKUP)) {
                binding.toolbar.txtTitle.setText(getResources().getString(R.string.store_pickup));
            }
            orderId= deliveryOrderItem.getId();
            viewModel.getOrderDetails(deliveryOrderItem.getId());
        }

        if (getIntent().hasExtra(Constants.IntentData.ORDER_ID)) {
            binding.toolbar.txtTitle.setText(getResources().getString(R.string.delivery));
            orderId = getIntent().getIntExtra(Constants.IntentData.ORDER_ID, 0);
            if (orderId != 0) {
                viewModel.getOrderDetails(orderId);
            }
        }
    }

    @Override
    public void onOrderRejecte(int pos, OrderItems orderItems, String note) {
        RequestUpdateOrderStatus requestUpdateOrderStatus = new RequestUpdateOrderStatus();
        //requestUpdateOrderStatus.setOrderId(deliveryOrderItem.getId());
        requestUpdateOrderStatus.setOrderId(orderId);
        requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.REJECTED);
        requestUpdateOrderStatus.setMessage(note);
        viewModel.updateOrderStatus(requestUpdateOrderStatus);
    }

    @Override
    public void onOrderAccept(int pos, OrderItems orderItems) {
        RequestUpdateOrderStatus requestUpdateOrderStatus = new RequestUpdateOrderStatus();
        //requestUpdateOrderStatus.setOrderId(deliveryOrderItem.getId());
        requestUpdateOrderStatus.setOrderId(orderId);
        requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.ACCEPTED);
        viewModel.updateOrderStatus(requestUpdateOrderStatus);
    }

    void showRejectionDialog(Context context, DeliveryOrderItem orderItems) {
        Dialog dialog = new Dialog(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        DialogRejectBinding dialogLogoutBinding = DialogRejectBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLogoutBinding.btnYes.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.btnNo.setOnClickListener(v -> {
            dialog.cancel();
            RequestUpdateOrderStatus requestUpdateOrderStatus = new RequestUpdateOrderStatus();
            requestUpdateOrderStatus.setOrderId(orderItems.getId());
            requestUpdateOrderStatus.setOrderStatus(Constants.OrderStatus.REJECTED);
            requestUpdateOrderStatus.setMessage(dialogLogoutBinding.etRejectmsg.getText().toString());
            viewModel.updateOrderStatus(requestUpdateOrderStatus);
        });
        dialog.getWindow().setLayout(((displayMetrics.widthPixels / 100) * 90), RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    @Override
    public void onItemAccept(int pos, OrderItems item) {

        ReuqestItemStatus request = new ReuqestItemStatus();
        //request.setOrderId(viewModel.odrderDetails.getValue().getId());
        orderId=viewModel.odrderDetails.getValue().getId();
        request.setOrderId(orderId);
        request.setMessage("");
        request.setOrderItemId(item.getId());
        switch (item.getStatus()) {
            case Constants.OrderStatus.PENDING:
                request.setStatus(Constants.OrderStatus.DONE);
                break;
        }
        viewModel.updateItemStatus(request);
    }

    @Override
    public void onItemReject(int pos, OrderItems item) {
        ReuqestItemStatus request = new ReuqestItemStatus();
        //request.setOrderId(deliveryOrderItem.getId());
        request.setOrderId(orderId);
        request.setStatus(Constants.OrderStatus.REJECTED);
        request.setOrderItemId(item.getId());
        request.setMessage("");
        viewModel.updateItemStatus(request);
    }
}