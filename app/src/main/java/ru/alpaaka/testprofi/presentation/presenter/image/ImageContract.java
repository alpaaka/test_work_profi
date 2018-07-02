package ru.alpaaka.testprofi.presentation.presenter.image;

import android.widget.ImageView;

import ru.alpaaka.testprofi.BasePresenter;
import ru.alpaaka.testprofi.BaseView;

public class ImageContract {

    public interface View extends BaseView<Presenter> {
        void showProgress(boolean progress);
        void displayError(int code);
        void showImage(String url);
    }

    public interface Presenter extends BasePresenter<View> {
        void loadImage(int id);
    }
}
