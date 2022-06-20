package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestReport implements Serializable {
    public RequestReport(String loginUserType, Integer postId, String type) {
        this.loginUserType = loginUserType;
        this.postId = postId;
        this.type = type;
    }

    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("type")
    @Expose
    private String type;

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
