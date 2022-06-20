package com.inlocal.restaurantapp.ui.notificationssettings.model;

public class SettingsModel {
    private String title;
    private boolean ischecked;

    public SettingsModel(String title, boolean ischecked) {
        this.title = title;
        this.ischecked = ischecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
}
