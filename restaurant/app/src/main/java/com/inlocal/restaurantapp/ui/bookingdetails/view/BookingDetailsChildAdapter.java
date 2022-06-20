package com.inlocal.restaurantapp.ui.bookingdetails.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.commonmodel.StoryItem;
import com.inlocal.restaurantapp.databinding.DialogRejectBinding;
import com.inlocal.restaurantapp.databinding.ItemBookingDetailsChildBinding;
import com.inlocal.restaurantapp.databinding.ItemBookingsBinding;
import com.inlocal.restaurantapp.ui.bookingdetails.model.OrderItems;

import java.text.DecimalFormat;
import java.util.List;

public class BookingDetailsChildAdapter extends RecyclerView.Adapter<BookingDetailsChildAdapter.Holder> {
    private List<OrderItems> orderItemsList;
    private OnOrderStatusListener listener;

    interface OnOrderStatusListener {
        void onOrderRejecte(int pos, OrderItems orderItems, String note);

        void onOrderAccept(int pos, OrderItems orderItems);
    }

    public BookingDetailsChildAdapter(OnOrderStatusListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemBookingDetailsChildBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(orderItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItemsList != null ? orderItemsList.size() : 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemBookingDetailsChildBinding binding;

        public Holder(@NonNull ItemBookingDetailsChildBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bind(OrderItems orderItems) {
            orderItems.setDisplayCalPrice(orderItems.getPrice() * orderItems.getQty());
            binding.setData(orderItems);
            //DecimalFormat df = new DecimalFormat("#.##");
            //binding.txtPrice.setText("€ " + df.format(orderItems.getPrice() * orderItems.getQty()));
            GlideApp.with(itemView.getContext())
                    .load("")
                    .timeout(30000)
                    .into(binding.ivProduct);
            if(orderItems.getOrderSubaddonItems()!=null && orderItems.getOrderSubaddonItems().size()>0) {
                binding.llTips.setVisibility(View.VISIBLE);
                binding.recyclerItem.setAdapter(new CustomizationAdapter(orderItems.getOrderSubaddonItems()));
            }else {
                binding.llTips.setVisibility(View.GONE);
            }
            binding.txtReject.setOnClickListener(v -> showDialog(v.getContext(), getAdapterPosition(), orderItems));
            binding.txtAccpet.setOnClickListener(v ->{
                listener.onOrderAccept(getAdapterPosition(),orderItems);
            });
        }
    }

    void showDialog(Context context, int pos, OrderItems orderItems) {
        Dialog dialog = new Dialog(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        DialogRejectBinding dialogLogoutBinding = DialogRejectBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLogoutBinding.btnYes.setOnClickListener(v -> dialog.cancel());
        dialogLogoutBinding.btnNo.setOnClickListener(v -> {
            dialog.cancel();
            listener.onOrderRejecte(pos,orderItems,dialogLogoutBinding.etRejectmsg.getText().toString());
        });
        dialog.getWindow().setLayout(((displayMetrics.widthPixels / 100) * 90), RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void setList(List<OrderItems> list) {
        this.orderItemsList = list;
        notifyDataSetChanged();
    }

    public void addRow(OrderItems item) {
        this.orderItemsList.add(item);
        notifyItemInserted(this.orderItemsList.size() - 1);
    }
}
