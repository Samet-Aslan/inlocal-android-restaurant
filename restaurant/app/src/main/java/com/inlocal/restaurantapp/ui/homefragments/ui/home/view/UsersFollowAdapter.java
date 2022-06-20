package com.inlocal.restaurantapp.ui.homefragments.ui.home.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.ItemUserFollowBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.UsersFollowModel;

import java.util.ArrayList;
import java.util.List;

public class UsersFollowAdapter extends RecyclerView.Adapter<UsersFollowAdapter.Holder> {
    public List<StoryItem> mData;
    private UserCallback mUserCallback;

    public UsersFollowAdapter(UserCallback mUserCallback) {
        this.mUserCallback = mUserCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemUserFollowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ItemUserFollowBinding binding;

        public Holder(@NonNull ItemUserFollowBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(StoryItem item) {
            binding.setData(item);
            GlideApp.with(itemView.getContext())
                    .load(item.getStoryImage())
                    .timeout(30000)
                    .into(binding.ivCoverImage);
            binding.getRoot().setOnClickListener(v -> mUserCallback.onStoryClick(getAdapterPosition(), item));
        }
    }

    public interface UserCallback {
        void onStoryClick(int pos, StoryItem item);
    }

    public void setList(List<StoryItem> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void addRow(StoryItem item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }
}
