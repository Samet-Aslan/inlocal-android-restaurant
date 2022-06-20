package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.databinding.FragmentOrderDeliveryBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.viewmodel.OrderDeliveryViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class OrderDeliveryFragment extends BaseFragment<FragmentOrderDeliveryBinding> implements OrderDeliveryAdapter.OrdersCallback, OrderDineInAdapter.OrdersDineInCallback {
    @Inject
    ViewModelFactory viewModelFactory;
    private OrderDeliveryViewModel vm;
    private ArrayList<DeliveryModel> mData;
    private OrderDeliveryAdapter orderDeliveryAdapter;
    private OrderDineInAdapter orderDineAdapter;
    private List<DeliveryOrderItem> deliveryOrderItemsList;
    private List<DeliveryOrderItem> dineInOrderList;
    private int mPage = 0, mPageDineIn=0;
    private Handler handler, handler1;
    private Runnable runnable,runnable1;
    int delay = 1000;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_order_delivery;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this, viewModelFactory).get(OrderDeliveryViewModel.class);
        binding.setVm(vm);
        binding.setLifecycleOwner(this);
        init();

        vm.resposeDeliverOrderList.observe(getViewLifecycleOwner(), responseDeliveryOrder -> {
            if (responseDeliveryOrder != null) {
                if (mPage == 0) {
                    deliveryOrderItemsList.clear();
                    deliveryOrderItemsList = responseDeliveryOrder.getOrderList();
                    orderDeliveryAdapter.setList(deliveryOrderItemsList);
                } else {
                    for (int i = 0; i < responseDeliveryOrder.getOrderList().size(); i++) {
                        orderDeliveryAdapter.addRow(responseDeliveryOrder.getOrderList().get(i));
                    }
                    deliveryOrderItemsList.addAll(responseDeliveryOrder.getOrderList());
                }
                if(responseDeliveryOrder.getReservationOrderCount()>0) {
                    binding.txtCount.setVisibility(View.VISIBLE);
                    binding.txtCount.setText(responseDeliveryOrder.getReservationOrderCount() + "");
                }else{
                    binding.txtCount.setVisibility(View.GONE);
                }
                getDeliveryOrderListAutoUpdate();
            }
        });

        vm.resposeDineInOrderList.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (mPageDineIn == 0) {
                    dineInOrderList.clear();
                    dineInOrderList = response.getOrderList();
                    orderDineAdapter.setList(dineInOrderList);
                } else {
                    for (int i = 0; i < response.getOrderList().size(); i++) {
                        orderDineAdapter.addRow(response.getOrderList().get(i));
                    }
                    dineInOrderList.addAll(response.getOrderList());
                }
                if(response.getDeliveryOrderCount()>0) {
                    binding.txtCount.setVisibility(View.VISIBLE);
                    binding.txtCount.setText(response.getDeliveryOrderCount() + "");
                }else{
                    binding.txtCount.setVisibility(View.GONE);
                }

                getDineInOrderListAutoUpdate();
            }
        });

        vm.errorFromServer.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                showSnackbar(response);
            }
        });

       /* binding.recyclerDelivery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (deliveryOrderItemsList.size() < vm.totalDeliverOrderListCount.getValue()) {
                        mPage = mPage + 1;
                        vm.getDeliverOrderList(mPage);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.recyclerDineIn.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(dineInOrderList!=null && vm.totalDineInOrderListCount!=null && vm.totalDineInOrderListCount.getValue()!=null) {
                        if (dineInOrderList.size() < vm.totalDineInOrderListCount.getValue()) {
                            mPageDineIn = mPageDineIn + 1;
                            vm.getDineInOrdeList(mPageDineIn);
                        }
                    }else{
                        mPageDineIn = 0;
                        vm.getDineInOrdeList(mPageDineIn);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/

        vm.isProgressEnabled.observe(getViewLifecycleOwner(), booleanEvent -> {
                    if (booleanEvent.getContentIfNotHandled()) {
                        showLoading();
                    } else {
                        hideLoading();
                    }
                }
        );
    }

    private void init() {
        binding.toolbar.imgBack.setVisibility(View.GONE);
        binding.toolbar.txtTitle.setText("Order");
        deliveryOrderItemsList = new ArrayList<>();
        dineInOrderList = new ArrayList<>();
        mData = new ArrayList<>();
        orderDeliveryAdapter = new OrderDeliveryAdapter(this);
        orderDineAdapter = new OrderDineInAdapter(this);
        orderDeliveryAdapter.setHasStableIds(true);
        orderDineAdapter.setHasStableIds(true);
        binding.recyclerDelivery.setAdapter(orderDeliveryAdapter);
        binding.recyclerDineIn.setAdapter(orderDineAdapter);
        binding.recyclerDineIn.setAnimation(null);
        binding.recyclerDelivery.setAnimation(null);
        binding.recyclerDineIn.setItemAnimator(null);
        binding.recyclerDelivery.setItemAnimator(null);



        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        binding.tvTimerFirst.setText(formatter.format(today).substring(0,1)+":");
        binding.tvTimerSecond.setText(formatter.format(today).substring(3,4)+":");
        binding.tvTimerThird.setText(formatter.format(today).substring(6,7));
        blink();

        vm.listType.observe(getViewLifecycleOwner(), s -> {
           loadList(s);
        });
    }

    @Override
    public void onDeliveryOrderItemClick(int pos, DeliveryOrderItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DELIVER_DATA, item);
        NavUtil.ForActivity.navTo(getBaseActivity(), BookingDetailsActivity.class, false, bundle);
    }

    private void blink() {
        final Handler hander = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(550);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        /*if(binding.tvTimer.getVisibility() == View.VISIBLE) {
                            binding.tvTimer.setVisibility(View.INVISIBLE);
                        } else {*/
                        Date today = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                        binding.tvTimerFirst.setText(formatter.format(today).substring(0,2)+":");
                        binding.tvTimerSecond.setText(formatter.format(today).substring(3,5)+":");
                        binding.tvTimerThird.setText(formatter.format(today).substring(6,8));
                        //binding.tvTimer.setVisibility(View.VISIBLE);
                        //}
                        blink();
                    }
                });
            }
        }).start();
    }


    @Override
    public void onDineInOrderItemClick(int pos, DeliveryOrderItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.IntentData.DELIVER_DATA, item);
        NavUtil.ForActivity.navTo(getBaseActivity(), BookingDetailsActivity.class, false, bundle);
    }

    private void getDineInOrderListAutoUpdate(){
        if(handler==null) {
            handler  = new Handler();
            handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, delay);
                    mPageDineIn=0;
                    vm.getDineInOrdeListNoLoader(mPageDineIn);
                }
            }, delay);
        }

    }

    private void getDeliveryOrderListAutoUpdate(){
        if(handler1==null) {
            handler1  = new Handler();
            handler1.postDelayed(runnable1 = new Runnable() {
                public void run() {
                    handler1.postDelayed(runnable1, delay);
                    mPage = 0;
                    vm.getDeliverOrderListNoLoader(mPage);
                }
            }, delay);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        callDeliveryInPause();
        callDinInPause();
    }

    private  void callDinInPause(){
        if(handler!=null) {
            handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
            handler=null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(vm.listType.getValue()!=null) {
            loadList(vm.listType.getValue());
        }
    }

    private  void callDeliveryInPause(){
        if(handler1!=null) {
            handler1.removeCallbacks(runnable1); //stop handler when activity not visible super.onPause();
            handler1=null;
        }
    }

    private void loadList(String s){
        if (s.equals("dine_in")) {
            mPageDineIn=0;
            vm.getDineInOrdeList(mPageDineIn);
            binding.recyclerDelivery.setVisibility(View.GONE);
            binding.recyclerDineIn.setVisibility(View.VISIBLE);
            //binding.txtCount.setVisibility(View.GONE);
            callDeliveryInPause();
        } else {
            mPage = 0;
            vm.getDeliverOrderList(mPage);
            binding.recyclerDineIn.setVisibility(View.GONE);
            binding.recyclerDelivery.setVisibility(View.VISIBLE);
            callDinInPause();
        }
    }
}