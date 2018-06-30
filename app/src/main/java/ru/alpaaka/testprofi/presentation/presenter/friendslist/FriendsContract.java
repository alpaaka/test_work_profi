package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import ru.alpaaka.testprofi.BasePresenter;
import ru.alpaaka.testprofi.BaseView;

public class FriendsContract {

    public interface View extends BaseView<Presenter>{

    }

    public interface Presenter extends BasePresenter<View>{

    }
}
