package ru.alpaaka.testprofi.data.source;

import com.vk.sdk.api.VKResponse;

public interface IDataSource {

    interface OnDataLoadedCallback {
        void onComplete(VKResponse response);

        void onError(int code);

    }

    void loadFriends(OnDataLoadedCallback callback, int offset);
}
