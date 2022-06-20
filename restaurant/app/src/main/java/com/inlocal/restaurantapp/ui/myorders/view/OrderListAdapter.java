package com.inlocal.restaurantapp.ui.myorders.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.databinding.ItemOrderListBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.myorders.model.OrderHistoryItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.DateConveter;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.Holder> {

    private List<OrderHistoryItem> orderHistoryItems;
    private HistoryItemClickListener listener;

    public interface HistoryItemClickListener{
        void  onHistoryItemClick(OrderHistoryItem item, int pos);
    }

    public OrderListAdapter(HistoryItemClickListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(orderHistoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return orderHistoryItems != null ? orderHistoryItems.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemOrderListBinding binding;

        public Holder(@NonNull ItemOrderListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(OrderHistoryItem orderHistoryItem) {
            binding.setData(orderHistoryItem);
            if(orderHistoryItem.getOrderType().equalsIgnoreCase(Constants.OrderType.DELIVERY)){
                binding.ivSymbol.setImageResource(R.drawable.ic_delivery);
            }else{
                binding.ivSymbol.setImageResource(R.drawable.ic_order_table);
            }

            if(orderHistoryItem.getOrderDate()!=null){
                binding.txtTime.setText(DateConveter.convertDateTimeFormat1(orderHistoryItem.getOrderDate()));
            }

            binding.getRoot().setOnClickListener(v->{
                listener.onHistoryItemClick(orderHistoryItem,getAdapterPosition());
            });
        }
    }

    public void setList(List<OrderHistoryItem> list) {
        this.orderHistoryItems = list;
        notifyDataSetChanged();
    }

    public void addRow(OrderHistoryItem item) {
        this.orderHistoryItems.add(item);
        notifyItemInserted(this.orderHistoryItems.size() - 1);
    }
}
