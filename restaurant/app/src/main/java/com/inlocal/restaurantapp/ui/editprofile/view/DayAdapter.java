package com.inlocal.restaurantapp.ui.editprofile.view;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.interfaces.OnDateClick;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.databinding.ItemDayBinding;
import com.inlocal.restaurantapp.ui.editprofile.model.MonthDayModel;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.util.CustomTimePicker;
import com.inlocal.restaurantapp.util.DateConveter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.Holder> {
    private List<OpeningHoursItem> mData;
    private OnItemClick onItemClick;

    public DayAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
        /*if(mData.get(position).isSelected()){
            holder.binding.checkboxDay.setChecked(true);
        }else {
            holder.binding.checkboxDay.setChecked(false);
        }*/

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemDayBinding binding;
        String convertedEndTime = "", convertedStartTime = "";

        public Holder(@NonNull ItemDayBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        void bind(OpeningHoursItem model) {
            binding.setData(model);
            binding.edtStart.setText(DateConveter.getFormatedDateTime(model.getStartTime()));
            binding.edtEnd.setText(DateConveter.getFormatedDateTime(model.getCloseTime()));

            if (model.getIsOpen().equalsIgnoreCase("1")) {
                binding.checkboxDay.setBackgroundResource(R.drawable.ic_select_blue_large);
            } else {
                binding.checkboxDay.setBackgroundResource(R.drawable.ic_unselected);
            }
            binding.checkboxDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.getIsOpen().equalsIgnoreCase("1")) {
                        binding.checkboxDay.setBackgroundResource(R.drawable.ic_unselected);
                        model.setIsOpen("0");
                    } else {
                        binding.checkboxDay.setBackgroundResource(R.drawable.ic_select_blue_large);
                        model.setIsOpen("1");
                    }
                    onItemClick.onItemClick(getAdapterPosition(), model);
                }
            });

            binding.edtStart.setOnClickListener(v -> {
                //  if (binding.checkboxDay.isChecked()) {
                if (model.getIsOpen().equalsIgnoreCase("1")) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if (!convertedEndTime.equalsIgnoreCase("")) {
                                convertedStartTime = DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute);
                                if (DateConveter.compaireStartEndTime(convertedStartTime, convertedEndTime)) {
                                    binding.edtStart.setText(DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute));
                                    model.setStartTime(DateConveter.getTimeFormat2ToFormat1(convertedStartTime) + ":00");
                                } else {
                                    Toast.makeText(v.getContext(), "Closing time must be greater than opening time", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                convertedStartTime = DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute);
                                binding.edtStart.setText(DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute));
                                model.setStartTime(DateConveter.getTimeFormat2ToFormat1(convertedStartTime) + ":00");
                            }
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(v.getContext().getString(R.string.select_time));
                    mTimePicker.show();
                }
            });

            binding.edtEnd.setOnClickListener(v -> {
                // if (binding.checkboxDay.isChecked()) {
                if (model.getIsOpen().equalsIgnoreCase("1")) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if (convertedStartTime.equalsIgnoreCase("")) {
                                Toast.makeText(v.getContext(), v.getContext().getString(R.string.select_opening_time_first), Toast.LENGTH_SHORT).show();
                            } else {
                                convertedEndTime = DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute);
                                if (DateConveter.compaireStartEndTime(convertedStartTime, convertedEndTime)) {
                                    binding.edtEnd.setText(DateConveter.getFormatedDateTime(selectedHour + ":" + selectedMinute));
                                    model.setCloseTime(DateConveter.getTimeFormat2ToFormat1(convertedEndTime) + ":00");
                                } else {
                                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.closing_time_greater_than_opening_time), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(v.getContext().getString(R.string.select_time));
                    mTimePicker.show();
                }
            });
        }
    }


    public interface OnItemClick {
        void onItemClick(int pos, OpeningHoursItem model);

    }

    public void setList(List<OpeningHoursItem> openingHoursItems) {
        this.mData = openingHoursItems;
        notifyDataSetChanged();
    }

    public void updatePost(int pos, OpeningHoursItem model) {
        this.mData.set(pos, model);
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }

}
