package com.inlocal.restaurantapp.ui.restauruntinfo.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseFragment;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;
import com.inlocal.restaurantapp.databinding.ActivityRestauruntInfoBinding;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.Data;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;
import com.inlocal.restaurantapp.ui.editprofile.view.DayAdapter;
import com.inlocal.restaurantapp.ui.restauruntinfo.viewmodel.RestuarantInfoViewModel;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class RestuarantInfoActivity extends BaseFragment<ActivityRestauruntInfoBinding> {

    @Inject
    ViewModelFactory viewModelFactory;
    private RestuarantInfoViewModel viewModel;
    private int currentPage = 0, NUM_PAGES = 0;
    private boolean reverse = false;
    private Data restinf;
    private WorkinDaysAdapter dayAdapter;
    private RestaurantDetails restaurantDetails;
    private List<OpeningHoursItem> openingHoursItem;


    @Override
    protected int layoutRes() {
        return R.layout.activity_restaurunt_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestuarantInfoViewModel.class);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);
        binding.topView.setActivity(getBaseActivity());
        init();

        binding.ivMap.setOnClickListener(v->{
            String strUri = "http://maps.google.com/maps?q=loc:" + restaurantDetails.getLocation().getLatitue() + "," + restaurantDetails.getLocation().getLogitute() ;/*+ " (" + "Label which you want" + ")";*/
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        });
    }

    private void init() {
        if (getArguments() != null && getArguments().containsKey(Constants.IntentData.DATA)) {
            restinf = (Data) getArguments().getSerializable(Constants.IntentData.DATA);
            restaurantDetails = restinf.getRestaurantDetails();
            openingHoursItem = new ArrayList<>();
            for (int i = 0; i <restinf.getOpeningHours().size() ; i++) {
                if(restinf.getOpeningHours().get(i).getIsOpen().equalsIgnoreCase("1")){
                    openingHoursItem.add(restinf.getOpeningHours().get(i));
                }
            }
        } else if (getArguments() != null && getArguments().containsKey(Constants.IntentData.RESTAURUNT_DETAILS)) {
            restaurantDetails = (RestaurantDetails) getArguments().getSerializable(Constants.IntentData.RESTAURUNT_DETAILS);
            openingHoursItem = restaurantDetails.getOpeningHours();
            openingHoursItem = new ArrayList<>();
            for (int i = 0; i <restaurantDetails.getOpeningHours().size() ; i++) {
                if(restaurantDetails.getOpeningHours().get(i).getIsOpen().equalsIgnoreCase("1")){
                    openingHoursItem.add(restaurantDetails.getOpeningHours().get(i));
                }
            }
        }

        binding.topView.txtTitle.setText(restaurantDetails.getName());
        binding.setData(restaurantDetails);
        binding.setData(restaurantDetails);
        dayAdapter = new WorkinDaysAdapter();
        dayAdapter.setList(openingHoursItem);
        binding.rvList.setAdapter(dayAdapter);
        binding.rvList.setNestedScrollingEnabled(false);
        loadSlider();
        String adr = "";
        if(restaurantDetails.getAddress()!=null && !restaurantDetails.getAddress().trim().equalsIgnoreCase("")){
        adr = restaurantDetails.getAddress();
        }
        if(restaurantDetails.getLandmark()!=null && !restaurantDetails.getLandmark().trim().equalsIgnoreCase("")){
            adr = adr+", "+restaurantDetails.getLandmark();
        }

        if(restaurantDetails.getCity()!=null && !restaurantDetails.getCity().trim().equalsIgnoreCase("")){
            adr = adr+", "+restaurantDetails.getCity();
        }

        if(restaurantDetails.getCountryCode()!=null && !restaurantDetails.getCountryCode().trim().equalsIgnoreCase("")){
            adr = adr+", "+restaurantDetails.getCountryCode();
        }

        if(restaurantDetails.getZipcode()!=null && !restaurantDetails.getZipcode().trim().equalsIgnoreCase("")){
            adr = adr+", "+restaurantDetails.getZipcode();
        }
        binding.tvAddress.setText(adr);
        Log.e("image_map","https://maps.googleapis.com/maps/api/staticmap?ccenter=" + restaurantDetails.getLocation().getLatitue() + "," + restaurantDetails.getLocation().getLogitute() + "&zoom=13&size=600x300&maptype=roadmap&markers=color:green%7Clabel:G%7C" + restaurantDetails.getLocation().getLatitue() + "," + restaurantDetails.getLocation().getLogitute() + "&key=" + getResources().getString(R.string.google_map_api_key));
        binding.ivMap.setImageUrl("https://maps.googleapis.com/maps/api/staticmap?ccenter=" + restaurantDetails.getLocation().getLatitue() + "," + restaurantDetails.getLocation().getLogitute() + "&zoom=13&size=600x300&maptype=roadmap&markers=color:green%7Clabel:G%7C" + restaurantDetails.getLocation().getLatitue() + "," + restaurantDetails.getLocation().getLogitute() + "&key=" + getResources().getString(R.string.google_map_api_key));
    }

    private void loadSlider() {
        List<String> imageList = new ArrayList<>();
        imageList.add(restaurantDetails.getCoverImage());
        binding.viewPagerHostel.setAdapter(new SlidingImageAdapter(imageList));
        binding.tabLayout.setupWithViewPager(binding.viewPagerHostel);
        NUM_PAGES = imageList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = currentPage - 1;
                    reverse = true;
                }

                if (currentPage == 0) {
                    //currentPage = currentPage + 1;
                    reverse = false;
                }

                if (reverse)
                    binding.viewPagerHostel.setCurrentItem(currentPage--, true);
                else
                    binding.viewPagerHostel.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);
    }


}