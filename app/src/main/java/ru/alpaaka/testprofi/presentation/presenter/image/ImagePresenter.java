package ru.alpaaka.testprofi.presentation.presenter.image;

import android.widget.ImageView;

import ru.alpaaka.testprofi.data.source.IDataSource;
import ru.alpaaka.testprofi.data.source.images.ImageLoader;

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

    @Override
    public void loadImage(int id, final ImageView imageView) {
        view.showProgress(true);
        dataSource.loadPhoto(new IDataSource.OnImageLoadedCallback() {
            @Override
            public void onComplete(String url) {
                if (view != null){
                    view.showProgress(false);
                    ImageLoader.getInstance().bind(imageView, url);
                }
            }

            @Override
            public void onError(int code) {
                if (view != null){
                    view.showProgress(false);
                    view.displayError(code);
                }
            }
        }, id);
    }
}
