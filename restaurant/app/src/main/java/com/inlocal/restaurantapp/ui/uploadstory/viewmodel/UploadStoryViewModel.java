package com.inlocal.restaurantapp.ui.uploadstory.viewmodel;

import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.MenuItem;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.NetworkUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadStoryViewModel extends BaseViewModel {

    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> restaurantId = new MutableLiveData<>("");
    public MutableLiveData<String> postUserType = new MutableLiveData<>("Restaurant");
    public MutableLiveData<String> createStoryResponse = new MutableLiveData<>();
    public MutableLiveData<String> fileError = new MutableLiveData<>();
    public MutableLiveData<MenuItem> menuItem = new MutableLiveData<>();
    public MutableLiveData<String> storyId = new MutableLiveData<>();

    @Inject
    public UploadStoryViewModel() {
    }

    public void btnCreatePost(View v, File file, boolean isEdit) {
        KeyboardUtil.hideSoftKeyboard(v);

        if (isEdit) {
            editStory(file);
        } else {
            if (file == null) {
                fileError.setValue(v.getContext().getResources().getString(R.string.error_item_file));
                errorFromServer.setValue(fileError.getValue());
                return;
            }

            createStory(file);
        }

    }

    private void createStory(File file) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody post_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");
        RequestBody menu_item_id = null;
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }

        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.createStoryNew(filePart, restaurant_id, menu_item_id,post_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createStoryResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }

    private void editStory(File file) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart =null;
        RequestBody menu_item_id = null;
        if(file!=null){
            filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }
        RequestBody psotId = RequestBody.create(MediaType.parse("text/plain"), storyId.getValue() + "");
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody active = RequestBody.create(MediaType.parse("text/plain"),  "1");
        RequestBody post_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.editStory(filePart, psotId, restaurant_id, menu_item_id,  active,post_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createStoryResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

}