package ru.alpaaka.testprofi.presenters.image;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.alpaaka.testprofi.data.source.DataSourceImpl;
import ru.alpaaka.testprofi.data.source.IDataSource;
import ru.alpaaka.testprofi.presentation.presenter.image.ImageContract;
import ru.alpaaka.testprofi.presentation.presenter.image.ImagePresenter;

public class ImagePresenterTest {

    private static int ID = 1;
    private static String URL = "https://www.google.com/photos/about/static/images/google.svg";

    @Mock
    private DataSourceImpl dataSource;
    @Mock
    private ImageContract.View view;
    @Captor
    private ArgumentCaptor<IDataSource.OnImageLoadedCallback> callback;
    private ImagePresenter presenter;
    @Before
    public void setupImagePresenter(){
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new ImagePresenter( dataSource));
        presenter.takeView(view);
    }

    @Test
    public void loadImageAndSetToView(){
        presenter.loadImage(ID);
        Mockito.verify(dataSource, Mockito.times(1))
                .loadPhoto(callback.capture(), Mockito.eq(ID));
        Mockito.verify(view).showProgress(true);
        callback.getValue().onComplete(URL);
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).showImage(URL);
    }

    @Test
    public void loadImageGetErrorAndSetErrorToView(){
        presenter.loadImage(ID);
        Mockito.verify(dataSource, Mockito.times(1))
                .loadPhoto(callback.capture(), Mockito.eq(ID));
        Mockito.verify(view).showProgress(true);
        callback.getValue().onError(-105);
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).displayError(-105);
    }
}
