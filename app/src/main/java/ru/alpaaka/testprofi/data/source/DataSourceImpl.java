package ru.alpaaka.testprofi.data.source;

import android.util.SparseArray;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;
import java.util.HashMap;

import ru.alpaaka.testprofi.data.source.network.IVkDataSource;
import ru.alpaaka.testprofi.data.source.network.VkDataSource;
import ru.alpaaka.testprofi.utils.AppExecutor;

public class DataSourceImpl implements IDataSource {

    private IVkDataSource vkDataSource;
    private ArrayList<VKApiUser> usersCache = new ArrayList<>();
    private int usersCount = 0;
    private SparseArray<String> usersPhoto = new SparseArray<>();

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

    @Override
    public void loadPhoto(final OnImageLoadedCallback callback, final int id) {
        if (usersPhoto.get(id) != null){
            callback.onComplete(usersPhoto.get(id));
        } else {
            vkDataSource.loadPhoto(new OnImageLoadedCallback() {
                @Override
                public void onComplete(String url) {
                    usersPhoto.put(id, url);
                    callback.onComplete(url);
                }

                @Override
                public void onError(int code) {
                    callback.onError(code);
                }
            }, id);
        }
    }
}
