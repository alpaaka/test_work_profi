package ru.alpaaka.testprofi.presentation.presenter.image;

import ru.alpaaka.testprofi.data.source.IDataSource;

public class ImagePresenter implements ImageContract.Presenter {

    private ImageContract.View view;
    private IDataSource dataSource;

    public ImagePresenter(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void takeView(ImageContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
