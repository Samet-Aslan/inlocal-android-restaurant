package com.inlocal.restaurantapp.ui.editprofile.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemTimeBinding;
import com.inlocal.restaurantapp.util.CustomTimePicker;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.Holder> {
    private ArrayList<String> mData;

    public TimeAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    public void addData(String s) {
        mData.add(s);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemTimeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemTimeBinding binding;

        public Holder(@NonNull ItemTimeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind() {
          /*  binding.edtStart.setOnClickListener(v -> {
                CustomTimePicker.openTimePicker(v.getContext(), binding.edtStart, "HH:MM a");
            });
            binding.edtEnd.setOnClickListener(v -> {
                CustomTimePicker.openTimePicker(v.getContext(), binding.edtEnd, "HH:MM a");
            });*/
        }
    }
}
