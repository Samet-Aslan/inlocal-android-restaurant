package com.inlocal.restaurantapp.ui.homefragments.ui.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemFeedBinding;
import com.inlocal.restaurantapp.ui.followers.model.FollowersItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.List;
import java.util.Objects;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Holder> {
    private FeedCallBack mFeedCallBack;
    private String type = "";
    private List<FeedWallItem> itemList;
    private boolean isMyProfile=false;

    public FeedAdapter(FeedCallBack mFeedCallBack, String type) {
        this.mFeedCallBack = mFeedCallBack;
        this.type = type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemFeedBinding binding;

        public Holder(@NonNull ItemFeedBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FeedWallItem item) {
            binding.setData(item);

            GlideApp.with(itemView.getContext())
                    .load(item.getPostImage())
                    .timeout(30000)
                    .into(binding.ivPost);

            GlideApp.with(itemView.getContext())
                    .load(item.getProfileImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(binding.imgUser);

            GlideApp.with(itemView.getContext())
                    .load(item.getRestauranImg())
                    .timeout(30000)
                    .error(R.drawable.profile)
                    .into(binding.imgLogo);

            if (type.equals("tagged")) {
                binding.txtUserName.setVisibility(View.GONE);
                binding.imgUser.setVisibility(View.GONE);
            } else {
                binding.txtUserName.setVisibility(View.VISIBLE);
                binding.imgUser.setVisibility(View.VISIBLE);
            }
            binding.flSpoon.setVisibility(item.getMenuItemId()!=null?View.VISIBLE:View.GONE);
            binding.flSpoon.setOnClickListener(v -> mFeedCallBack.viewMenu(getAdapterPosition(),item));
            //binding.txtUserName.setOnClickListener(v -> mFeedCallBack.viewUser(getAdapterPosition()));
            binding.imgUser.setOnClickListener(v -> mFeedCallBack.viewUser(getAdapterPosition(),item));
            binding.imgLogo.setOnClickListener(v -> mFeedCallBack.viewRestro(getAdapterPosition(), item));
            binding.flComment.setOnClickListener(v -> mFeedCallBack.viewComment(getAdapterPosition(), item));
            binding.imgTag.setOnClickListener(v -> {
                item.setFavorite(!item.getFavorite());
                itemList.get(getAdapterPosition()).setFavorite(item.getFavorite());
                mFeedCallBack.onPostFavClick(getAdapterPosition(), item);
                notifyItemChanged(getAdapterPosition());
            });
            binding.imgLike.setOnClickListener(v -> {
                item.setLiked(!item.getLiked());
                item.setLikeCounter(item.getLiked() ? item.getLikeCounter() + 1 : item.getLikeCounter() - 1);
                itemList.get(getAdapterPosition()).setLiked(item.getLiked());
                itemList.get(getAdapterPosition()).setLikeCounter(item.getLikeCounter());
                mFeedCallBack.onPostLikeClick(getAdapterPosition(), item);
                notifyItemChanged(getAdapterPosition());
            });
            binding.layoutParent.setOnClickListener(v -> {
                if (binding.llIcons.getVisibility() == View.INVISIBLE) {
                    binding.llIcons.startAnimation(inFromRightAnimation());
                    binding.llIcons.setVisibility(View.VISIBLE);
                    binding.txtDesc.setVisibility(View.GONE);
                    binding.txtDescMore.setVisibility(View.VISIBLE);
                } else {
                    binding.llIcons.startAnimation(outToRightAnimation());
                    binding.llIcons.setVisibility(View.INVISIBLE);
                    binding.txtDesc.setVisibility(View.VISIBLE);
                    binding.txtDescMore.setVisibility(View.GONE);
                }

            });

        }
    }

    public interface FeedCallBack {
        void viewComment(int pos, FeedWallItem feedWallItem);

        void viewRestro(int pos, FeedWallItem item);

        void onPostLikeClick(int pos, FeedWallItem item);

        void onPostFavClick(int pos, FeedWallItem item);

        void viewUser(int pos, FeedWallItem item);

        void viewMenu(int pos, FeedWallItem item);
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(250);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(250);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(250);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public void setList(List<FeedWallItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void setIsMyProfile(boolean isMyProfile) {
        this.isMyProfile = isMyProfile;
    }

    public void addRow(FeedWallItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
