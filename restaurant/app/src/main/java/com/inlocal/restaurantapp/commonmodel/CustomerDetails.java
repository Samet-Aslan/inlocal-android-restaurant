package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerDetails implements Serializable {

	@SerializedName("profilePicture")
	private String profilePicture;

	@SerializedName("isFollower")
	private Boolean isFollow=false;

	@SerializedName("posts_counter")
	private Integer postsCounter;

	@SerializedName("insight_counter")
	private Integer insightCounter;

	@SerializedName("followers")
	private Integer followers;

	@SerializedName("phone")
	private String phone;

	@SerializedName("followings")
	private Integer followings;

	@SerializedName("name")
	private String name;

	@SerializedName("customer_id")
	private Integer customerId;

	@SerializedName("email")
	private String email;

	public void setProfilePicture(String profilePicture){
		this.profilePicture = profilePicture;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public void setPostsCounter(Integer postsCounter){
		this.postsCounter = postsCounter;
	}

	public Integer getPostsCounter(){
		return postsCounter;
	}

	public void setInsightCounter(Integer insightCounter){
		this.insightCounter = insightCounter;
	}

	public Integer getInsightCounter(){
		return insightCounter;
	}

	public void setFollowers(Integer followers){
		this.followers = followers;
	}

	public Integer getFollowers(){
		return followers;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setFollowings(Integer followings){
		this.followings = followings;
	}

	public Integer getFollowings(){
		return followings;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCustomerId(Integer customerId){
		this.customerId = customerId;
	}

	public Integer getCustomerId(){
		return customerId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public Boolean getFollow() {
		return isFollow;
	}

	public void setFollow(Boolean follow) {
		isFollow = follow;
	}
}