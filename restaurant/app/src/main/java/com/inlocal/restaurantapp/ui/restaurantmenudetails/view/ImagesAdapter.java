package com.inlocal.restaurantapp.ui.restaurantmenudetails.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Holder> {
    private ImagesCallback mImagesCallback;
    private Context mContext;
    private List<FeedWallItem> itemList;

    public ImagesAdapter(ImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
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

        void bind(FeedWallItem feedWallItem) {

            GlideApp.with(mContext)
                    .load(feedWallItem.getPostImage())
                    .timeout(30000)
                    .error(R.drawable.food)
                    .into(binding.imgFood);

            if(feedWallItem.getFavorite()!=null){
                binding.imgTag.setVisibility(feedWallItem.getFavorite() ? View.VISIBLE : View.GONE);
            }else{
                binding.imgTag.setVisibility(View.GONE);
            }


            binding.getRoot().setOnClickListener(v -> mImagesCallback.click(getAdapterPosition(),feedWallItem));

        }
    }

    public interface ImagesCallback {
        void click(int pos,FeedWallItem item);
    }

    public void setList(List<FeedWallItem> feedWallItemList) {
        this.itemList = feedWallItemList;
        notifyDataSetChanged();
    }

    public void addRow(FeedWallItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
