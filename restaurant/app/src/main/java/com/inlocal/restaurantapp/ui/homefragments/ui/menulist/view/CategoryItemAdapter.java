package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.databinding.ItemAddCategoryBinding;
import com.inlocal.restaurantapp.databinding.ItemCategoryItemBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.CategoryModel;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MenuItem> mData;
    private OnMenuItemListener listener;

    public CategoryItemAdapter(OnMenuItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CategoryModel.VIEW_TYPE_ADD) {
            return new AddItemHolder(ItemAddCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new Holder(ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

   /* @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }*/

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((AddItemHolder) holder).bind();
        } else {
            ((Holder) holder).bind(mData.get(position));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CategoryModel.VIEW_TYPE_ADD;
        } else {
            return CategoryModel.VIEW_TYPE_ITEM;
        }
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
            binding.imgEdit.setOnClickListener(v -> {
                listener.onMenuItemEditClick(getAdapterPosition(),menuItem);
            });
            binding.imgDelete.setOnClickListener(v -> {
                listener.onMenuItemDeleteClick(getAdapterPosition(),menuItem);
            });
        }
    }

    public class AddItemHolder extends RecyclerView.ViewHolder {

        ItemAddCategoryBinding binding;

        public AddItemHolder(@NonNull ItemAddCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind() {

            binding.getRoot().setOnClickListener(v -> {
                listener.onAddClick(getAdapterPosition());
            });
        }
    }

    public interface OnMenuItemListener {
        void onAddClick(int pos);

        void onMenuItemDeleteClick(int pos, MenuItem item);

        void onMenuItemEditClick(int pos, MenuItem item);

        void onMenuItemClick(int pos);
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
