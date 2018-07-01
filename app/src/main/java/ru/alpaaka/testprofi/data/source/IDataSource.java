package ru.alpaaka.testprofi.data.source;

import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

public interface IDataSource {

    interface OnDataLoadedCallback {
        void onComplete(ArrayList<VKApiUser> response, int count);

        void onError(int code);

    }

    void loadFriends(OnDataLoadedCallback callback, int offset);
}
