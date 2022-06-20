package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.databinding.ItemDeliveryListBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.DateConveter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryAdapter extends RecyclerView.Adapter<OrderDeliveryAdapter.Holder> {

    private List<DeliveryOrderItem> mData;
    private OrdersCallback mOrdersCallback;

    public OrderDeliveryAdapter(OrdersCallback mOrdersCallback) {
        this.mOrdersCallback = mOrdersCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDeliveryListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemDeliveryListBinding binding;

        public Holder(@NonNull ItemDeliveryListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(DeliveryOrderItem data) {
            binding.setData(data);
            binding.txtTime.setText(DateConveter.convertDateTimeFormat1(data.getDateTime()));
            DecimalFormat df = new DecimalFormat("#.##");
            //binding.txtPrice.setText("€"+df.format(data.getFinalOrderAmount()));
            //binding.tvTip.setText("€"+df.format(data.getPercentageTipValue()));

            switch (data.getOrderStatus()) {
                case Constants.OrderStatus.PENDING:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_grey_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.ACCEPTED:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.IN_PROGRESS:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.DELIVERED:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.FAILED:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_red_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.CANCELED:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_red_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
                case Constants.OrderStatus.REJECTED:
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_red_dot,null), null, null, null );
                    binding.txtStatus.setText(data.getOrderStatus());
                    break;
            }


            binding.getRoot().setOnClickListener(v -> {
                mOrdersCallback.onDeliveryOrderItemClick(getAdapterPosition(), data);
            });
        }
    }

    public interface OrdersCallback {
        void onDeliveryOrderItemClick(int pos, DeliveryOrderItem item);
    }

    public void setList(List<DeliveryOrderItem> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void addRow(DeliveryOrderItem item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }
}
