package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.SerializedName;

public class CommentListRequest{

	public CommentListRequest(Integer skip,Integer limit, int postId) {
		this.limit = limit;
		this.skip = skip;
		this.postId = postId;
	}


	@SerializedName("post_id")
	private Integer postId;

	@SerializedName("limit")
	private Integer limit;

	@SerializedName("skip")
	private Integer skip;

	public void setPostId(Integer postId){
		this.postId = postId;
	}

	public Integer getPostId(){
		return postId;
	}

	public void setLimit(Integer limit){
		this.limit = limit;
	}

	public Integer getLimit(){
		return limit;
	}

	public void setSkip(Integer skip){
		this.skip = skip;
	}

	public Integer getSkip(){
		return skip;
	}
}