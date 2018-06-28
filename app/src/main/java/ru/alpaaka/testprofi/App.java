package ru.alpaaka.testprofi;

import android.app.Application;

import com.vk.sdk.VKSdk;

import ru.alpaaka.testprofi.data.source.DataSourceImpl;
import ru.alpaaka.testprofi.data.source.DataSourceManager;

public class App extends Application {

    private DataSourceManager dataSourceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dataSourceManager = new DataSourceManager(new DataSourceImpl());
        VKSdk.initialize(this);
    }

    public DataSourceManager getDataSourceManager() {
        return dataSourceManager;
    }
}
