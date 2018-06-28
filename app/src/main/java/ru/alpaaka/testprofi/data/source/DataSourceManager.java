package ru.alpaaka.testprofi.data.source;

public class DataSourceManager {

    private IDataSource dataSource;

    public DataSourceManager(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public IDataSource getDataSource() {
        return dataSource;
    }
}
