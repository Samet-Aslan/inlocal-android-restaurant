package com.inlocal.restaurantapp.ui.bookingdetails.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.DialogRejectBinding;
import com.inlocal.restaurantapp.databinding.ItemBookingDetailsChildBinding;
import com.inlocal.restaurantapp.databinding.ItemDeliveryOrderBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderItems;
import com.inlocal.restaurantapp.util.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class DeliveryBookingItemAdapter extends RecyclerView.Adapter<DeliveryBookingItemAdapter.Holder> {
    private List<OrderItems> orderItemsList;
    private String orderType = Constants.OrderType.DELIVERY;
    private ItemStatusUpdateListener listener;

    interface ItemStatusUpdateListener {
        void onItemAccept(int pos, OrderItems item);

        void onItemReject(int pos, OrderItems item);
    }

    public DeliveryBookingItemAdapter(ItemStatusUpdateListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDeliveryOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(orderItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItemsList != null ? orderItemsList.size() : 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemDeliveryOrderBinding binding;

        public Holder(@NonNull ItemDeliveryOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(OrderItems orderItems) {
            DecimalFormat df = new DecimalFormat("#.##");
            Double subAdOnTotal = 0.0;
            if (orderItems.getOrderSubaddonItems() != null && orderItems.getOrderSubaddonItems().size() > 0) {
                binding.llTips.setVisibility(View.VISIBLE);
                binding.recyclerItem.setAdapter(new CustomizationAdapter(orderItems.getOrderSubaddonItems()));
                for (int i = 0; i < orderItems.getOrderSubaddonItems().size(); i++) {
                    subAdOnTotal = subAdOnTotal + orderItems.getOrderSubaddonItems().get(i).getPrice();
                }
            } else {
                binding.llTips.setVisibility(View.GONE);
            }
            //binding.txtPrice.setText("€ " + df.format((orderItems.getPrice() * orderItems.getQty()) + subAdOnTotal));
            orderItems.setDisplayCalPrice(((orderItems.getPrice() * orderItems.getQty()) + subAdOnTotal));
            binding.setData(orderItems);

            GlideApp.with(itemView.getContext())
                    .load(orderItems.getImage())
                    .timeout(30000)
                    .into(binding.ivProduct);


            if (orderType.equalsIgnoreCase(Constants.OrderType.DINE_IN)) {
                if (orderItems.getStatus() != null) {
                    binding.layoutButton.setVisibility(View.VISIBLE);
                    //binding.txtReject.setVisibility(View.VISIBLE);
                    binding.txtAccpet.setVisibility(View.VISIBLE);
                    switch (orderItems.getStatus()) {
                        case Constants.OrderStatus.PENDING:
                            binding.layoutButton.setVisibility(View.VISIBLE);
                            // binding.txtReject.setVisibility(View.VISIBLE);
                            binding.txtAccpet.setText("DONE");
                            break;
                        case Constants.OrderStatus.DONE:
                            binding.layoutButton.setVisibility(View.GONE);
                            binding.tvStatus.setVisibility(View.VISIBLE);
                            binding.txtAccpet.setText(orderItems.getStatus());
                            binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot, null), null, null, null);
                            binding.tvStatus.setText(orderItems.getStatus());
                            break;
                        case Constants.OrderStatus.FAILED:
                            binding.layoutButton.setVisibility(View.GONE);
                            binding.tvStatus.setVisibility(View.GONE);
                            break;
                        case Constants.OrderStatus.CANCELED:
                            binding.layoutButton.setVisibility(View.GONE);
                            binding.tvStatus.setVisibility(View.GONE);
                            break;
                        case Constants.OrderStatus.REJECTED:
                            binding.layoutButton.setVisibility(View.GONE);
                            binding.tvStatus.setVisibility(View.VISIBLE);
                            binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_red_dot, null), null, null, null);
                            binding.tvStatus.setText(orderItems.getStatus());
                            break;
                    }
                }
            } else {
                binding.layoutButton.setVisibility(View.GONE);
            }


            binding.layoutAccept.setOnClickListener(v -> {
                if (orderItems.getStatus() != null) {
                    listener.onItemAccept(getAdapterPosition(), orderItems);
                }
            });

            binding.layoutReject.setOnClickListener(v -> {
                if (orderItems.getStatus() != null) {
                    listener.onItemReject(getAdapterPosition(), orderItems);
                }
            });
        }
    }

    public void setList(List<OrderItems> list, String orderType) {
        this.orderItemsList = list;
        this.orderType = orderType;
        notifyDataSetChanged();
    }

    public void addRow(OrderItems item) {
        this.orderItemsList.add(item);
        notifyItemInserted(this.orderItemsList.size() - 1);
    }
}
