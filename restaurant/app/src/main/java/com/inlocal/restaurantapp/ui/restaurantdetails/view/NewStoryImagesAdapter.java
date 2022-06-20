package com.inlocal.restaurantapp.ui.restaurantdetails.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.NewStoryItemImageBinding;
import com.inlocal.restaurantapp.databinding.StoryItemImageBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.List;

public class NewStoryImagesAdapter extends RecyclerView.Adapter<NewStoryImagesAdapter.Holder> {
    private NewStoryImagesCallback mImagesCallback;
    private List<FeedWallItem> itemList;

    public NewStoryImagesAdapter(NewStoryImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(NewStoryItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        NewStoryItemImageBinding binding;

        public Holder(@NonNull NewStoryItemImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FeedWallItem item) {
            binding.setData(item);
            //Log.e("imageUrl",item.getPostImage());
            GlideApp.with(itemView.getContext())
                    .load(item.getPostImage()!=null?item.getPostImage():"")
                    .timeout(30000)
                    .into(binding.imgFood);
            binding.getRoot().setOnClickListener(v -> {
                mImagesCallback.onStoryItemClick(getAdapterPosition(), item);

            });
        }
    }

    public interface NewStoryImagesCallback {
        void onStoryItemClick(int pos, FeedWallItem storyItem);
    }

    public void setStoryItemList(List<FeedWallItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(FeedWallItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
