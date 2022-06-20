package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.SerializedName;

public class CommentRequestBody{

	@SerializedName("post_id")
	private Integer postId;

	@SerializedName("message")
	private String message;

	@SerializedName("comment_user_type")
	private String commentUserType;

	public void setPostId(Integer postId){
		this.postId = postId;
	}

	public Integer getPostId(){
		return postId;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setCommentUserType(String commentUserType){
		this.commentUserType = commentUserType;
	}

	public String getCommentUserType(){
		return commentUserType;
	}
}