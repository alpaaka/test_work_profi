package ru.alpaaka.testprofi.data.source;

import ru.alpaaka.testprofi.utils.AppExecutor;

public class DataSourceManager {

    private IDataSource dataSource;
    private AppExecutor appExecutor;

    public DataSourceManager(IDataSource dataSource) {
        this.dataSource = dataSource;
        this.appExecutor = new AppExecutor(new AppExecutor.MainThreadExecutor());
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public AppExecutor getAppExecutor() {
        return appExecutor;
    }
}
