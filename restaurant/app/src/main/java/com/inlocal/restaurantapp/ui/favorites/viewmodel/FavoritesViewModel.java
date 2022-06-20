package com.inlocal.restaurantapp.ui.favorites.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inlocal.restaurantapp.base.BaseViewModel;
import com.inlocal.restaurantapp.service.network.EmptyNetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkCall;
import com.inlocal.restaurantapp.service.network.NetworkCallback;
import com.inlocal.restaurantapp.service.network.NetworkService;
import com.inlocal.restaurantapp.service.network.RetrofitService;
import com.inlocal.restaurantapp.ui.favorites.model.FavouriteListResponse;
import com.inlocal.restaurantapp.ui.favorites.model.RequesFavouritesList;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallListResponse;
import com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model.MenuListResponseModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.Event;

import javax.inject.Inject;

public class FavoritesViewModel extends BaseViewModel {
    public MutableLiveData<Integer> type = new MutableLiveData<>(1);
    public MutableLiveData<Integer> totalFavouritesListCount = new MutableLiveData<>();
    public MutableLiveData<Integer> totalUserFavouritesListCount = new MutableLiveData<>();
    public MutableLiveData<Integer> page = new MutableLiveData<>(0);
    public MutableLiveData<FavouriteListResponse> favouritesResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorFromServer = new MutableLiveData<>();


    @Inject
    public FavoritesViewModel() {
    }

    public void changeType(int value) {
        /*if (type.getValue() == 1) {
            type.setValue(2);
        } else {
            type.setValue(1);
        }*/
        type.setValue(value);
        getFavList();
    }

    public void getFavList() {
        isProgressEnabled.setValue(new Event<>(true));
        NetworkService n = RetrofitService.getInstance().create(NetworkService.class);
        NetworkCall<FavouriteListResponse> networkCall = new NetworkCall<>();
        networkCall.makeCall(n.requestFavouritesList(makeFavListRequest()), new NetworkCallback<FavouriteListResponse>() {
            @Override
            public void onSuccessResponse(FavouriteListResponse response) {
                isProgressEnabled.setValue(new Event<>(false));
                favouritesResponse.setValue(response);
                if (type.getValue() == 1) {
                    totalFavouritesListCount.setValue(response.getTotal());
                } else {
                    totalUserFavouritesListCount.setValue(response.getTotal());
                }
            }

            @Override
            public void onErrorResponse(String error, int errorCode, boolean isNetworkErro) {
                isProgressEnabled.setValue(new Event<>(false));
                errorFromServer.setValue(error);
                favouritesResponse.setValue(null);
            }

        });
    }

    private RequesFavouritesList makeFavListRequest() {
        return new RequesFavouritesList(Constants.DEFAULT_PAGE_SIZE * page.getValue(), Constants.DEFAULT_PAGE_SIZE, type.getValue() == 1 ? "Restaurant" : "Customer", "Restaurant");
    }
}
