package com.inlocal.restaurantapp.ui.editprofile.model.getProfile;

import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.Location;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.commonmodel.Phone;

import java.io.Serializable;
import java.util.List;

public class RestaurantDetails implements Serializable {

	@SerializedName("restaurant_id")
	private Integer restaurantId;

	@SerializedName("owner_email")
	private String ownerEmail;

	@SerializedName("location")
	private Location location;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("landmark")
	private String landmark;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("city")
	private String city;

	@SerializedName("address")
	private String address;

	@SerializedName("description")
	private String description;

	@SerializedName("owner_phone")
	private String ownerPhone;

	@SerializedName("like_counter")
	private Integer likeCounter;

	@SerializedName("restDeliveryAvailable")
	private String restDeliveryAvailable;

	@SerializedName("zipcode")
	private String zipcode;

	@SerializedName("profilePicture")
	private String profilePicture;

	@SerializedName("favaourite_counter")
	private Integer favaouriteCounter;

	@SerializedName("posts_counter")
	private Integer postsCounter;

	@SerializedName("insight_counter")
	private Integer insightCounter;

	@SerializedName("followers")
	private Integer followers;

	@SerializedName("mobile_verified")
	private String mobileVerified;

	@SerializedName("phone")
	private Phone phone;

	@SerializedName("followings")
	private Integer followings;

	@SerializedName("name")
	private String name;

	@SerializedName("cuisineCategory")
	private CuisineCategory cuisineCategory;

	@SerializedName("id")
	private Integer id;

	@SerializedName("cover_image")
	private String coverImage;

	@SerializedName("IsFollow")
	private Boolean isFollow;

	@SerializedName("openingHours")
	private List<OpeningHoursItem> openingHours;

	@SerializedName("restReservationAvailable")
	private String restReservationAvailable;

	@SerializedName("email")
	private String email;

	@SerializedName("delivery_note")
	private String deliveryNote;

	@SerializedName("delivery_charges")
	private Double deliveryCharges;

	public void setOwnerEmail(String ownerEmail){
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerEmail(){
		return ownerEmail;
	}


	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setOwnerPhone(String ownerPhone){
		this.ownerPhone = ownerPhone;
	}

	public String getOwnerPhone(){
		return ownerPhone;
	}

	public void setLikeCounter(Integer likeCounter){
		this.likeCounter = likeCounter;
	}

	public Integer getLikeCounter(){
		return likeCounter;
	}

	public void setRestDeliveryAvailable(String restDeliveryAvailable){
		this.restDeliveryAvailable = restDeliveryAvailable;
	}

	public String getRestDeliveryAvailable(){
		return restDeliveryAvailable;
	}

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setProfilePicture(String profilePicture){
		this.profilePicture = profilePicture;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public void setFavaouriteCounter(Integer favaouriteCounter){
		this.favaouriteCounter = favaouriteCounter;
	}

	public Integer getFavaouriteCounter(){
		return favaouriteCounter;
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

	public void setMobileVerified(String mobileVerified){
		this.mobileVerified = mobileVerified;
	}

	public String getMobileVerified(){
		return mobileVerified;
	}

	public void setPhone(Phone phone){
		this.phone = phone;
	}

	public Phone getPhone(){
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

	public void setCuisineCategory(CuisineCategory cuisineCategory){
		this.cuisineCategory = cuisineCategory;
	}

	public CuisineCategory getCuisineCategory(){
		return cuisineCategory;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setCoverImage(String coverImage){
		this.coverImage = coverImage;
	}

	public String getCoverImage(){
		return coverImage;
	}

	public void setRestReservationAvailable(String restReservationAvailable){
		this.restReservationAvailable = restReservationAvailable;
	}

	public String getRestReservationAvailable(){
		return restReservationAvailable;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Boolean getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}

	public List<OpeningHoursItem> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(List<OpeningHoursItem> openingHours) {
		this.openingHours = openingHours;
	}

	public Double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(Double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}