package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestPostDetails implements Serializable {
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("post_id")
    @Expose
    private Integer postId;

    public RequestPostDetails(String loginUserType, Integer postId) {
        this.loginUserType = loginUserType;
        this.postId = postId;
    }

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
}
