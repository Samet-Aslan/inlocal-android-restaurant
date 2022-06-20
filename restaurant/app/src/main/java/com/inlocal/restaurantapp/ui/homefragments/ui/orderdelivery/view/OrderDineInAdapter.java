package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.inlocal.restaurantapp.databinding.ItemDineInBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderSubaddonItem;
import com.inlocal.restaurantapp.ui.bookingdetails.view.CustomizationAdapter;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.OrderListDiffCallBack;
import com.inlocal.restaurantapp.util.DateConveter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDineInAdapter extends RecyclerView.Adapter<OrderDineInAdapter.Holder> {
    private OrdersDineInCallback mOrdersCallback;
    private List<DeliveryOrderItem> mData;

    public OrderDineInAdapter(OrdersDineInCallback mOrdersCallback) {
        this.mOrdersCallback = mOrdersCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDineInBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemDineInBinding binding;

        public Holder(@NonNull ItemDineInBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(DeliveryOrderItem data) {
            binding.setData(data);
            binding.txtPrice.setText(DateConveter.convertDateTimeFormat1(data.getDateTime()));
            //DecimalFormat df = new DecimalFormat("#.##");
            //binding.txtPrice.setText("€"+df.format(data.getFinalOrderAmount()));
           // List<OrderSubaddonItem> lst = new ArrayList<>();
            //lst.add(new OrderSubaddonItem(data.getId() + ""));
            //CustomizationAdapter adapter = new CustomizationAdapter(lst);
            //adapter.setHasStableIds(true);
            //binding.recyclerItem.setAdapter(adapter);
            //binding.recyclerItem.setAnimation(null);
            //binding.recyclerItem.setItemAnimator(null);
            //List<String> lst = new ArrayList<>();
            /*List<OrderSubaddonItem> lst = new ArrayList<>();
            lst.add(new OrderSubaddonItem(data.getId() + ""));
            adapter.setList(lst);*/
            binding.getRoot().setOnClickListener(v -> {
                mOrdersCallback.onDineInOrderItemClick(getAdapterPosition(), data);
            });
        }
    }

    public void setList(List<DeliveryOrderItem> list) {
        /*final OrderListDiffCallBack diffCallback = new OrderListDiffCallBack(this.mData, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mData=new ArrayList<>();
//        this.mData.clear();
        this.mData.addAll(list);
        diffResult.dispatchUpdatesTo(this);*/

        this.mData = list;
        notifyDataSetChanged();
    }

    public void addRow(DeliveryOrderItem item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }

    public interface OrdersDineInCallback {
        void onDineInOrderItemClick(int pos, DeliveryOrderItem item);
    }
}
