package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestDeletePost implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;

    public RequestDeletePost(Integer id, String postUserType) {
        this.id = id;
        this.postUserType = postUserType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostUserType() {
        return postUserType;
    }

    public void setPostUserType(String postUserType) {
        this.postUserType = postUserType;
    }

}
