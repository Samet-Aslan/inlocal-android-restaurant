package com.inlocal.restaurantapp.ui.comment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemCommentBinding;
import com.inlocal.restaurantapp.ui.comment.model.CommentsItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.util.Constants;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder> {
    private List<CommentsItem> commentsItemList;
    private OnCommentItemListener listener;

    public interface OnCommentItemListener {
        void onCommentUserClick(int pos, CommentsItem commentsItem);
    }

    public CommentAdapter(OnCommentItemListener commentItemListener) {
        this.listener = commentItemListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(commentsItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsItemList != null ? commentsItemList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCommentBinding itemCommentBinding;

        public Holder(@NonNull ItemCommentBinding itemView) {
            super(itemView.getRoot());
            itemCommentBinding = itemView;
        }

        void bind(CommentsItem commentsItem) {
            itemCommentBinding.setData(commentsItem);

            GlideApp.with(itemView.getContext())
                    .load(commentsItem.getUser_profile_image())
                    .timeout(30000)
                    .into(itemCommentBinding.imgUser);

            itemCommentBinding.imgUser.setOnClickListener(v -> {
                listener.onCommentUserClick(getAdapterPosition(), commentsItem);
            });
        }
    }


    public void setList(List<CommentsItem> commentsItemList) {
        this.commentsItemList = commentsItemList;
        notifyDataSetChanged();
    }

    public void addRow(CommentsItem item) {
        this.commentsItemList.add(item);
        notifyItemInserted(this.commentsItemList.size() - 1);
    }
}
