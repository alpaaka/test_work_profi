package ru.alpaaka.testprofi.data.source;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

import ru.alpaaka.testprofi.data.source.network.IVkDataSource;
import ru.alpaaka.testprofi.data.source.network.VkDataSource;
import ru.alpaaka.testprofi.utils.AppExecutor;

public class DataSourceImpl implements IDataSource {

    private IVkDataSource vkDataSource;
    private ArrayList<VKApiUser> usersCache = new ArrayList<>();
    private int usersCount = 0;

    DataSourceImpl(AppExecutor executor) {
        this.vkDataSource = new VkDataSource(executor);
    }

    @Override
    public void loadFriends(final OnDataLoadedCallback callback, int offset) {
        if (offset == 0 && !usersCache.isEmpty()) {
            callback.onComplete(usersCache, usersCount);
        } else {
            vkDataSource.loadFriends(new OnDataLoadedCallback() {
                @Override
                public void onComplete(ArrayList<VKApiUser> response, int count) {
                    usersCache.addAll(response);
                    usersCount = count;
                    callback.onComplete(response, count);
                }

                @Override
                public void onError(int code) {
                    callback.onError(code);
                }
            }, offset);
        }
    }
}
