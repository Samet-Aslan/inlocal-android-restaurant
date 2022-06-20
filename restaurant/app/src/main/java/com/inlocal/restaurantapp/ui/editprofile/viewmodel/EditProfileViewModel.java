package com.inlocal.restaurantapp.ui.editprofile.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.addaddress.model.AddressData;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.editprofile.model.ProfileUpdateRequestModel;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.Data;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.CusisineListResponse;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileViewModel extends BaseViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> name = new MutableLiveData<>("");
    public MutableLiveData<String> phone = new MutableLiveData<>("");
    public MutableLiveData<String> category = new MutableLiveData<>("");
    public MutableLiveData<String> deliveryCharges = new MutableLiveData<>("");
    public MutableLiveData<String> description = new MutableLiveData<>("");
    public MutableLiveData<String> deliveryNote = new MutableLiveData<>("");
    public MutableLiveData<String> profileUpdateResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<List<CateListModel>> cuisineList = new MutableLiveData<>();
    public MutableLiveData<Data> restaurantProfileDetailsResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> cbReservation = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> cbDelivery = new MutableLiveData<>(false);
    public MutableLiveData<String> imageUploadResponse = new MutableLiveData<>();
    public MutableLiveData<String> isDeliverable = new MutableLiveData<>("0");
    public MutableLiveData<String> isEatInside = new MutableLiveData<>("0");
    public MutableLiveData<String> address = new MutableLiveData<>("");
    public MutableLiveData<AddressData> addressData = new MutableLiveData<>();

    @Inject
    public EditProfileViewModel() {
    }

    public void getProfileDetails() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getProfileDetails(), new NetworkCallback<Data>() {
            @Override
            public void onSuccessResponse(Data response) {
                isProgressEnabled.setValue(new Event<>(false));
                restaurantProfileDetailsResponseMutableLiveData.setValue(response);
                isDeliverable.setValue(restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails().getRestDeliveryAvailable());
                isEatInside.setValue(restaurantProfileDetailsResponseMutableLiveData.getValue().getRestaurantDetails().getRestReservationAvailable());
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });
    }

    public void setIsDeliverable(CompoundButton button, boolean isChecked) {
        Log.d("deliverable", "onCheckedChange: " + isChecked);
        isDeliverable.setValue(isChecked ? "1" : "0");
    }

    public void setIsDeliverable() {
       // Log.d("deliverable", "onCheckedChange: " + isChecked);
        isDeliverable.setValue(cbDelivery.getValue() ? "1" : "0");
    }

    public void setIsEatInside(CompoundButton button, boolean isChecked) {
        Log.d("inside", "onCheckedChange: " + isChecked);
        isEatInside.setValue(isChecked ? "1" : "0");
    }

    public void setIsEatInside() {
        isEatInside.setValue(cbReservation.getValue() ? "1" : "0");
    }

    public void updateProfile(View v, ProfileUpdateRequestModel profileUpdateRequestModel) {

        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateProfile(profileUpdateRequestModel), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                profileUpdateResponse.setValue(null);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                profileUpdateResponse.setValue(message);
            }
        });
    }

    public void getAddressDetailsResponse() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getRestaurantAddress(), new NetworkCallback<AddressData>() {
            @Override
            public void onSuccessResponse(AddressData response) {
                isProgressEnabled.setValue(new Event<>(false));
                addressData.setValue(response);
                address.setValue(response.getAddress() + ", " +
                        (response.getLandmark() != null ? response.getLandmark() + ", " : "") +
                        (response.getCity() != null ? response.getCity() + ", " : "") +
                        (response.getCountryCode() != null ? response.getCountryCode() + ", " : "") +
                        (response.getZipcode() != null ? response.getZipcode() : ""));
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });

    }


    public void callCuisineListing(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getCuisineList(getListRequest(page)), new NetworkCallback<CusisineListResponse>() {
            @Override
            public void onSuccessResponse(CusisineListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                cuisineList.setValue(response.getCateListing());
//                cateListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });
    }

    private RequestList getListRequest(int page) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
    }

    public void uploadFileImage(View v, File profileFile, File coverFile) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (profileFile == null && coverFile == null) {
            return;
        }

        uploadFile(profileFile, coverFile);
    }

    private void uploadFile(File profileFile, File coverFile) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart = null, coverPart = null;
        if (profileFile != null) {
            filePart = MultipartBody.Part.createFormData("profile_image", profileFile.getName(), RequestBody.create(MediaType.parse("image/*"), profileFile));
        }
        if (coverFile != null) {
            coverPart = MultipartBody.Part.createFormData("cover_image", coverFile.getName(), RequestBody.create(MediaType.parse("image/*"), coverFile));
        }


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.updateProfileImage(filePart, coverPart), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                imageUploadResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }

}
