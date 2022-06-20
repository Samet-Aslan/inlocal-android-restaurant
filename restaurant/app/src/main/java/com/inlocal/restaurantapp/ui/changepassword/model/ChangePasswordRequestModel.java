package com.inlocal.restaurantapp.ui.changepassword.model;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequestModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("oldPassword")
    private  String oldPassword;

    @SerializedName("newPassword")
    private  String newPassword;

    public ChangePasswordRequestModel(int id, String oldPassword, String newPassword){
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}
