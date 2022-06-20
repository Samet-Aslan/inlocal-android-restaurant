package com.inlocal.restaurantapp.ui.addpost.viewmodel;

import android.util.Log;
import android.view.View;

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
import com.inlocal.restaurantapp.util.ValidationUtil;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPostViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> descError = new MutableLiveData<>(false);
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();
    public MutableLiveData<String> restaurantId = new MutableLiveData<>("");
    public MutableLiveData<String> postId = new MutableLiveData<>("");
    public MutableLiveData<String> storyId = new MutableLiveData<>("");
    public MutableLiveData<MenuItem> menuItem = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>("");
    public MutableLiveData<String> postUserType = new MutableLiveData<>("Restaurant");
    public MutableLiveData<String> createPostResponse = new MutableLiveData<>();
    public MutableLiveData<String> fileError = new MutableLiveData<>();
    public MutableLiveData<String> messageError = new MutableLiveData<>();
    public MutableLiveData<String> menuError = new MutableLiveData<>();

    @Inject
    public AddPostViewModel() {
    }

    public void descChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            descError.setValue(true);
        } else {
            message.setValue(" ");
            descError.setValue(false);
        }
    }

    public void btnCreatePost(View v, File file, boolean isUpdate) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (isUpdate) {
            editPost(file);
        } else {
            if (file == null) {
                fileError.setValue(v.getContext().getResources().getString(R.string.error_item_file));
                errorFromServer.setValue(fileError.getValue());
                return;
            } /*else if (ValidationUtil.isFieldEmpty(message.getValue() + "")) {
            messageError.setValue(v.getContext().getResources().getString(R.string.enter_description));
            errorFromServer.setValue(messageError.getValue());
            return;
        }  else if (menuItem.getValue() == null) {
            menuError.setValue(v.getContext().getResources().getString(R.string.please_select_menu));
            errorFromServer.setValue(menuError.getValue());
            return;
        }*/
            createPost(file);
        }

    }

    private void createPost(File file) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody menu_item_id = null;
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }
        RequestBody messe = RequestBody.create(MediaType.parse("text/plain"), message.getValue() + "");
        RequestBody post_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.createPost(filePart, restaurant_id, menu_item_id, messe, post_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createPostResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }

    private void editPost(File file) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart =null;
        RequestBody menu_item_id = null;
        if(file!=null){
            filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }
        RequestBody psotId = RequestBody.create(MediaType.parse("text/plain"), postId.getValue() + "");
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody active = RequestBody.create(MediaType.parse("text/plain"),  "1");
        RequestBody messe = RequestBody.create(MediaType.parse("text/plain"), message.getValue() + "");
        RequestBody post_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.editPost(filePart, psotId, restaurant_id, menu_item_id, messe, active,post_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createPostResponse.setValue(message);
                Log.e("response", message);
            }
        });
    }

    public void btnCreateStory(View v, File file, boolean isUpdate) {
        KeyboardUtil.hideSoftKeyboard(v);
        if (isUpdate) {
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
        /*isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody menu_item_id = null;
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }
        RequestBody messe = RequestBody.create(MediaType.parse("text/plain"), message.getValue() + "");
        RequestBody story_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.createStoryNew(filePart, restaurant_id, menu_item_id, messe, story_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createPostResponse.setValue(message);
                Log.e("response", message);
            }
        });*/
    }

    private void editStory(File file) {
        /*isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart =null;
        RequestBody menu_item_id = null;
        if(file!=null){
            filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        if (menuItem.getValue() != null) {
            menu_item_id = RequestBody.create(MediaType.parse("text/plain"), menuItem.getValue().getId() + "");
        }
        RequestBody psotId = RequestBody.create(MediaType.parse("text/plain"), postId.getValue() + "");
        RequestBody restaurant_id = RequestBody.create(MediaType.parse("text/plain"), restaurantId.getValue() + "");
        RequestBody active = RequestBody.create(MediaType.parse("text/plain"),  "1");
        RequestBody messe = RequestBody.create(MediaType.parse("text/plain"), message.getValue() + "");
        RequestBody post_user_type = RequestBody.create(MediaType.parse("text/plain"), postUserType.getValue() + "");


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.editPost(filePart, psotId, restaurant_id, menu_item_id, messe, active,post_user_type), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                createPostResponse.setValue(message);
                Log.e("response", message);
            }
        });*/
    }
}
