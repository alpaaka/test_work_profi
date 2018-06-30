package ru.alpaaka.testprofi.data.source.network;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.HashMap;

import ru.alpaaka.testprofi.data.source.IDataSource;
import ru.alpaaka.testprofi.utils.AppExecutor;

public class VkDataSource implements IVkDataSource {

    //Количество записей за 1 запрос
    private static final int USERS_COUNT = 20;

    private AppExecutor executor;
    private HashMap<String, Object> friendsParameters;

    public VkDataSource(AppExecutor executor) {
        this.executor = executor;
        this.friendsParameters = new HashMap<>();
        bindBasicParameters();
    }

    @Override
    public void loadFriends(final IDataSource.OnDataLoadedCallback callback, int offset) {
        friendsParameters.put(VKApiConst.OFFSET, offset);
        VKRequest request = VKApi.friends()
                .get(new VKParameters(friendsParameters));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                executor.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(response);
                    }
                });
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    private void bindBasicParameters(){
        friendsParameters.put(VKApiConst.COUNT, USERS_COUNT);
        friendsParameters.put(VKApiConst.FIELDS, "photo_100_orig");
    }
}
