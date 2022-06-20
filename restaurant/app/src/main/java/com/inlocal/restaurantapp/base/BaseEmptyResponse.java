package com.inlocal.restaurantapp.base;

import com.google.gson.annotations.SerializedName;

public class BaseEmptyResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
