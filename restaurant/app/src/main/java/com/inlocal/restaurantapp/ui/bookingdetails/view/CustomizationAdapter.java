package com.inlocal.restaurantapp.ui.bookingdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemBookingsBinding;
import com.inlocal.restaurantapp.databinding.ItemCustomizationBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderSubaddonItem;

import java.util.List;

public class CustomizationAdapter extends RecyclerView.Adapter<CustomizationAdapter.Holder> {
    private List<OrderSubaddonItem> mList;

    public CustomizationAdapter(List<OrderSubaddonItem> mList) {
        this.mList = mList;
    }
    public CustomizationAdapter() {
        //this.mList = mList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCustomizationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemCustomizationBinding binding;

        public Holder(@NonNull ItemCustomizationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind() {
            binding.setData(mList.get(getAdapterPosition()).getSubAddOnName());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setList(List<OrderSubaddonItem> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
}
