package com.inlocal.restaurantapp.ui.bookingdetails.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemBookingDetailsBinding;
import com.inlocal.restaurantapp.databinding.ItemBookingsBinding;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.Holder> {

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemBookingDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 7;
    }

     class Holder extends RecyclerView.ViewHolder {
         ItemBookingDetailsBinding binding;

        public Holder(@NonNull ItemBookingDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind() {
            //binding.recyclerItem.setAdapter(new BookingDetailsChildAdapter());
        }
    }
}
