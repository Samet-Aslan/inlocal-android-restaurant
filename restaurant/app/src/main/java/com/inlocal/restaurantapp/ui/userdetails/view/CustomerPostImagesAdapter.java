package com.inlocal.restaurantapp.ui.userdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ItemCustomerPostImageBinding;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.util.List;

public class CustomerPostImagesAdapter extends RecyclerView.Adapter<CustomerPostImagesAdapter.Holder> {
    private ImagesCallback mImagesCallback;
    private List<FeedWallItem> itemList;
    private Integer showType = 0, pageIndex=0;

    public CustomerPostImagesAdapter(ImagesCallback mImagesCallback) {
        this.mImagesCallback = mImagesCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemCustomerPostImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemCustomerPostImageBinding binding;

        public Holder(@NonNull ItemCustomerPostImageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(FeedWallItem item) {
            binding.setData(item);
            GlideApp.with(itemView.getContext())
                    .load(item.getPostImage())
                    .timeout(30000)
                    .error(R.drawable.food)
                    .into(binding.imgFood);
            binding.getRoot().setOnClickListener(v -> {
                if(showType==0) {
                    mImagesCallback.click(getAdapterPosition(), item);
                }else{
                    mImagesCallback.clickForPagerAdapterItem(showType, getAdapterPosition(), item);
                }
            });
        }
    }

    public interface ImagesCallback {
        void click(int pos, FeedWallItem item);
        void clickForPagerAdapterItem(Integer showType, int pos, FeedWallItem item);
    }

    public void setItemList(List<FeedWallItem> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
        notifyDataSetChanged();
    }

    public void addRow(FeedWallItem item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
