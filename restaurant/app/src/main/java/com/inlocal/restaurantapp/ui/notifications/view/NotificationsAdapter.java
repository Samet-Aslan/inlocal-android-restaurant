package com.inlocal.restaurantapp.ui.notifications.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ItemNotificationListBinding;
import com.inlocal.restaurantapp.ui.notifications.model.NotificationsModel;
import com.inlocal.restaurantapp.ui.notifications.viewmodel.NotificationViewModel;
import com.inlocal.restaurantapp.util.DateConveter;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.Holder> {

    private List<NotificationsModel> mData;
    private OnNotificationClickListener listener;

    public NotificationsAdapter(OnNotificationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemNotificationListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemNotificationListBinding binding;

        public Holder(@NonNull ItemNotificationListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(NotificationsModel data) {
            binding.setData(data);
            GlideApp.with(itemView.getContext())
                    .load(data.getFromImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(binding.ivPic);

            binding.txtTime.setText(DateConveter.convertDateInDotedFormate(data.getTime()));
            binding.txtPrice.setText(DateConveter.convertNotificationDateToTime(data.getTime()));
            binding.getRoot().setOnClickListener(v -> {
                listener.OnNotificationClick(data, getAdapterPosition());
            });
        }
    }


    public interface OnNotificationClickListener {
        void OnNotificationClick(NotificationsModel item, int pos);
    }

    public void setList(List<NotificationsModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addRow(NotificationsModel item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }
}
