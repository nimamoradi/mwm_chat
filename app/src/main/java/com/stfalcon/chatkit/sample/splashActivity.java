package com.stfalcon.chatkit.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stfalcon.chatkit.sample.Controler.LoginActivity;
import com.stfalcon.chatkit.sample.features.demo.def.DefaultDialogsActivity;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = preferences.getBoolean("isLogin", false);
        if (isLogin) {
            Intent login = new Intent(this, test.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            Intent login = new Intent(this, LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }

    }
}
