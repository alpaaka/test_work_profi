package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.alpaaka.testprofi.data.source.IDataSource;

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private IDataSource dataSource;

    private boolean isFirstLoad = true;
    private int usersCount = 0;
    private int totalCount = 0;

    public FriendsPresenter(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void takeView(FriendsContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void loadFriends() {
        if (usersCount - totalCount > 0 || isFirstLoad) {
            view.showProgress(true);
            dataSource.loadFriends(new IDataSource.OnDataLoadedCallback() {
                @Override
                public void onComplete(VKResponse response) {
                    view.showProgress(false);
                    try {
                        JSONObject resp = (JSONObject) response.json.get("response");
                        usersCount = resp.getInt("count");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    view.displayResult(new ArrayList<VKApiUser>((VKUsersArray) response.parsedModel));
                    totalCount += ((VKUsersArray) response.parsedModel).size();
                }

                @Override
                public void onError(int code) {
                    if (view != null){
                        view.showProgress(false);
                        view.displayError(code);
                    }
                }

            }, totalCount);
            isFirstLoad = false;
        }
    }

}
