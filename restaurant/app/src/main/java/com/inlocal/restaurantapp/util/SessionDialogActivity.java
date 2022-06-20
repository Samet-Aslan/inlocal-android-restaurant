package com.inlocal.restaurantapp.util;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;

import com.inlocal.restaurantapp.R;
import com.inlocal.restaurantapp.databinding.ActivityTestDialogBinding;
import com.inlocal.restaurantapp.ui.auth.login.view.LoginActivity;


public class SessionDialogActivity extends AppCompatActivity {

    private TextView mTextViewOk;
    private SharedPreferences mSharedPreference;
    private ActivityTestDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_dialog);
        switch (getIntent().getIntExtra("code", 0)) {
            case 401:
                binding.tvTitle.setText(getString(R.string.str_session_expired));
                binding.tvPopupLine.setText(getString(R.string.str_session_message));
                break;
            /*case 504:
                binding.tvTitle.setText(getString(R.string.alert));
                binding.tvPopupLine.setText(getString(R.string.str_account_suspended));
                break;
            case 505:
                binding.tvTitle.setText(getString(R.string.alert));
                binding.tvPopupLine.setText(getString(R.string.str_account_deleted));
                break;*/
        }
        SharedPrefUtils.clearAll(this);
        this.setFinishOnTouchOutside(false);
        mTextViewOk = findViewById(R.id.tvOK);
        mTextViewOk.setOnClickListener(v -> NavUtil.ForActivity.navTo(SessionDialogActivity.this, LoginActivity.class, true, null));

    }

    @Override
    public void onBackPressed() {

    }
}
