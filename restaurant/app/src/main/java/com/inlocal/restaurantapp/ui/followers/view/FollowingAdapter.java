package com.inlocal.restaurantapp.ui.followers.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemFollowersBinding;
import com.inlocal.restaurantapp.databinding.ItemFollowingBinding;
import com.inlocal.restaurantapp.ui.followers.model.FollowersItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.Holder> {
    private List<FollowersItem> list;
    private OnFollowingClickListener listener;

    public FollowingAdapter(OnFollowingClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemFollowingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemFollowingBinding binding;

        public Holder(@NonNull ItemFollowingBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FollowersItem followersItem) {
            binding.setData(followersItem);

            if (followersItem.getOtherUserType() != null && followersItem.getOtherUserId() != null &&
                    followersItem.getOtherUserType().equalsIgnoreCase("restaurant") &&
                    SharedPrefUtils.getIntData(itemView.getContext(), Constants.RESTAURANT_ID) == followersItem.getOtherUserId()) {
                binding.imgClose.setVisibility(View.VISIBLE);
            } else {
                binding.imgClose.setVisibility(View.INVISIBLE);
            }

            GlideApp.with(itemView.getContext())
                    .load(followersItem.getProfileImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(binding.imgProfile);

            binding.imgClose.setOnClickListener(v -> {
                listener.onActionClick(getAdapterPosition(), followersItem);
            });
        }
    }

    public void setList(List<FollowersItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addRow(FollowersItem item) {
        this.list.add(item);
        notifyItemInserted(this.list.size() - 1);
    }

    public interface OnFollowingClickListener {
        void onActionItemClick(int pos, FollowersItem followersItem);
        void onActionClick(int pos, FollowersItem followersItem);
    }
}
