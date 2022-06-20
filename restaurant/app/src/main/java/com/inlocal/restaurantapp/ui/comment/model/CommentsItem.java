package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentsItem implements Serializable {

	@SerializedName("user_comment_by")
	private Integer userCommentBy;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	@SerializedName("message")
	private String message;

	@SerializedName("comment_user_type")
	private String commentUserType;

	@SerializedName("user_profile_image")
	private String user_profile_image;

	public String getUser_profile_image() {
		return user_profile_image;
	}

	public void setUser_profile_image(String user_profile_image) {
		this.user_profile_image = user_profile_image;
	}

	public void setUserCommentBy(Integer userCommentBy){
		this.userCommentBy = userCommentBy;
	}

	public Integer getUserCommentBy(){
		return userCommentBy;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
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