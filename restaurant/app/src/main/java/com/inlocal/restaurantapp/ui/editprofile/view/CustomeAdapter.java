package com.inlocal.restaurantapp.ui.editprofile.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemCustomeBinding;
import com.inlocal.restaurantapp.databinding.ItemDayBinding;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeSubItem;
import com.inlocal.restaurantapp.ui.editprofile.model.MonthDayModel;

import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.Holder> {
    private List<CustomizeSubItem> mData;
    private OnCustomeItemListener listener;

    public interface OnCustomeItemListener {
        void onCustomeItemOption(int pos, CustomizeSubItem dat);

        void onCustomeItemCheckClick(int pos, CustomizeSubItem dat);
    }

    public CustomeAdapter(List<CustomizeSubItem> mData, OnCustomeItemListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCustomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemCustomeBinding binding;

        public Holder(@NonNull ItemCustomeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(CustomizeSubItem model) {
            binding.setData(model);

            binding.ivOption.setOnClickListener(v -> {
                listener.onCustomeItemOption(getAdapterPosition(), model);
            });

            binding.checkboxDay.setOnClickListener(v -> {
                model.setActive(binding.checkboxDay.isChecked() ? 1 : 0);
                listener.onCustomeItemCheckClick(getAdapterPosition(), model);
            });
        }
    }
}
