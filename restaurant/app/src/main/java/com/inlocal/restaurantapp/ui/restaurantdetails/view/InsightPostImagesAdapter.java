package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemCustomerPostImageBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.ArrayList;
import java.util.List;

public class InsightPostImagesAdapter extends RecyclerView.Adapter<InsightPostImagesAdapter.Holder> {
    private ImagesCallback mImagesCallback;
    private List<FeedWallItem> itemList;

    public InsightPostImagesAdapter(ImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCustomerPostImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCustomerPostImageBinding binding;

        public Holder(@NonNull ItemCustomerPostImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FeedWallItem item) {
            binding.setData(item);
            GlideApp.with(itemView.getContext())
                    .load(item.getPostImage())
                    .timeout(30000)
                    .error(R.drawable.food)
                    .into(binding.imgFood);
            binding.getRoot().setOnClickListener(v -> mImagesCallback.onInsightClick(getAdapterPosition(), item));
        }
    }

    public interface ImagesCallback {
        void onInsightClick(int pos, FeedWallItem item);
    }

    public void setItemList(List<FeedWallItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(FeedWallItem item) {
        if(this.itemList!=null){
            this.itemList.add(item);
            notifyItemInserted(this.itemList.size() - 1);
        }
    }
}
