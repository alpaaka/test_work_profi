package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

import ru.alpaaka.testprofi.data.source.IDataSource;

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private IDataSource dataSource;

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
    public void loadMore() {
        if (usersCount - totalCount > 0) {
            view.showProgress(true);
            loadFriends(totalCount);
        }
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public void init() {
        if (view != null) {
            view.showProgress(true);
            totalCount = 0;
        }
        loadFriends(0);
    }

    private void loadFriends(int offset) {
        dataSource.loadFriends(new IDataSource.OnDataLoadedCallback() {
            @Override
            public void onComplete(ArrayList<VKApiUser> response, int count) {
                view.showProgress(false);
                usersCount = count;
                view.displayResult(response);
                totalCount += response.size();
            }

            @Override
            public void onError(int code) {
                if (view != null) {
                    view.showProgress(false);
                    view.displayError(code);
                }
            }

        }, offset);
    }

}
