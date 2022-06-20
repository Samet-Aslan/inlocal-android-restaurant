package com.inlocal.restaurantapp.custom.dropdown;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.interfaces.OnitemClickListner;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;

import java.util.ArrayList;
import java.util.List;

public class DropDownCuisineListAdapter extends RecyclerView.Adapter<DropDownCuisineListAdapter.ViewHolder> {

    private Activity activity;
    private List<String> items;
    private OnitemClickListner onitemClickListner;
    List<CateListModel> cuisines;

    DropDownCuisineListAdapter(Activity activity, List<CateListModel> cuisines,
                               OnitemClickListner onitemClickListner) {
        this.activity = activity;
        this.cuisines = cuisines;
        this.onitemClickListner = onitemClickListner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_dropdown, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_dropdown.setText(cuisines.get(position).getName());
        if (onitemClickListner != null) {
            holder.tv_dropdown.setOnClickListener(view1 ->
                    onitemClickListner.onClick(null, position));
        }
    }

    @Override
    public int getItemCount() {
        return cuisines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_dropdown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_dropdown = itemView.findViewById(R.id.tv_dropdown);
        }
    }
}
