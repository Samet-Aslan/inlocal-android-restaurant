package com.inlocal.restaurantapp.ui.additem.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.databinding.DialogAddItemBinding;
import com.inlocal.restaurantapp.databinding.DialogDeleteBinding;
import com.inlocal.restaurantapp.databinding.DialogReportStoryBinding;
import com.inlocal.restaurantapp.databinding.ItemAddItemBinding;
import com.inlocal.restaurantapp.ui.additem.model.AddItemModel;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeList;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeSubItem;
import com.inlocal.restaurantapp.ui.editprofile.model.MonthDayModel;
import com.inlocal.restaurantapp.ui.editprofile.view.CustomeAdapter;
import com.inlocal.restaurantapp.util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.Holder> {

    private List<CustomizeList> mData;
    private Context context;

    private OnAddItemParentListener listener;

    public interface OnAddItemParentListener {
        void onAddItemSelected(int pos, CustomizeList data);
    }

    public AddItemAdapter(List<CustomizeList> mData, OnAddItemParentListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(ItemAddItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements CustomeAdapter.OnCustomeItemListener {
        ItemAddItemBinding binding;
        CustomeAdapter customeAdapter;

        public Holder(@NonNull ItemAddItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(CustomizeList addItemModel) {
            customeAdapter = new CustomeAdapter(addItemModel.getCustomizeSubItem(), this);
            binding.txtName.setText(addItemModel.getTitle());
            binding.recyclerItem.setAdapter(customeAdapter);
            binding.llAddItem.setOnClickListener(v -> {
                openDialog(v.getContext(), getAdapterPosition(), false, -1);
            });

            binding.ivEdit.setOnClickListener(v -> {
                listener.onAddItemSelected(getAdapterPosition(), addItemModel);
            });
        }

        @Override
        public void onCustomeItemOption(int pos, CustomizeSubItem dat) {
            showEditDeleteDialog(context, pos, getAdapterPosition(), dat);
        }

        @Override
        public void onCustomeItemCheckClick(int pos, CustomizeSubItem dat) {
            mData.get(getAdapterPosition()).getCustomizeSubItem().get(pos).setActive(dat.getActive());
        }
    }

    private void openDialog(Context context, int pos, boolean isEdit, int childPos) {
        Dialog dialog = new Dialog(context);
        DialogAddItemBinding dialogLogoutBinding = DialogAddItemBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLogoutBinding.tvEtPrice.setVisibility(View.VISIBLE);
        dialogLogoutBinding.edtPrice.setVisibility(View.VISIBLE);
        if (isEdit) {
            dialogLogoutBinding.edtName.setText(mData.get(pos).getCustomizeSubItem().get(childPos).getName());
            dialogLogoutBinding.edtPrice.setText(mData.get(pos).getCustomizeSubItem().get(childPos).getPrice().toString().replace("€", ""));
            dialogLogoutBinding.btnNo.setText(context.getString(R.string.update_item));
        } else {

        }
        dialogLogoutBinding.btnYes.setOnClickListener(v -> {
            dialog.cancel();
            KeyboardUtil.hideSoftKeyboard(v);
        });
        dialogLogoutBinding.btnNo.setOnClickListener(v -> {
            if (dialogLogoutBinding.edtName.getText().toString().trim().equals("")) {
                Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show();
            } else if (dialogLogoutBinding.edtPrice.getText().toString().trim().equals("")) {
                Toast.makeText(context, "Please enter price", Toast.LENGTH_SHORT).show();
            } else {
                dialog.cancel();
                KeyboardUtil.hideSoftKeyboard(v);
                if (isEdit) {
                    mData.get(pos).getCustomizeSubItem().get(childPos).setName(dialogLogoutBinding.edtName.getText().toString());
                    mData.get(pos).getCustomizeSubItem().get(childPos).setPrice(Double.parseDouble(dialogLogoutBinding.edtPrice.getText().toString()));
                } else {
                    List<CustomizeSubItem> strings = mData.get(pos).getCustomizeSubItem();
                    strings.add(new CustomizeSubItem(dialogLogoutBinding.edtName.getText().toString(), Double.parseDouble(dialogLogoutBinding.edtPrice.getText().toString()), 1));
                    mData.get(pos).setCustomizeSubItem(strings);
                }
                notifyDataSetChanged();
//                mAddItemAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private void showEditDeleteDialog(Context context, int childPos, int parentPos, CustomizeSubItem data) {
        Dialog dialog = new Dialog(context);
        DialogDeleteBinding dialogLogoutBinding = DialogDeleteBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialogLogoutBinding.txtDelete.setOnClickListener(v -> {
            dialog.cancel();
            KeyboardUtil.hideSoftKeyboard(v);
            mData.get(parentPos).getCustomizeSubItem().remove(childPos);
            notifyDataSetChanged();
        });
        dialogLogoutBinding.txtEdit.setOnClickListener(v -> {
            dialog.cancel();
            KeyboardUtil.hideSoftKeyboard(v);
            openDialog(context, parentPos, true, childPos);
        });
        dialogLogoutBinding.txtCancel.setOnClickListener(v -> {
            dialog.cancel();
            KeyboardUtil.hideSoftKeyboard(v);
        });

        dialog.show();
    }
}
