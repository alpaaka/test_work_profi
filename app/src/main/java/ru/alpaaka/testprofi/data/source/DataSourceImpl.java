package ru.alpaaka.testprofi.data.source;

import ru.alpaaka.testprofi.data.source.network.IVkDataSource;
import ru.alpaaka.testprofi.data.source.network.VkDataSource;
import ru.alpaaka.testprofi.utils.AppExecutor;

public class DataSourceImpl implements IDataSource {

    private IVkDataSource vkDataSource;

    DataSourceImpl(AppExecutor executor) {
        this.vkDataSource = new VkDataSource(executor);
    }

    @Override
    public void loadFriends(OnDataLoadedCallback callback, int offset) {
        vkDataSource.loadFriends(callback, offset);
    }
}
