package com.inlocal.restaurantapp.ui.home.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.base.BaseActivity;
import com.inlocal.restaurantapp.databinding.ActivityHomeBinding;
import com.inlocal.restaurantapp.service.network.TokenStore;
import com.inlocal.restaurantapp.ui.auth.splash.view.SplashActivity;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.view.HomeFragment;
import com.inlocal.restaurantapp.ui.imagepicker.view.CameraActivity;
import com.inlocal.restaurantapp.util.Constants;
import com.inlocal.restaurantapp.util.NavUtil;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements HomeFragment.HomeActivityUpdateListener {
    private NavController navController;
    private TokenStore tokenStore;

    @Override
    protected int layoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tokenStore = new TokenStore(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //binding.navView.setItemIconTintList(null);
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_menulist, R.id.navigation_notifications)
                .build();*/
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        if (getIntent().hasExtra(Constants.IntentKey.RedirectionTarget) && getIntent().hasExtra(Constants.IntentKey.RedirectionExtra)) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.IntentKey.RedirectionTarget, getIntent().getStringExtra(Constants.IntentKey.RedirectionTarget));
            bundle.putInt(Constants.IntentKey.RedirectionExtra, getIntent().getIntExtra(Constants.IntentKey.RedirectionExtra, 0));
            navController.setGraph(navController.getGraph(), bundle);
        }


        Log.e("HomeToken", tokenStore.load());
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if (destination.getId() == R.id.navigation_view_story || destination.getId() == R.id.navigation_comment || destination.getId() == R.id.navigation_favorites) {
                    binding.navView.setVisibility(View.GONE);
                } else {
                    binding.navView.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 401) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                NavUtil.ForActivity.navTo(HomeActivity.this, CameraActivity.class, false, null);
            } else {
                Toast.makeText(HomeActivity.this, "Please grant permission from settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNotificationCountUpdate(int count) {
        Log.e("notificationCount", count + "");
    }
}