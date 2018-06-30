package ru.alpaaka.testprofi.data.source;

import android.content.Context;

import ru.alpaaka.testprofi.utils.AppExecutor;
import ru.alpaaka.testprofi.utils.sharedpreferences.ISharedPreferences;
import ru.alpaaka.testprofi.utils.sharedpreferences.SharedPreferencesImpl;

public class DataSourceManager {

    private final IDataSource dataSource;
    private final ISharedPreferences sharedPreferences;
    private final AppExecutor appExecutor;

    public DataSourceManager(Context context) {
        this.sharedPreferences = new SharedPreferencesImpl(context);
        this.appExecutor = new AppExecutor(new AppExecutor.MainThreadExecutor());
        this.dataSource = new DataSourceImpl(appExecutor);
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public AppExecutor getAppExecutor() {
        return appExecutor;
    }

    public ISharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
