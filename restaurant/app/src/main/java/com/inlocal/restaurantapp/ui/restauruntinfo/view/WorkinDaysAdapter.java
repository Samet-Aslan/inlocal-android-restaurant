package com.inlocal.restaurantapp.ui.restauruntinfo.view;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.databinding.ItemDayBinding;
import com.inlocal.restaurantapp.databinding.ItemDayWorkingBinding;
import com.inlocal.restaurantapp.databinding.ItemImageBinding;
import com.inlocal.restaurantapp.ui.editprofile.view.DayAdapter;
import com.inlocal.restaurantapp.util.DateConveter;

import java.util.Calendar;
import java.util.List;

public class WorkinDaysAdapter extends RecyclerView.Adapter<WorkinDaysAdapter.Holder> {

    private List<OpeningHoursItem> mData;


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDayWorkingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        ItemDayWorkingBinding binding;

        public Holder(@NonNull ItemDayWorkingBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        void bind(OpeningHoursItem model) {
            binding.setData(model);
            binding.tvTuesdayTime.setText(" : "+DateConveter.getFormatedDateTime(model.getStartTime())+" - "+DateConveter.getFormatedDateTime(model.getCloseTime()));
            binding.viewLine.setVisibility(getAdapterPosition()==mData.size()-1?View.GONE:View.VISIBLE);
        }
    }

    public void setList(List<OpeningHoursItem> openingHoursItems) {
        this.mData = openingHoursItems;
        notifyDataSetChanged();
    }
}
