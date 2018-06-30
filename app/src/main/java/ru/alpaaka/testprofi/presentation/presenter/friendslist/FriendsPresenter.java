package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import ru.alpaaka.testprofi.data.source.IDataSource;

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View view;
    private IDataSource dataSource;

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
}
