package ru.alpaaka.testprofi.presentation.presenter.image;

import ru.alpaaka.testprofi.BasePresenter;
import ru.alpaaka.testprofi.BaseView;

public class ImageContract {

    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter<View> {

    }
}
