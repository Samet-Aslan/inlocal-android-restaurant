package com.inlocal.restaurantapp.ui.comment.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CommentList implements Serializable {

	@SerializedName("post_image")
	private String image;

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("restaurant_id")
	private Integer restaurantId;

	@SerializedName("id")
	private Integer id;

	@SerializedName("message")
	private String message;

	@SerializedName("like_counter")
	private Integer likeCounter;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setRestaurantId(Integer restaurantId){
		this.restaurantId = restaurantId;
	}

	public Integer getRestaurantId(){
		return restaurantId;
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

	public void setLikeCounter(Integer likeCounter){
		this.likeCounter = likeCounter;
	}

	public Integer getLikeCounter(){
		return likeCounter;
	}
}