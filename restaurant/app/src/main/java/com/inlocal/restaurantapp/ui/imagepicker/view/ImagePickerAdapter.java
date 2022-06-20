package com.inlocal.restaurantapp.ui.imagepicker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlocal.restaurantapp.databinding.ItemImagePickerBinding;
import com.inlocal.restaurantapp.ui.imagepicker.model.ImagePickerModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.Holder> {

    public ArrayList<ImagePickerModel> mImages;
    private ImageSelected mImageSelected;
    private int lastCheckedPosition = -1;
    private String max = "";
    private String type = "";

    public ImagePickerAdapter(ArrayList<ImagePickerModel> mImages, ImageSelected mImageSelected) {
        this.mImages = mImages;
        this.mImageSelected = mImageSelected;
        this.max = max;
        this.type = type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemImagePickerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemImagePickerBinding binding;

        public Holder(@NonNull ItemImagePickerBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(ImagePickerModel imagePickerModel) {
            binding.image.setImageUrl(imagePickerModel.getFilePath());
            binding.getRoot().setOnClickListener(v -> mImageSelected.onlyOneSelected(getAdapterPosition()));
        }
    }

    public interface ImageSelected {
        void selected(boolean isAdded);

        void onlyOneSelected(int pos);

        void trimVideo(int pos);
    }

    public static String convertSecondsToHMmSs(double miliSeconds) {
        long milis = Double.valueOf(miliSeconds).longValue() * 1000;
        long hours = TimeUnit.MILLISECONDS.toHours(milis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milis) % 60;
        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        } else if (seconds > 0) {
            return String.format(Locale.ENGLISH, "00:%02d", seconds);
        } else {
            return "00:00";
        }
    }

    public static String convertSecondsToHMmSs(long miliSeconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        } else if (seconds > 0) {
            return String.format(Locale.ENGLISH, "00:%02d", seconds);
        } else {
            return "00:00";
        }
    }

    private void showAlert(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                }).show();
    }

}

