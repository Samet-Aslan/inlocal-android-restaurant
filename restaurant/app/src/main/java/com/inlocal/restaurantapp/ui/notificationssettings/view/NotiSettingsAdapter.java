package com.inlocal.restaurantapp.ui.notificationssettings.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemNotiSettingsBinding;
import com.inlocal.restaurantapp.ui.notificationssettings.model.SettingsModel;

import java.util.ArrayList;
import java.util.List;

public class NotiSettingsAdapter extends RecyclerView.Adapter<NotiSettingsAdapter.Holder> {

    private List<SettingsModel> mData;

    public NotiSettingsAdapter(List<SettingsModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemNotiSettingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        //holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemNotiSettingsBinding binding;

        public Holder(@NonNull ItemNotiSettingsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(SettingsModel settingsModel) {
            binding.setData(settingsModel);
        }
    }
}
