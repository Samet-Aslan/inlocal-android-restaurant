package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.SerializedName;

public class CommentListResponse{

	@SerializedName("data")
	private CommentData data;

	public void setData(CommentData data){
		this.data = data;
	}

	public CommentData getData(){
		return data;
	}
}