package ru.alpaaka.testprofi.presentation.presenter.friendslist;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

import ru.alpaaka.testprofi.BasePresenter;
import ru.alpaaka.testprofi.BaseView;

public class FriendsContract {

    public interface View extends BaseView<Presenter>{
        void showProgress(boolean progress);
        void displayResult(ArrayList<VKApiUser> list);
        void displayError(int code);
    }

    public interface Presenter extends BasePresenter<View>{
        void loadMore();
        void init();
    }
}
