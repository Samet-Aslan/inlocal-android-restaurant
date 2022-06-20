package com.inlocal.restaurantapp.service.network;

import com.inlocal.restaurantapp.base.BaseEmptyResponse;
import com.inlocal.restaurantapp.base.BaseResponse;
import com.inlocal.restaurantapp.commonmodel.GlobalConfig;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.commonmodel.NotificationSettings;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.ui.addaddress.model.AddressData;
import com.inlocal.restaurantapp.ui.addaddress.model.updateAddress.UpdateAddressRequest;
import com.inlocal.restaurantapp.ui.addcategory.model.RequestAddCategory;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginRequestModel;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestOrderDetails;
import com.inlocal.restaurantapp.ui.bookingdetails.model.RequestUpdateOrderStatus;
import com.inlocal.restaurantapp.ui.bookingdetails.model.ReuqestItemStatus;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListResponse;
import com.inlocal.restaurantapp.ui.changepassword.model.ChangePasswordRequestModel;
import com.inlocal.restaurantapp.ui.comment.model.CommentData;
import com.inlocal.restaurantapp.ui.comment.model.CommentListRequest;
import com.inlocal.restaurantapp.ui.comment.model.CommentRequestBody;
import com.inlocal.restaurantapp.ui.comment.model.RequestDeletePost;
import com.inlocal.restaurantapp.ui.comment.model.RequestPostDetails;
import com.inlocal.restaurantapp.ui.comment.model.RequestReport;
import com.inlocal.restaurantapp.ui.comment.model.ResponsePostDetails;
import com.inlocal.restaurantapp.ui.editprofile.model.ProfileUpdateRequestModel;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.Data;
import com.inlocal.restaurantapp.ui.favorites.model.FavouriteListResponse;
import com.inlocal.restaurantapp.ui.favorites.model.RequesFavouritesList;
import com.inlocal.restaurantapp.ui.followers.model.FollowersListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.FollowingListingResponse;
import com.inlocal.restaurantapp.ui.followers.model.RequestFollowersList;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.RequestResrvationStatusUpdate;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model.ReservationListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestFavourites;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.RequestLike;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.ResponseCustomerDetails;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.StoryListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.CusisineListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.DeliveryOrderItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.RequestOrderList;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model.ResponseDeliveryOrder;
import com.inlocal.restaurantapp.ui.myorders.model.ExportResponse;
import com.inlocal.restaurantapp.ui.myorders.model.OrderHistoryResponse;
import com.inlocal.restaurantapp.ui.myorders.model.RequestExportFile;
import com.inlocal.restaurantapp.ui.notifications.model.NotificationListResponse;
import com.inlocal.restaurantapp.ui.notifications.model.RequestReadNotification;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.MyPostResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.MyStoryListResponse;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestBlockUser;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestFollowe;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestInsightListing;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMenuDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestMyPostList;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestOthersFollowersLIst;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.RequestRestauruntDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ResponseOthersFollowersList;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ResponseRestauruntDetails;
import com.inlocal.restaurantapp.ui.restaurantdetails.model.ReuqestInsightList;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.model.RequestMenuPostList;
import com.inlocal.restaurantapp.ui.statistics.model.RequestStatistics;
import com.inlocal.restaurantapp.ui.statistics.model.ResponseBestSeller;
import com.inlocal.restaurantapp.ui.statistics.model.ResponseStatic;
import com.inlocal.restaurantapp.ui.taggedphotos.model.ResponseTopPostList;
import com.inlocal.restaurantapp.ui.userdetails.model.CustomerPostListResponse;
import com.inlocal.restaurantapp.ui.userdetails.model.ReuqestCustomerPostList;
import com.inlocal.restaurantapp.ui.viewstory.viewmodel.RequestDeleteStory;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface NetworkService {
    @POST("restaurant/login")
    Call<BaseResponse<LoginResponseModel>> doLogin(@Body LoginRequestModel request);

    @POST("restaurant/menucategory/add")
    Call<BaseEmptyResponse> addMenuCategory(@Body RequestAddCategory request);

    @POST("restaurant/menucategory/edit")
    Call<BaseEmptyResponse> editMenuCategory(@Body CateListModel request);

    @POST("restaurant/menucategory/delete")
    Call<BaseEmptyResponse> deleteMenuCategory(@Body CateListModel request);

    @POST("restaurant/menucategory/list")
    Call<BaseResponse<CateListResponse>> getMenuList(@Body RequestList requestList);

    @POST("menu_category/list")
    Call<BaseResponse<CateListResponse>> getRestMenuList(@Body RequestList requestList);

    @POST("restaurant/menuitems/by_type")
    Call<BaseResponse<MenuListResponseModel>> getMenuListById(@Body RequestList requestList);

    @POST("restaurant/menuitems/list")
    Call<BaseResponse<MenuListResponseModel>> getMenuItemList(@Body RequestList requestList);

    @POST("restaurant/cuisine/list")
    Call<BaseResponse<CusisineListResponse>> getCuisineList(@Body RequestList requestList);

    @POST("restaurant/story/delete")
    Call<BaseEmptyResponse> storyDelete(@Body RequestDeleteStory request);

    @Multipart
    @POST("restaurant/menuitems/add")
    Call<BaseEmptyResponse> addItem(@Part MultipartBody.Part image,
                                    @Part("name") RequestBody name,
                                    @Part("description") RequestBody description,
                                    @Part("price") RequestBody price,
                                    @Part("category_id") RequestBody category_id,
                                    @Part("EatInsideAvailable") RequestBody EatInsideAvailable,
                                    @Part("DeliveryAvailable") RequestBody DeliveryAvailable,
                                    @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("restaurant/menuitems/edit")
    Call<BaseEmptyResponse> editItem(@Part MultipartBody.Part image,
                                     @Part("id") RequestBody id,
                                     @Part("name") RequestBody name,
                                     @Part("description") RequestBody description,
                                     @Part("price") RequestBody price,
                                     @Part("category_id") RequestBody category_id,
                                     @Part("EatInsideAvailable") RequestBody EatInsideAvailable,
                                     @Part("DeliveryAvailable") RequestBody DeliveryAvailable,
                                     @PartMap Map<String, RequestBody> params);

    @POST("restaurant/menuitems/details")
    Call<BaseResponse<MenuDetailsResponse>> getMenuItemDetails(@Body RequestById request);

    @Multipart
    @POST("restaurant/post/create")
    Call<BaseEmptyResponse> createPost(@Part MultipartBody.Part image,
                                       @Part("restaurant_id") RequestBody restaurant_id,
                                       @Part("menu_item_id") RequestBody menu_item_id,
                                       @Part("message") RequestBody message,
                                       @Part("post_user_type") RequestBody post_user_type);

    @Multipart
    @POST("restaurant/post/edit")
    Call<BaseEmptyResponse> editPost(@Part MultipartBody.Part image,
                                     @Part("id") RequestBody post_id,
                                     @Part("restaurant_id") RequestBody restaurant_id,
                                     @Part("menu_item_id") RequestBody menu_item_id,
                                     @Part("message") RequestBody message,
                                     @Part("active") RequestBody active,
                                     @Part("post_user_type") RequestBody post_user_type);

    @Multipart
    @POST("restaurant/story/create")
    Call<BaseEmptyResponse> createStory(@Part MultipartBody.Part image,
                                        @Part("restaurant_id") RequestBody restaurant_id,
                                        @Part("story_user_type") RequestBody post_user_type);

    @Multipart
    @POST("restaurant/story/create")
    Call<BaseEmptyResponse> createStoryNew(@Part MultipartBody.Part image,
                                        @Part("restaurant_id") RequestBody restaurant_id,
                                        @Part("menu_item_id") RequestBody menu_item_id,
                                        @Part("story_user_type") RequestBody post_user_type);

    @Multipart
    @POST("restaurant/story/edit")
    Call<BaseEmptyResponse> editStory(@Part MultipartBody.Part image,
                                     @Part("id") RequestBody post_id,
                                     @Part("restaurant_id") RequestBody restaurant_id,
                                     @Part("menu_item_id") RequestBody menu_item_id,
                                     @Part("active") RequestBody active,
                                     @Part("story_user_type") RequestBody post_user_type);



    @POST("restaurant/menuitems/delete")
    Call<BaseEmptyResponse> deleteMenuItem(@Body MenuItem request);

    @Multipart
    @POST("restaurant/images/update")
    Call<BaseEmptyResponse> updateProfileImage(@Part MultipartBody.Part profileImage,
                                               @Part MultipartBody.Part coverImage);

    @POST("restaurant/changepassword")
    Call<BaseEmptyResponse> changePassWord(@Body ChangePasswordRequestModel changePasswordRequestModel);

   /* @POST("profile/update")
    Call<BaseResponse<>> updateProfile(@Body ProfileUpdateRequestModel profileUpdateRequestModel);*/

    @POST("restaurant/profile/update")
    Call<BaseEmptyResponse> updateProfile(@Body ProfileUpdateRequestModel profileUpdateRequestModel);

    @GET("restaurant/profiledetails")
    Call<BaseResponse<Data>> getProfileDetails();

    @POST("restaurant/mypost/list")
    Call<BaseResponse<MyPostResponse>> getMyPostList(@Body RequestMyPostList requestList);

    @POST("restaurant/post/insight/list")
    Call<BaseResponse<MyPostResponse>> getMyInsightsList(@Body ReuqestInsightList requestList);

    @POST("restaurant/mystory/list")
    Call<BaseResponse<MyStoryListResponse>> getMyStoryList(@Body RequestMyPostList requestList);

    @GET("restaurant/notification/setting")
    Call<BaseResponse<NotificationSettings>> getNotificationSettings();

    @POST("restaurant/notify/setting/update")
    Call<BaseEmptyResponse> updateNotificationSettings(@Body NotificationSettings notificationSettings);

    @POST("restaurant/myfollower/list")
    Call<BaseResponse<FollowersListingResponse>> getFollowersList(@Body RequestFollowersList requestFollowersList);

    @POST("restaurant/myfollowing/list")
    Call<BaseResponse<FollowingListingResponse>> getFollowingList(@Body RequestFollowersList requestFollowersList);

    @POST("restaurant/post/feedwall/list")
    Call<BaseResponse<FeedWallListResponse>> getFeedWalListResponse(@Body RequestFollowersList requestFollowersList);

    @POST("restaurant/story/feedwall/list")
    Call<BaseResponse<StoryListResponse>> getStoryList(@Body RequestFollowersList requestFollowersList);

    @POST("restaurant/details")
    Call<BaseResponse<ResponseRestauruntDetails>> getRestauruntDetails(@Body RequestRestauruntDetails request);

    @POST("restaurant/post/list")
    Call<BaseResponse<MyPostResponse>> getRestauruntPostList(@Body RequestRestauruntDetails requestList);

    @POST("restaurant/post/view/list")
    Call<BaseResponse<FeedWallItem>> getPostViewLis(@Body RequestRestauruntDetails requestList);

    @POST("restaurant/menu/post/view/list")
    Call<BaseResponse<FeedWallItem>> getRestMenuPostViewLis(@Body RequestRestauruntDetails requestList);

    @POST("restaurant/insight/view/list")
    Call<BaseResponse<FeedWallItem>> getInsightViewList(@Body RequestInsightListing requestList);

    @POST("restaurant/story/list")
    Call<BaseResponse<MyStoryListResponse>> getRestauruntStoryList(@Body RequestRestauruntDetails requestList);

    @POST("restaurant/comment/create")
    Call<BaseEmptyResponse> createComment(@Body CommentRequestBody commentRequestBody);

    @POST("restaurant/comment/list")
    Call<BaseResponse<CommentData>> getCommentList(@Body CommentListRequest commentListRequest);

    @POST("restaurant/follower")
    Call<BaseEmptyResponse> requestFollower(@Body RequestFollowe requestFollowe);

    @POST("restaurant/post/like")
    Call<BaseEmptyResponse> requestLike(@Body RequestLike request);

    @POST("restaurant/post/favorite")
    Call<BaseEmptyResponse> requestFavourites(@Body RequestFavourites request);

    @POST("restaurant/post/favorite/list")
    Call<BaseResponse<FavouriteListResponse>> requestFavouritesList(@Body RequesFavouritesList request);

    @GET("restaurant/address/details")
    Call<BaseResponse<AddressData>> getRestaurantAddress();

    @POST("restaurant/address/update")
    Call<BaseEmptyResponse> updateRestaurantAddress(@Body UpdateAddressRequest updateAddressRequest);

    @POST("restaurant/notification/list")
    Call<BaseResponse<NotificationListResponse>> getNotificationList(@Body RequesFavouritesList requestList);

    @POST("restaurant/notification/read")
    Call<BaseEmptyResponse> readNotification(@Body RequestReadNotification request);

    @POST("restaurant/others/followers/list")
    Call<BaseResponse<ResponseOthersFollowersList>> getOthersFollowersList(@Body RequestOthersFollowersLIst requestList);

    @POST("restaurant/menu/details")
    Call<BaseResponse<MenuDetailsResponse>> getMenuDetails(@Body RequestMenuDetails request);

    @POST("restaurant/menuitems/posts")
    Call<BaseResponse<MenuDetailsResponse>> getPostMenuList(@Body RequestMenuPostList request);

    @POST("restaurant/order/list")
    Call<BaseResponse<ResponseDeliveryOrder>> getDeliverOrder(@Body RequestOrderList request);

    @POST("restaurant/order/dinein/list")
    Call<BaseResponse<ResponseDeliveryOrder>> getDineInOrdeList(@Body RequestOrderList request);

    @POST("restaurant/order/details")
    Call<BaseResponse<DeliveryOrderItem>> getDeliverOrderDetails(@Body RequestOrderDetails request);

    @POST("restaurant/orderstatus/update")
    Call<BaseEmptyResponse> updateOrderStatus(@Body RequestUpdateOrderStatus request);

    @POST("restaurant/order/dinein/item_status/update")
    Call<BaseEmptyResponse> updateItemStatus(@Body ReuqestItemStatus request);

    @POST("reservation/status_update")
    Call<BaseEmptyResponse> updateBookingStatus(@Body RequestResrvationStatusUpdate request);

    @POST("restaurant/orders/statistics")
    Call<BaseResponse<ResponseStatic>> getStatics(@Body RequestStatistics request);

    @GET("restaurant/global_config")
    Call<BaseResponse<GlobalConfig>> globalConfig();

    @POST("customer/details")
    Call<BaseResponse<ResponseCustomerDetails>> getCustomerDetails(@Body RequestCustomerDetails postListRequest);

    @POST("customer/post/list")
    Call<BaseResponse<CustomerPostListResponse>> getCustomerPost(@Body ReuqestCustomerPostList postListRequest);


    @GET("restaurant/order/best_seller")
    Call<BaseResponse<ResponseBestSeller>> getBestSeller();

    @POST("restaurant/orders/history")
    Call<BaseResponse<OrderHistoryResponse>> getOrderHistory(@Body RequestOrderList request);

    @POST("reservation_list")
    Call<BaseResponse<ReservationListResponse>> getReservationList(@Body RequestList request);

    @POST("restaurant/order/history/export")
    Call<BaseResponse<ExportResponse>> getExportResponse(@Body RequestExportFile request);

    @POST("restaurant/post/reported")
    Call<BaseEmptyResponse> postReport(@Body RequestReport requestReport);

    @POST("restaurant/post/delete")
    Call<BaseEmptyResponse> deletePost(@Body RequestDeletePost requestDeletePost);

    @POST("restaurant/block")
    Call<BaseEmptyResponse> bloackUser(@Body RequestBlockUser request);

    @POST("restaurant/post/details")
    Call<BaseResponse<ResponsePostDetails>> getPostDetails(@Body RequestPostDetails request);

    @GET("restaurant/logout")
    Call<BaseEmptyResponse> logout();

}
