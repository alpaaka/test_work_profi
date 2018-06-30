package ru.alpaaka.testprofi;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import ru.alpaaka.testprofi.ui.activities.AuthActivity;
import ru.alpaaka.testprofi.data.source.DataSourceManager;

public class App extends Application {

    private DataSourceManager dataSourceManager;

    //Обработка невалидного access токена. В случае ошибки запуск активити с формой авторизации
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        dataSourceManager = new DataSourceManager(this);
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    public DataSourceManager getDataSourceManager() {
        return dataSourceManager;
    }
}
