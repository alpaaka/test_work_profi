package ru.alpaaka.testprofi.data.source;

import ru.alpaaka.testprofi.utils.sharedpreferences.ISharedPreferences;

public class DataSourceImpl implements IDataSource{

    private ISharedPreferences sharedPreferences;

    public DataSourceImpl(ISharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
