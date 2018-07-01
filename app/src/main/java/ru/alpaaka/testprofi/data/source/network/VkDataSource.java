package ru.alpaaka.testprofi.data.source.network;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                        int usersCount = 0;
                        try {
                            JSONObject resp = (JSONObject) response.json.get("response");
                            usersCount = resp.getInt("count");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback
                                .onComplete(new ArrayList<VKApiUser>((VKUsersArray) response.parsedModel)
                                        , usersCount);
                    }
                });
            }

            @Override
            public void onError(final VKError error) {
                executor.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(error.errorCode);
                    }
                });
            }
        });
    }

    @Override
    public void loadPhoto(final IDataSource.OnImageLoadedCallback callback, int id) {
        HashMap<String, Object> usersParameters = new HashMap<>();
        usersParameters.put(VKApiConst.USER_IDS, id);
        usersParameters.put(VKApiConst.FIELDS, "photo_max_orig");
        VKRequest request = VKApi.users()
                .get(new VKParameters(usersParameters));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                executor.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(((VKApiUserFull)
                                ((VKList) response.parsedModel).get(0)).photo_max_orig);
                    }
                });
            }

            @Override
            public void onError(final VKError error) {
                executor.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(error.errorCode);
                    }
                });
            }
        });
    }

    private void bindBasicParameters() {
        friendsParameters.put(VKApiConst.COUNT, USERS_COUNT);
        friendsParameters.put(VKApiConst.FIELDS, "photo_100");
    }
}
