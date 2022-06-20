package com.inlocal.restaurantapp.app.di.module;


import com.inlocal.restaurantapp.ui.addaddress.view.AddAddressActivityBindingModule;
import com.inlocal.restaurantapp.ui.addaddress.view.AddAdressActivity;
import com.inlocal.restaurantapp.ui.addcategory.view.AddCategoryActivity;
import com.inlocal.restaurantapp.ui.addcategory.view.AddCategoryActivityBindingModule;
import com.inlocal.restaurantapp.ui.additem.view.AddItemActivity;
import com.inlocal.restaurantapp.ui.additem.view.AddItemActivityBindingModule;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivity;
import com.inlocal.restaurantapp.ui.addpost.view.AddPostActivityBindingModule;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivityBindingModule;
import com.inlocal.restaurantapp.ui.auth.splash.view.SplashActivity;
import com.inlocal.restaurantapp.ui.auth.splash.view.SplashActivityBindingModule;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivity;
import com.inlocal.restaurantapp.ui.bookingdetails.view.BookingDetailsActivityBindingModule;
import com.inlocal.restaurantapp.ui.categorylist.view.CateListActivity;
import com.inlocal.restaurantapp.ui.categorylist.view.CateListActivityBindingModule;
import com.inlocal.restaurantapp.ui.changepassword.view.ChangePasswordActivity;
import com.inlocal.restaurantapp.ui.changepassword.view.ChangePasswordActivityBindingModule;
import com.inlocal.restaurantapp.ui.comment.view.CommentActivity;
import com.inlocal.restaurantapp.ui.comment.view.CommentActivityBindingModule;
import com.inlocal.restaurantapp.ui.editprofile.view.EditProfileActivity;
import com.inlocal.restaurantapp.ui.editprofile.view.EditProfileActivityBindingModule;
import com.inlocal.restaurantapp.ui.favorites.view.FavoritesActivity;
import com.inlocal.restaurantapp.ui.favorites.view.FavoritesActivityBindingModule;
import com.inlocal.restaurantapp.ui.followers.view.FollowersActivity;
import com.inlocal.restaurantapp.ui.followers.view.FollowersActivityBindingModule;
import com.inlocal.restaurantapp.ui.home.view.HomeActivity;
import com.inlocal.restaurantapp.ui.home.view.HomeActivityBindingModule;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.view.BookingListFragment;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.view.BookingListFragmentBindingModule;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.view.HomeFragment;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.view.HomeFragmentBindingModule;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.MenuListFragment;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.view.MenuListFragmentBindingModule;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.view.OrderDeliveryFragment;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.view.OrderDeliveryFragmentBindingModule;
import com.inlocal.restaurantapp.ui.homefragments.ui.profile.view.ProfileFragment;
import com.inlocal.restaurantapp.ui.homefragments.ui.profile.view.ProfileFragmentBindingModule;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivityBindingModule;
import com.inlocal.restaurantapp.ui.imagepicker.view.ImageChooserActivity;
import com.inlocal.restaurantapp.ui.imagepicker.view.ImageChooserActivityBindingModule;
import com.inlocal.restaurantapp.ui.locationmap.MapAcitivityModule;
import com.inlocal.restaurantapp.ui.locationmap.MapActivity;
import com.inlocal.restaurantapp.ui.menulistpost.view.MenuListActivity;
import com.inlocal.restaurantapp.ui.menulistpost.view.MenuListPostActivityBindingModule;
import com.inlocal.restaurantapp.ui.myorders.view.MyOrdersActivity;
import com.inlocal.restaurantapp.ui.myorders.view.MyOrdersActivityBindingModule;
import com.inlocal.restaurantapp.ui.notifications.view.NotificationsActivity;
import com.inlocal.restaurantapp.ui.notifications.view.NotificationsActivityBindingModule;
import com.inlocal.restaurantapp.ui.notificationssettings.view.NotificationsSettingsActivity;
import com.inlocal.restaurantapp.ui.notificationssettings.view.NotificationsSettingsActivityBindingModule;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.InsightListFragment;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.InsightListFragmentBindingModule;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.PostListFragment;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.PostListFragmentBindingModule;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.RestaurantDetailsActivity;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.RestaurantDetailsActivityBindingModule;
import com.inlocal.restaurantapp.ui.restaurantmenu.view.RestaurantMenuActivity;
import com.inlocal.restaurantapp.ui.restaurantmenu.view.RestaurantMenuActivityBindingModule;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.view.RestaurantMenuDetailsActivity;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.view.RestaurantMenuDetailsActivityBindingModule;
import com.inlocal.restaurantapp.ui.restauruntinfo.view.RestuarantInfoActivity;
import com.inlocal.restaurantapp.ui.restauruntinfo.view.RestuarantInfoBindingModule;
import com.inlocal.restaurantapp.ui.statistics.view.StatisticsActivity;
import com.inlocal.restaurantapp.ui.statistics.view.StatisticsActivityBindingModule;
import com.inlocal.restaurantapp.ui.taggedphotos.view.TaggedPhotosActivity;
import com.inlocal.restaurantapp.ui.taggedphotos.view.TaggedPhotosActivityBindingModule;
import com.inlocal.restaurantapp.ui.uploadstory.view.UploadStoryActivity;
import com.inlocal.restaurantapp.ui.uploadstory.view.UploadStoryActivityBindingModule;
import com.inlocal.restaurantapp.ui.userdetails.view.UserDetailsActivity;
import com.inlocal.restaurantapp.ui.userdetails.view.UserDetailsActivityBindingModule;
import com.inlocal.restaurantapp.ui.viewstory.view.ViewStoryActivity;
import com.inlocal.restaurantapp.ui.viewstory.view.ViewStoryActivityBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {LoginActivityBindingModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {MenuListPostActivityBindingModule.class})
    abstract MenuListActivity bindMenuListActivity();

    @ContributesAndroidInjector(modules = {SplashActivityBindingModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {HomeActivityBindingModule.class})
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = {HomeFragmentBindingModule.class})
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector(modules = {MenuListFragmentBindingModule.class})
    abstract MenuListFragment bindMenuListFragment();

    @ContributesAndroidInjector(modules = {OrderDeliveryFragmentBindingModule.class})
    abstract OrderDeliveryFragment bindOrderListFragment();

    @ContributesAndroidInjector(modules = {ProfileFragmentBindingModule.class})
    abstract ProfileFragment bindProfileFragment();

    @ContributesAndroidInjector(modules = {BookingListFragmentBindingModule.class})
    abstract BookingListFragment bindBookingListFragment();

    @ContributesAndroidInjector(modules = {CommentActivityBindingModule.class})
    abstract CommentActivity bindCommentActivity();

    @ContributesAndroidInjector(modules = {NotificationsSettingsActivityBindingModule.class})
    abstract NotificationsSettingsActivity bindNotificationsSettingsActivity();

    @ContributesAndroidInjector(modules = {AddCategoryActivityBindingModule.class})
    abstract AddCategoryActivity bindAddCategoryActivity();

    @ContributesAndroidInjector(modules = {CateListActivityBindingModule.class})
    abstract CateListActivity bindCateListActivity();

    @ContributesAndroidInjector(modules = {BookingDetailsActivityBindingModule.class})
    abstract BookingDetailsActivity bindBookingDetailsActivity();

    @ContributesAndroidInjector(modules = {MyOrdersActivityBindingModule.class})
    abstract MyOrdersActivity bindMyOrdersActivity();

    @ContributesAndroidInjector(modules = {StatisticsActivityBindingModule.class})
    abstract StatisticsActivity bindStatisticsActivity();

    @ContributesAndroidInjector(modules = {ChangePasswordActivityBindingModule.class})
    abstract ChangePasswordActivity bindChangePasswordActivity();

    @ContributesAndroidInjector(modules = {ViewStoryActivityBindingModule.class})
    abstract ViewStoryActivity bindViewStoryActivity();

    @ContributesAndroidInjector(modules = {NotificationsActivityBindingModule.class})
    abstract NotificationsActivity bindNotificationsActivity();

    @ContributesAndroidInjector(modules = {RestaurantDetailsActivityBindingModule.class})
    abstract RestaurantDetailsActivity bindRestaurantDetailsActivity();

    @ContributesAndroidInjector(modules = {FollowersActivityBindingModule.class})
    abstract FollowersActivity bindFollowersActivity();

    @ContributesAndroidInjector(modules = {CameraActivityBindingModule.class})
    abstract CameraActivity bindCameraActivity();

    @ContributesAndroidInjector(modules = {ImageChooserActivityBindingModule.class})
    abstract ImageChooserActivity bindImageChooserActivity();

    @ContributesAndroidInjector(modules = {AddPostActivityBindingModule.class})
    abstract AddPostActivity bindAddPostActivity();

    @ContributesAndroidInjector(modules = {UserDetailsActivityBindingModule.class})
    abstract UserDetailsActivity bindUserDetailsActivity();

    @ContributesAndroidInjector(modules = {TaggedPhotosActivityBindingModule.class})
    abstract TaggedPhotosActivity bindTaggedPhotosActivity();

    @ContributesAndroidInjector(modules = {FavoritesActivityBindingModule.class})
    abstract FavoritesActivity bindFavoritesActivity();

    @ContributesAndroidInjector(modules = {EditProfileActivityBindingModule.class})
    abstract EditProfileActivity bindEditProfileActivity();

    @ContributesAndroidInjector(modules = {AddItemActivityBindingModule.class})
    abstract AddItemActivity bindAddItemActivity();

    @ContributesAndroidInjector(modules = {RestaurantMenuActivityBindingModule.class})
    abstract RestaurantMenuActivity bindRestaurantMenuActivity();

    @ContributesAndroidInjector(modules = {RestuarantInfoBindingModule.class})
    abstract RestuarantInfoActivity bindRestuarantInfoActivity();

    @ContributesAndroidInjector(modules = {UploadStoryActivityBindingModule.class})
    abstract UploadStoryActivity bindUploadStoryActivity();

    @ContributesAndroidInjector(modules = {RestaurantMenuDetailsActivityBindingModule.class})
    abstract RestaurantMenuDetailsActivity bindRestaurantMenuDetails();


    @ContributesAndroidInjector(modules = {AddAddressActivityBindingModule.class})
    abstract AddAdressActivity bindAddAddressMOdule();


    @ContributesAndroidInjector(modules = {PostListFragmentBindingModule.class})
    abstract PostListFragment bindPostListFragmentBindingModule();


    @ContributesAndroidInjector(modules = {InsightListFragmentBindingModule.class})
    abstract InsightListFragment bindInsightListFragmentBindingModule();

    @ContributesAndroidInjector(modules = {MapAcitivityModule.class})
    abstract MapActivity bindMapMOdule();

}
