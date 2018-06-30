package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import ru.alpaaka.testprofi.BasePresenter;
import ru.alpaaka.testprofi.BaseView;

public class FriendsContract {

    public interface View extends BaseView<Presenter>{
        void showProgress();
    }

    public interface Presenter extends BasePresenter<View>{
        void loadFriends();
    }
}
