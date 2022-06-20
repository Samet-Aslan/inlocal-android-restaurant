package com.inlocal.restaurantapp.ui.statistics.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ItemBestSellerBinding;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.Holder> {

    private List<MenuItem> menuItemList;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemBestSellerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(menuItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItemList != null ? menuItemList.size() <= 4 ? menuItemList.size() : 4 : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemBestSellerBinding bestSellerBinding;

        public Holder(@NonNull ItemBestSellerBinding itemView) {
            super(itemView.getRoot());
            bestSellerBinding = itemView;
        }

        void bind(MenuItem menuItem) {
            bestSellerBinding.setData(menuItem);
            GlideApp.with(itemView.getContext())
                    .load(menuItem.getImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(bestSellerBinding.imgItem);
        }
    }

    public void setList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
        notifyDataSetChanged();
    }
}
