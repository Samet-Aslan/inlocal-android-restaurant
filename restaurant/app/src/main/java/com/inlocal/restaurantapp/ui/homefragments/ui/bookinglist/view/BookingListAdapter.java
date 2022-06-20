package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.app.di.module.GlideApp;
import com.inlocal.restaurantapp.databinding.ItemBookingsBinding;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.BookingModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.Reservation;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.DateConveter;

import java.util.ArrayList;
import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.Holder> {

    private List<Reservation> itemList;
    private BookingListCallback mBookingListCallback;

    public BookingListAdapter(BookingListCallback mBookingListCallback) {
        this.mBookingListCallback = mBookingListCallback;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemBookingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList!=null?itemList.size():0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemBookingsBinding bookingsBinding;

        public Holder(@NonNull ItemBookingsBinding itemView) {
            super(itemView.getRoot());
            bookingsBinding = itemView;
        }

        void bind(Reservation bookingModel) {
            bookingsBinding.setData(bookingModel);
            GlideApp.with(itemView.getContext())
                    .load(bookingModel.getCustomer().getProfileImage())
                    .error(R.drawable.profile)
                    .timeout(30000)
                    .into(bookingsBinding.frameIcon);

            switch (bookingModel.getBookingStatus()) {
                case Constants.BookingStatus.PENDING:
                    bookingsBinding.txtPrice.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_grey_dot,null), null, null, null );
                    bookingsBinding.txtPrice.setText(bookingModel.getBookingStatus());
                    bookingsBinding.txtAccpet.setVisibility(View.VISIBLE);
                    bookingsBinding.txtReject.setVisibility(View.VISIBLE);
                    break;
                case Constants.BookingStatus.ACCEPTED:
                    bookingsBinding.txtPrice.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot,null), null, null, null );
                    bookingsBinding.txtPrice.setText(bookingModel.getBookingStatus());
                    bookingsBinding.txtAccpet.setVisibility(View.GONE);
                    bookingsBinding.txtReject.setVisibility(View.GONE);
                    break;
                case Constants.BookingStatus.CONFIRM:
                    bookingsBinding.txtPrice.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_blue_dot,null), null, null, null );
                    bookingsBinding.txtPrice.setText(bookingModel.getBookingStatus());
                    bookingsBinding.txtAccpet.setVisibility(View.GONE);
                    bookingsBinding.txtReject.setVisibility(View.GONE);
                    break;
                case Constants.BookingStatus.DECLINED:
                    bookingsBinding.txtPrice.setCompoundDrawablesWithIntrinsicBounds(itemView.getContext().getResources().getDrawable(R.drawable.ic_red_dot,null), null, null, null );
                    bookingsBinding.txtPrice.setText("Rejected");
                    bookingsBinding.txtAccpet.setVisibility(View.GONE);
                    bookingsBinding.txtReject.setVisibility(View.GONE);
                    break;
            }

            bookingsBinding.txtTime.setText(DateConveter.convertToBookingDateTime(bookingModel.getBookingDate()));
            //bookingsBinding.getRoot().setOnClickListener(v -> mBookingListCallback.click(getAdapterPosition()));
            bookingsBinding.txtAccpet.setOnClickListener(v->{
                mBookingListCallback.onAcceptClick(getAdapterPosition(),bookingModel);
            });

            bookingsBinding.txtReject.setOnClickListener(v->{
                mBookingListCallback.onRejectClick(getAdapterPosition(),bookingModel);
            });
        }
    }

    public interface BookingListCallback {
        void click(int pos);
        void onRejectClick(int pos, Reservation item);
        void onAcceptClick(int pos, Reservation item);
    }

    public void setList(List<Reservation> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }

    public void addRow(Reservation item) {
        this.itemList.add(item);
        notifyItemInserted(this.itemList.size() - 1);
    }
}
