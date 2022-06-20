package com.inlocal.restaurantapp.ui.categorylist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemCatAddBinding;
import com.inlocal.restaurantapp.databinding.ItemCatListBinding;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;

import java.util.ArrayList;
import java.util.List;

public class CateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CateListModel> mData;
    private AddCatCallback mAddCatCallback;
    private static final int VIEW_TYPE_ADD = 1;
    private static final int VIEW_TYPE_CATE = 2;

    public CateListAdapter(List<CateListModel> mData, AddCatCallback mAddCatCallback) {
        this.mData = mData;
        this.mAddCatCallback = mAddCatCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ADD) {
            return new AddViewHolder(ItemCatAddBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new CateViewHolder(ItemCatListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mData.get(position).getType() == 1) {
            ((AddViewHolder) holder).bind();
        } else {
            ((CateViewHolder) holder).bind(mData.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType() == 1) {
            return VIEW_TYPE_ADD;
        } else {
            return VIEW_TYPE_CATE;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class CateViewHolder extends RecyclerView.ViewHolder {
        ItemCatListBinding binding;

        public CateViewHolder(@NonNull ItemCatListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(CateListModel cateListModel) {
            binding.setData(cateListModel);
            binding.imgEdit.setOnClickListener(v -> {
                mAddCatCallback.update(getAdapterPosition(), cateListModel);
            });

            binding.imgDelete.setOnClickListener(v -> {
                mAddCatCallback.delete(getAdapterPosition(), cateListModel);
            });
        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {
        public AddViewHolder(@NonNull ItemCatAddBinding itemView) {
            super(itemView.getRoot());
            itemView.getRoot().setOnClickListener(v -> mAddCatCallback.add());
        }

        void bind() {

        }
    }

    public interface AddCatCallback {
        void add();

        void update(int pos, CateListModel model);

        void delete(int pos, CateListModel model);
    }

    public void setList(List<CateListModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addRow(CateListModel item) {
        this.mData.add(item);
        notifyItemInserted(this.mData.size() - 1);
    }

    public void removeItem(int pos) {
        //this.mData.remove(pos);
        notifyDataSetChanged();
    }
}
