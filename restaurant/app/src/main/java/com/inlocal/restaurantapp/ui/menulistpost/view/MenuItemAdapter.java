package com.inlocal.restaurantapp.ui.menulistpost.view;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ItemAddCategoryBinding;
import com.inlocal.restaurantapp.databinding.ItemCategoryItemBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.CategoryModel;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.Holder> {

    private List<MenuItem> mData;
    private OnMenuItemListener listener;

    public MenuItemAdapter(OnMenuItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        /*if (position == 0) {
            return CategoryModel.VIEW_TYPE_ADD;
        } else {*/
            return CategoryModel.VIEW_TYPE_ITEM;
        //}
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCategoryItemBinding binding;

        public Holder(@NonNull ItemCategoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(MenuItem menuItem) {
            binding.setData(menuItem);
            binding.imgItem.setImageUrl(menuItem.getImage());
            binding.imgEdit.setVisibility(View.INVISIBLE);
            binding.imgDelete.setVisibility(View.INVISIBLE);
            binding.getRoot().setOnClickListener(v -> {
                listener.onMenuItemClick(getAdapterPosition(), menuItem);
            });
        }
    }

    public interface OnMenuItemListener {

        void onMenuItemClick(int pos, MenuItem item);
    }

    public void setList(List<MenuItem> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addRow(MenuItem item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }

    public void removeItem(int pos) {
        //this.mData.remove(pos);
        notifyDataSetChanged();
    }
}
