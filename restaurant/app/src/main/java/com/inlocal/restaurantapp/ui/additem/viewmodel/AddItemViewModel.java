package com.inlocal.restaurantapp.ui.additem.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.commonmodel.RequestById;
import com.inlocal.restaurantapp.commonmodel.RequestList;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeList;
import com.inlocal.restaurantapp.ui.additem.model.RequestAddItem;
import com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse.MenuDetailsResponse;
import com.inlocal.restaurantapp.ui.auth.login.model.LoginResponseModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListModel;
import com.inlocal.restaurantapp.ui.categorylist.model.CateListResponse;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;
import com.inlocal.restaurantapp.util.KeyboardUtil;
import com.inlocal.restaurantapp.util.ValidationUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddItemViewModel extends BaseViewModel {

    public MutableLiveData<CateListResponse> cateListResponse = new MutableLiveData<>();
    public MutableLiveData<String> itemName = new MutableLiveData<>("");
    public MutableLiveData<String> itemNameError = new MutableLiveData<>("");
    public MutableLiveData<String> itemPrice = new MutableLiveData<>("");
    public MutableLiveData<String> itemPriceError = new MutableLiveData<>("");
    public MutableLiveData<String> itemDesc = new MutableLiveData<>("");
    public MutableLiveData<String> itemDescError = new MutableLiveData<>("");
    public MutableLiveData<Integer> isDeliverable = new MutableLiveData<>(0);
    public MutableLiveData<Integer> isEatInside = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> cbEatInside = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> cbDelivery = new MutableLiveData<>(false);
    public MutableLiveData<List<CustomizeList>> customizeList = new MutableLiveData<>();
    public MutableLiveData<CateListModel> selectedCateListModel = new MutableLiveData<>();
    public MutableLiveData<String> addItemResponse = new MutableLiveData<>();
    public MutableLiveData<RequestById> requestId = new MutableLiveData<>();
    public MutableLiveData<MenuDetailsResponse> menuDtailsResponse = new MutableLiveData<>();


    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();

    @Inject
    public AddItemViewModel() {
    }

    public void itemNameChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            itemNameError.setValue("");
        }
    }

    public void setIsDeliverable() {
        // Log.d("deliverable", "onCheckedChange: " + isChecked);
        isDeliverable.setValue(cbDelivery.getValue() ? 1 : 0);
    }

    public void setIsEatInside() {
        isEatInside.setValue(cbEatInside.getValue() ? 1 :0);
    }

    public void itemPriceChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            itemPriceError.setValue("");
        }
    }

    public void itemDescChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            itemDescError.setValue("");
        }
    }

    public void setIsDeliverable(CompoundButton button, boolean isChecked) {
        Log.d("deliverable", "onCheckedChange: " + isChecked);
        isDeliverable.setValue(isChecked ? 1 : 0);
    }

    public void setIsEatInside(CompoundButton button, boolean isChecked) {
        Log.d("inside", "onCheckedChange: " + isChecked);
        isEatInside.setValue(isChecked ? 1 : 0);
    }


    public void addItem(View v, File file, boolean isEdit) {
        KeyboardUtil.hideSoftKeyboard(v);
        /*if (itemImageFile.getValue() == null) {
            itemImageFileError.setValue(v.getContext().getResources().getString(R.string.error_item_file));
            errorFromServer.setValue(itemImageFileError.getValue());
            return;
        }*/
        if (ValidationUtil.isFieldEmpty(itemName.getValue())) {
            itemNameError.setValue(v.getContext().getResources().getString(R.string.error_item_name));
            errorFromServer.setValue(itemNameError.getValue());
            return;
        } else if (ValidationUtil.isFieldEmpty(itemPrice.getValue() + "") || Double.parseDouble(itemPrice.getValue()) < 0) {
            itemPriceError.setValue(v.getContext().getResources().getString(R.string.error_price));
            errorFromServer.setValue(itemPriceError.getValue());
            return;
        } else if (ValidationUtil.isFieldEmpty(itemDesc.getValue())) {
            itemDescError.setValue(v.getContext().getResources().getString(R.string.error_desc));
            errorFromServer.setValue(itemDescError.getValue());
            return;
        }

        if (isEdit) {
            callEditItemApi(file);
        } else {
            callAddItemApi(file);
        }

    }

    public void getCateList(int page) {
        callListing(page);
    }

    private void callListing(int page) {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuList(getListRequest(page)), new NetworkCallback<CateListResponse>() {
            @Override
            public void onSuccessResponse(CateListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                cateListResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
            }

        });
    }

    public void callMenuDetails() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeCall(n.getMenuItemDetails(requestId.getValue()), new NetworkCallback<MenuDetailsResponse>() {
            @Override
            public void onSuccessResponse(MenuDetailsResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                menuDtailsResponse.setValue(response);
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                menuDtailsResponse.setValue(null);
            }

        });


    }

    private RequestList getListRequest(int page) {
        return new RequestList(Constants.DEFAULT_PAGE_SIZE * page, Constants.DEFAULT_PAGE_SIZE);
    }


    private void callAddItemApi(File file) {
        isProgressEnabled.setValue(new Event<>(true));

        /*RequestAddItem requestAddItem = new RequestAddItem();
        requestAddItem.setImage(file);


        requestAddItem.setCustomizeList(customizeList.getValue());*/

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), itemName.getValue());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), itemDesc.getValue() + "");
        RequestBody isPrice = RequestBody.create(MediaType.parse("text/plain"), itemPrice.getValue() + "");
        RequestBody cateId = RequestBody.create(MediaType.parse("text/plain"), selectedCateListModel.getValue().getId() + "");
        RequestBody isEatInsideAvailable = RequestBody.create(MediaType.parse("text/plain"), isEatInside.getValue() + "");
        RequestBody isDeliveryAvailable = RequestBody.create(MediaType.parse("text/plain"), isDeliverable.getValue() + "");

        HashMap<String, RequestBody> hashMap = new HashMap<>();
        for (int i = 0; i < customizeList.getValue().size(); i++) {
            hashMap.put("customizeList[" + i + "][title]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getTitle()));
            hashMap.put("customizeList[" + i + "][active]", RequestBody.create(MediaType.parse("text/plain"), "1"));
            for (int j = 0; j < customizeList.getValue().get(i).getCustomizeSubItem().size(); j++) {
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][name]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getName()));
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][price]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getPrice() + ""));
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][active]", RequestBody.create(MediaType.parse("text/plain"), "1"));
            }
        }


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.addItem(filePart, name, desc, isPrice, cateId, isEatInsideAvailable, isDeliveryAvailable, hashMap), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                addItemResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }


    private void callEditItemApi(File file) {
        isProgressEnabled.setValue(new Event<>(true));
        MultipartBody.Part filePart = null;
        if (file != null) {
            filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), menuDtailsResponse.getValue().getMenuItemDetails().getId() + "");
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), itemName.getValue());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), itemDesc.getValue() + "");
        RequestBody isPrice = RequestBody.create(MediaType.parse("text/plain"), itemPrice.getValue() + "");
        RequestBody cateId = RequestBody.create(MediaType.parse("text/plain"), selectedCateListModel.getValue().getId() + "");
        RequestBody isEatInsideAvailable = RequestBody.create(MediaType.parse("text/plain"), isEatInside.getValue() + "");
        RequestBody isDeliveryAvailable = RequestBody.create(MediaType.parse("text/plain"), isDeliverable.getValue() + "");

        HashMap<String, RequestBody> hashMap = new HashMap<>();
        for (int i = 0; i < customizeList.getValue().size(); i++) {
            if (customizeList.getValue().get(i).getId() != null) {
                hashMap.put("customizeList[" + i + "][id]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getId() + ""));
            }
            hashMap.put("customizeList[" + i + "][title]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getTitle()));
            hashMap.put("customizeList[" + i + "][active]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getActive() + ""));
            for (int j = 0; j < customizeList.getValue().get(i).getCustomizeSubItem().size(); j++) {
                if (customizeList.getValue().get(i).getCustomizeSubItem().get(j).getId() != null) {
                    hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][id]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getId() + ""));
                }
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][name]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getName()));
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][price]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getPrice() + ""));
                hashMap.put("customizeList[" + i + "][menuitemsubaddon][" + j + "][active]", RequestBody.create(MediaType.parse("text/plain"), customizeList.getValue().get(i).getCustomizeSubItem().get(j).getActive() + ""));
            }
        }


        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);

        NetworkCall networkCall = new NetworkCall<>();
        networkCall.makeEmptyCall(n.editItem(filePart, id, name, desc, isPrice, cateId, isEatInsideAvailable, isDeliveryAvailable, hashMap), new EmptyNetworkCallback() {

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                Log.e("error", error);
            }

            @Override
            public void onEmptySuccessRespon(String message, int code, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                addItemResponse.setValue(message);
                Log.e("response", message);
            }
        });


    }

    private RequestAddItem getRequestAddItem(File file) {
        //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestAddItem requestAddItem = new RequestAddItem();
        requestAddItem.setImage(file);
        requestAddItem.setName(itemName.getValue());
        requestAddItem.setDescription(itemDesc.getValue());
        requestAddItem.setPrice(Double.parseDouble(itemPrice.getValue()));
        requestAddItem.setDeliveryAvailable(isDeliverable.getValue());
        requestAddItem.setEatInsideAvailable(isEatInside.getValue());
        requestAddItem.setCustomizeList(customizeList.getValue());
        requestAddItem.setCategoryId(selectedCateListModel.getValue().getId());
        return requestAddItem;

        //File file = // initialize file here

    }
}
