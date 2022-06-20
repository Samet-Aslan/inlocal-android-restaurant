package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.view;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.DialogRejectBinding;
import com.inlocal.restaurantapp.databinding.FragmentBookingListBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.RequestResrvationStatusUpdate;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.Reservation;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.viewmodel.BookingListViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookingListFragment extends BaseFragment<FragmentBookingListBinding> implements BookingListAdapter.BookingListCallback {


    @Inject
    ViewModelFactory viewModelFactory;
    private BookingListViewModel viewModel;
    private BookingListAdapter bookingListAdapter;
    private int mPage = 0;
    private List<Reservation> reservationList;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_booking_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(BookingListViewModel.class);
        binding.setLifecycleOwner(this);
        initVar();

        viewModel.errorFromServer.observe(getViewLifecycleOwner(), response -> {
            showSnackbar(response);
        });

        binding.recyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (reservationList.size() < viewModel.totalListRecord.getValue()) {
                        mPage = mPage + 1;
                        viewModel.getReservationList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        viewModel.isProgressEnabled.observe(getViewLifecycleOwner(), booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );

        viewModel.updateBookingStatusResponse.observe(getViewLifecycleOwner(), response -> {
                    if (response != null) {
                        viewModel.getReservationList(mPage);
                    }
                }
        );

        viewModel.reservatListResponse.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (mPage == 0) {
                    reservationList.clear();
                    reservationList = response.getReservationList();
                    bookingListAdapter.setList(reservationList);
                } else {
                    for (int i = 0; i < response.getReservationList().size(); i++) {
                        bookingListAdapter.addRow(response.getReservationList().get(i));
                    }
                    reservationList.addAll(response.getReservationList());
                }
            }
        });

    }

    private void initVar() {
        reservationList = new ArrayList<>();
        binding.toolbar.imgBack.setVisibility(View.GONE);
        binding.toolbar.txtTitle.setText(getResources().getString(R.string.your_booking));
        bookingListAdapter = new BookingListAdapter(this);
        binding.recyclerItem.setAdapter(bookingListAdapter);
    }

    @Override
    public void click(int pos) {

    }

    @Override
    public void onRejectClick(int pos, Reservation item) {
        showRejectionDialog(getContext(), item);
    }

    @Override
    public void onAcceptClick(int pos, Reservation item) {
        RequestResrvationStatusUpdate request = new RequestResrvationStatusUpdate();
        request.setReservationId(item.getId());
        request.setBookingStatus(Constants.BookingStatus.CONFIRM);
        //request.setMessage("");
        viewModel.updateOrderStatus(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 0;
        viewModel.getReservationList(mPage);
    }

    void showRejectionDialog(Context context, Reservation orderItems) {
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
            RequestResrvationStatusUpdate request = new RequestResrvationStatusUpdate();
            request.setReservationId(orderItems.getId());
            request.setBookingStatus(Constants.BookingStatus.DECLINED);
            request.setMessage(dialogLogoutBinding.etRejectmsg.getText().toString());
            viewModel.updateOrderStatus(request);
        });
        dialog.getWindow().setLayout(((displayMetrics.widthPixels / 100) * 90), RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }
}
