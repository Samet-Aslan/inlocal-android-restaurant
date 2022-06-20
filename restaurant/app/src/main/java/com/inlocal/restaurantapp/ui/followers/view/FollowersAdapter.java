package com.inlocal.restaurantapp.ui.followers.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemFollowersBinding;
import com.inlocal.restaurantapp.ui.followers.model.FollowersItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.SharedPrefUtils;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.Holder> {

    public FollowersAdapter(OnFollowersClickListener listener) {
        this.listener = listener;
    }

    private List<FollowersItem> followersItems;
    private OnFollowersClickListener listener;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemFollowersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(followersItems.get(position));
    }

    @Override
    public int getItemCount() {
        return followersItems != null ? followersItems.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemFollowersBinding binding;

        public Holder(@NonNull ItemFollowersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FollowersItem followersItem) {
            binding.setData(followersItem);

            /*if (followersItem.getOtherUserType() != null && followersItem.getOtherUserId() != null &&
                    followersItem.getOtherUserType().equalsIgnoreCase("restaurant") &&
                    SharedPrefUtils.getIntData(itemView.getContext(), Constants.RESTAURANT_ID) == followersItem.getOtherUserId()) {
                binding.imgClose.setVisibility(View.VISIBLE);
            } else {
                binding.imgClose.setVisibility(View.INVISIBLE);
            }*/

            if (SharedPrefUtils.getIntData(itemView.getContext(), Constants.RESTAURANT_ID) == followersItem.getId()) {
                binding.imgClose.setVisibility(View.INVISIBLE);
            } else {
                binding.imgClose.setVisibility(View.VISIBLE);
            }

            binding.imgClose.setOnClickListener(v -> {
                listener.onUnFollowingClick(getAdapterPosition(), followersItem);
            });
        }
    }

    public interface OnFollowersClickListener {
        void onUnFollowingClick(int pos, FollowersItem followersItem);
        void onItemClick(int pos, FollowersItem followersItem);
    }

    public void setList(List<FollowersItem> list) {
        this.followersItems = list;
        notifyDataSetChanged();
    }

    public void addRow(FollowersItem item) {
        this.followersItems.add(item);
        notifyItemInserted(this.followersItems.size() - 1);
    }
}
