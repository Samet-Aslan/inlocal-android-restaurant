package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemCategoryBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    private List<CateListModel> mData;
    int row_index;
    private OnCateItemClickListener listener;

    public CategoryAdapter(List<CateListModel> mData) {
        this.mData = mData;
    }

    public CategoryAdapter(OnCateItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemCategoryBinding binding;

        public Holder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(CateListModel model) {
            binding.setData(model);
            binding.getRoot().setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition(), model);
            });
        }
    }

    public void setList(List<CateListModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addRow(CateListModel item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }

    public interface OnCateItemClickListener {
        void onItemClick(int pos, CateListModel item);
    }
}
