package ru.alpaaka.testprofi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.alpaaka.testprofi.R;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CONTAINER = R.id.container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!VKSdk.onActivityResult(requestCode,
                resultCode,
                data,
                new VKCallback<VKAccessToken>() {
                    @Override
                    public void onResult(VKAccessToken res) {
                        showFriendsList();
                    }

                    @Override
                    public void onError(VKError error) {
                        showInfoMessage(error);
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Запрос окна авторизации через VKSDK, с доступом к списку друзей
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                VKSdk.login(this, "friends");
                break;
        }
    }

    private void showInfoMessage(VKError error) {
        switch (error.errorCode) {
            default:
            case VKError.VK_API_ERROR:
            case VKError.VK_JSON_FAILED:
            case VKError.VK_REQUEST_NOT_PREPARED:
            case VKError.VK_REQUEST_HTTP_FAILED:
                generateSnackbar(R.string.internal_error);
                break;
            case VKError.VK_CANCELED:
                break;

        }

    }

    //Отображение snackbar с ошибкой
    private void generateSnackbar(int message) {
        Snackbar.make(findViewById(CONTAINER), message, Snackbar.LENGTH_SHORT)
                .show();
    }


    //Если токен получен
    private void showFriendsList() {
        Intent intent = new Intent(AuthActivity.this, FriendsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
