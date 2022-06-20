package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Holder> {
    private ImagesCallback mImagesCallback;
    private List<PostItem> itemList;

    public ImagesAdapter(ImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    public ImagesAdapter(ImagesCallback mImagesCallback, String type) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemImageBinding binding;

        public Holder(@NonNull ItemImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(PostItem item) {
            binding.setData(item);
            GlideApp.with(itemView.getContext())
                    .load(item.getImage())
                    .timeout(30000)
                    .error(R.drawable.food)
                    .into(binding.imgFood);
            binding.getRoot().setOnClickListener(v -> {
                mImagesCallback.click(getAdapterPosition(), item);

            });
        }
    }

    public interface ImagesCallback {
        void click(int pos, PostItem item);
    }

    public void setItemList(List<PostItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(PostItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
