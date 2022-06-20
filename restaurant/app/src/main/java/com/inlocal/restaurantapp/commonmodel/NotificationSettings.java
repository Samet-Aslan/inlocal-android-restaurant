package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationSettings implements Serializable {
    @SerializedName("stories")
    private String stories;

    @SerializedName("comments")
    private String comments;

    @SerializedName("likes")
    private String likes;

    @SerializedName("followers")
    private String followers;

    @SerializedName("post")
    private String post;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("restaurant_id")
    private String restaurantId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("orders")
    private String orders;

    @SerializedName("payment")
    private String payment;

    @SerializedName("id")
    private String id;

    @SerializedName("bookings")
    private String bookings;

    public void setStories(String stories){
        this.stories = stories;
    }

    public String getStories(){
        return stories;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    public String getComments(){
        return comments;
    }

    public void setFollowers(String followers){
        this.followers = followers;
    }

    public String getFollowers(){
        return followers;
    }

    public void setPost(String post){
        this.post = post;
    }

    public String getPost(){
        return post;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setRestaurantId(String restaurantId){
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId(){
        return restaurantId;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setOrders(String orders){
        this.orders = orders;
    }

    public String getOrders(){
        return orders;
    }

    public void setPayment(String payment){
        this.payment = payment;
    }

    public String getPayment(){
        return payment;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setBookings(String bookings){
        this.bookings = bookings;
    }

    public String getBookings(){
        return bookings;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
