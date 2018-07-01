package ru.alpaaka.testprofi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vk.sdk.VKSdk;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserToken();
    }

    private void checkUserToken(){
        Intent i = null;
        if (VKSdk.isLoggedIn()){
            i = new Intent(SplashActivity.this, FriendsActivity.class);
        } else {
            i = new Intent(SplashActivity.this, AuthActivity.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }
}
