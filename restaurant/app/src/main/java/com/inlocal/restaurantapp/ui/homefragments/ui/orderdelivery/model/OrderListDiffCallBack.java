package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class OrderListDiffCallBack extends DiffUtil.Callback {

    private List<DeliveryOrderItem> mOldList;
    private List<DeliveryOrderItem> mNewList;

    public OrderListDiffCallBack(List<DeliveryOrderItem> mOldList, List<DeliveryOrderItem> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        return mOldList!=null?mOldList.size():0;
    }

    @Override
    public int getNewListSize() {
        return mNewList!=null?mNewList.size():0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == mNewList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final DeliveryOrderItem oldEmployee = mOldList.get(oldItemPosition);
        final DeliveryOrderItem newEmployee = mNewList.get(newItemPosition);

        return oldEmployee.getBookingId() == newEmployee.getBookingId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
