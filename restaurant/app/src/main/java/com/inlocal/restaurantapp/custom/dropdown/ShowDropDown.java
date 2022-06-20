package com.inlocal.restaurantapp.custom.dropdown;

import static android.widget.PopupWindow.INPUT_METHOD_NEEDED;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.interfaces.OnitemClickListner;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;

import java.util.ArrayList;
import java.util.List;

public class ShowDropDown {
    private Activity activity;
    private PopupWindow popup;
    private View contentLayout, contentLayoutstates;

    public ShowDropDown(Activity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        contentLayout = layoutInflater.inflate(R.layout.dialog_dropdown, null);

        popup = new PopupWindow(contentLayout);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setInputMethodMode(INPUT_METHOD_NEEDED);
        popup.setFocusable(false);
        popup.setOutsideTouchable(true);
    }


    public void showCuisines(View anchor, List<CateListModel> items, OnitemClickListner onUiEventListener) {
        popup.setWidth(anchor.getWidth());
        popup.showAsDropDown(anchor, Gravity.NO_GRAVITY, 0, 0);

        setViewStates(anchor, items,onUiEventListener);
    }



    public PopupWindow getPopup() {
        return popup;
    }


    private void setViewStates(View anchor, List<CateListModel> items, OnitemClickListner onUiEventListener) {
        RecyclerView rv = contentLayout.findViewById(R.id.rv_popup);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.setAdapter(new DropDownCuisineListAdapter(activity, items, (view, position) -> {
            if (anchor instanceof TextView) {
                ((TextView) anchor).setText(items.get(position).getName());
            }
            if (onUiEventListener != null)
                onUiEventListener.onClick(view, position);
            popup.dismiss();
        }));
    }
}
