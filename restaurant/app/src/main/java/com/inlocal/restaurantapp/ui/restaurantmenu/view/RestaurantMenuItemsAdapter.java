package com.inlocal.restaurantapp.ui.restaurantmenu.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ItemRestaurantMenuItemBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderItems;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.CategoryItemAdapter;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.RestaurantDetailsActivity;

import java.util.List;

public class RestaurantMenuItemsAdapter extends RecyclerView.Adapter<RestaurantMenuItemsAdapter.Holder> {


    private List<MenuItem> mData;
    private Context applicationContext;
    private ViewMenuDetailsCallbakc viewMenuDetailsCallbakc;

    public RestaurantMenuItemsAdapter(ViewMenuDetailsCallbakc viewMenuDetailsCallbakc) {
        this.viewMenuDetailsCallbakc = viewMenuDetailsCallbakc;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.applicationContext = parent.getContext();
        return new Holder(ItemRestaurantMenuItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemRestaurantMenuItemBinding binding;

        public Holder(@NonNull ItemRestaurantMenuItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MenuItem item) {
            binding.setData(item);

            GlideApp.with(itemView.getContext())
                    .load(item.getImage())
                    .timeout(30000)
                    .error(R.drawable.ic_cover)
                    .into(binding.imgUser);
            itemView.setOnClickListener(v -> {
                viewMenuDetailsCallbakc.view(getAdapterPosition(), item);
            });
        }
    }

    public void setList(List<MenuItem> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addRow(MenuItem item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }

    public interface ViewMenuDetailsCallbakc {
        void view(int pos,MenuItem item);
    }
}
