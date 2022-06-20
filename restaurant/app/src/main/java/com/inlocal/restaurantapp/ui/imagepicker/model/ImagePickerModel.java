package com.inlocal.restaurantapp.ui.imagepicker.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImagePickerModel implements Parcelable {
    private String filePath;
    private String date;
    private String type;
    private Bitmap thumbnail;
    private String size;
    private long duration;
    private boolean isSelected;

    public ImagePickerModel(String filePath, String date, String type, Bitmap thumbnail, String size,
                            long duration, boolean isSelected) {
        this.filePath = filePath;
        this.date = date;
        this.type = type;
        this.thumbnail = thumbnail;
        this.size = size;
        this.duration = duration;
        this.isSelected=isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filePath);
        dest.writeString(this.date);
        dest.writeString(this.type);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeString(this.size);
        dest.writeLong(this.duration);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected ImagePickerModel(Parcel in) {
        this.filePath = in.readString();
        this.date = in.readString();
        this.type = in.readString();
        this.thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        this.size = in.readString();
        this.duration = in.readLong();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<ImagePickerModel> CREATOR = new Creator<ImagePickerModel>() {
        @Override
        public ImagePickerModel createFromParcel(Parcel source) {
            return new ImagePickerModel(source);
        }

        @Override
        public ImagePickerModel[] newArray(int size) {
            return new ImagePickerModel[size];
        }
    };
}
