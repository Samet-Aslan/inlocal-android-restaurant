package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.databinding.ItemImageLayoutBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.List;

public class NewImagesAdapter extends RecyclerView.Adapter<NewImagesAdapter.Holder> {
    private ImagesCallback mImagesCallback;
    private List<FeedWallItem> itemList;

    public NewImagesAdapter(ImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    public NewImagesAdapter(ImagesCallback mImagesCallback, String type) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemImageLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemImageLayoutBinding binding;

        public Holder(@NonNull ItemImageLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FeedWallItem item) {
            binding.setData(item);
            GlideApp.with(itemView.getContext())
                    .load(item.getPostImage()!=null?item.getPostImage():"")
                    .timeout(30000)
                    .into(binding.imgFood);
            binding.getRoot().setOnClickListener(v -> {
                mImagesCallback.click(getAdapterPosition(), item);

            });
        }
    }

    public interface ImagesCallback {
        void click(int pos, FeedWallItem item);
    }

    public void setItemList(List<FeedWallItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(FeedWallItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
