package com.inlocal.restaurantapp.app.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.app.di.util.ViewModelKey;
import com.inlocal.restaurantapp.ui.addaddress.viewmodel.AddAddressViewModel;
import com.inlocal.restaurantapp.ui.addcategory.viewmodel.AddCategoryViewModel;
import com.inlocal.restaurantapp.ui.additem.viewmodel.AddItemViewModel;
import com.inlocal.restaurantapp.ui.addpost.viewmodel.AddPostViewModel;
import com.inlocal.restaurantapp.ui.auth.login.viewmodel.LoginViewModel;
import com.inlocal.restaurantapp.ui.bookingdetails.viewmodel.BookingDetailsViewModel;
import com.inlocal.restaurantapp.ui.categorylist.viewmodel.CategoryListViewModel;
import com.inlocal.restaurantapp.ui.changepassword.viewmodel.ChangePswViewModel;
import com.inlocal.restaurantapp.ui.comment.viewmodel.CommentViewModel;
import com.inlocal.restaurantapp.ui.editprofile.viewmodel.EditProfileViewModel;
import com.inlocal.restaurantapp.ui.favorites.viewmodel.FavoritesViewModel;
import com.inlocal.restaurantapp.ui.followers.viewmodel.FollowViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.viewmodel.BookingListViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.viewmodel.HomeViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.viewmodel.MenuListViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.viewmodel.OrderDeliveryViewModel;
import com.inlocal.restaurantapp.ui.homefragments.ui.profile.viewmodel.ProfileViewModel;
import com.inlocal.restaurantapp.ui.menulistpost.viewmodel.MenuListPostViewModel;
import com.inlocal.restaurantapp.ui.myorders.viewmodel.OrderHistoryViewModel;
import com.inlocal.restaurantapp.ui.notifications.viewmodel.NotificationViewModel;
import com.inlocal.restaurantapp.ui.notificationssettings.viewmodel.NotificationSettingsViewModel;
import com.inlocal.restaurantapp.ui.restaurantdetails.view.RestaurantDetailsActivity;
import com.inlocal.restaurantapp.ui.restaurantdetails.viewmodel.RestroDetailsViewModel;
import com.inlocal.restaurantapp.ui.restaurantmenu.viewmodel.RestaurantMenuViewModel;
import com.inlocal.restaurantapp.ui.restaurantmenudetails.viewmodel.RestaurantMenuDetailsViewModel;
import com.inlocal.restaurantapp.ui.restauruntinfo.viewmodel.RestuarantInfoViewModel;
import com.inlocal.restaurantapp.ui.statistics.viewmodel.StaticViewModel;
import com.inlocal.restaurantapp.ui.taggedphotos.viewmodel.TaggedPhotoViewModel;
import com.inlocal.restaurantapp.ui.uploadstory.viewmodel.UploadStoryViewModel;
import com.inlocal.restaurantapp.ui.userdetails.viewmodel.UserDetailsViewModel;
import com.inlocal.restaurantapp.ui.viewstory.model.ViewStoryViewModel;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationSettingsViewModel.class)
    abstract ViewModel bindNotificaitonSettingsViewModel(NotificationSettingsViewModel notificationSettingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MenuListPostViewModel.class)
    abstract ViewModel bingMenuListPostViewMOdel(MenuListPostViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel.class)
    abstract ViewModel bindCategoryListViewModel(CategoryListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddCategoryViewModel.class)
    abstract ViewModel bindAddCategoryModel(AddCategoryViewModel addCategoryModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OrderDeliveryViewModel.class)
    abstract ViewModel bindOrderListViewModel(OrderDeliveryViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MenuListViewModel.class)
    abstract ViewModel bindMenuListViewModel(MenuListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddItemViewModel.class)
    abstract ViewModel bindAddItemViewModel(AddItemViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel.class)
    abstract ViewModel bindCommentViewModel(CommentViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChangePswViewModel.class)
    abstract ViewModel bindChangePswViewModel(ChangePswViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FollowViewModel.class)
    abstract ViewModel bindFollowViewModel(FollowViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddPostViewModel.class)
    abstract ViewModel bindAddPostViewModel(AddPostViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel.class)
    abstract ViewModel bindEditProfileViewModel(EditProfileViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RestuarantInfoViewModel.class)
    abstract ViewModel bindRestuarantInfoViewModel(RestuarantInfoViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantMenuViewModel.class)
    abstract ViewModel bindRestaurantMenuViewModel(RestaurantMenuViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RestroDetailsViewModel.class)
    abstract ViewModel bindRestroDetailsViewModel(RestroDetailsViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UploadStoryViewModel.class)
    abstract ViewModel bindUploadStoryViewModel(UploadStoryViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantMenuDetailsViewModel.class)
    abstract ViewModel bindRestaurantMenuDetailsViewModel(RestaurantMenuDetailsViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddAddressViewModel.class)
    abstract ViewModel bindAddAddressViewModel(AddAddressViewModel addAddressViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    abstract ViewModel bindNotifictionViewModel(NotificationViewModel addNotificationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel addProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BookingDetailsViewModel.class)
    abstract ViewModel bindBookingDetailsViewModel(BookingDetailsViewModel bookingDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StaticViewModel.class)
    abstract ViewModel bindStaticViewModel(StaticViewModel staticViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel.class)
    abstract ViewModel bindUserDetailsViewModel(UserDetailsViewModel userDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewStoryViewModel.class)
    abstract ViewModel bindViewStoryViewModel(ViewStoryViewModel viewStoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OrderHistoryViewModel.class)
    abstract ViewModel bindOrderHistoryViewModel(OrderHistoryViewModel orderHistoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaggedPhotoViewModel.class)
    abstract ViewModel bindTaggedPhotoViewModel(TaggedPhotoViewModel taggedPhotoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BookingListViewModel.class)
    abstract ViewModel bindBookingListViewModel(BookingListViewModel bookingListViewModel);

    @Singleton
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
