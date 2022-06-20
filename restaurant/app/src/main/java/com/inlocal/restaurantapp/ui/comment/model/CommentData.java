package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.SerializedName;

public class CommentData {

	@SerializedName("total")
	private Integer total;

	@SerializedName("limit")
	private Integer limit;

	@SerializedName("CommentList")
	private CommentList commentList;

	@SerializedName("skip")
	private Integer skip;

	public void setTotal(Integer total){
		this.total = total;
	}

	public Integer getTotal(){
		return total;
	}

	public void setLimit(Integer limit){
		this.limit = limit;
	}

	public Integer getLimit(){
		return limit;
	}

	public void setCommentList(CommentList commentList){
		this.commentList = commentList;
	}

	public CommentList getCommentList(){
		return commentList;
	}

	public void setSkip(Integer skip){
		this.skip = skip;
	}

	public Integer getSkip(){
		return skip;
	}
}