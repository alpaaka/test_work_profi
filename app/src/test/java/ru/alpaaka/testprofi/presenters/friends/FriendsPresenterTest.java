package ru.alpaaka.testprofi.presenters.friends;

import com.vk.sdk.api.model.VKApiUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import ru.alpaaka.testprofi.data.source.DataSourceImpl;
import ru.alpaaka.testprofi.data.source.IDataSource;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsContract;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsPresenter;

public class FriendsPresenterTest {

    private static ArrayList<VKApiUser> LIST;
    private static int COUNT;

    @Mock
    private DataSourceImpl dataSource;

    @Mock
    private FriendsContract.View view;
    @Captor
    private ArgumentCaptor<IDataSource.OnDataLoadedCallback> callback;

    private FriendsPresenter presenter;

    @Before
    public void setupFriendsPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new FriendsPresenter(dataSource));
        presenter.takeView(view);
        LIST = new ArrayList<>();
        LIST.add(new VKApiUser());
        LIST.add(new VKApiUser());
        LIST.add(new VKApiUser());
        COUNT = LIST.size();
    }

    @Test
    public void loadInitFriendsAndSetToView() {
        presenter.init();
        Mockito.verify(dataSource, Mockito.times(1))
                .loadFriends(callback.capture(), Mockito.eq(0));
        Mockito.verify(view).showProgress(true);
        callback.getValue().onComplete(LIST, LIST.size());
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).displayResult(LIST);
    }

    @Test
    public void loadEmptyFriendsAndSetToView() {
        presenter.init();
        Mockito.verify(dataSource, Mockito.times(1))
                .loadFriends(callback.capture(), Mockito.eq(0));
        Mockito.verify(view).showProgress(true);
        LIST.clear();
        callback.getValue().onComplete(LIST, LIST.size());
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).displayResult(LIST);
    }

    @Test
    public void loadFriendsAndShowError() {
        presenter.init();
        Mockito.verify(dataSource, Mockito.times(1))
                .loadFriends(callback.capture(), Mockito.eq(0));
        Mockito.verify(view).showProgress(true);
        callback.getValue().onError(-105);
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).displayError(-105);
    }

    @Test
    public void loadMoreSameCountAndSetData(){
        presenter.setUsersCount(COUNT);
        presenter.setTotalCount(COUNT);
        presenter.loadMore(); //totalcount == usercount
        Mockito.verify(dataSource, Mockito.times(0))
                .loadFriends(callback.capture(), Mockito.eq(0));
    }

    @Test
    public void loadMoreFriendsAndSetData(){
        presenter.setUsersCount(COUNT + 1);
        presenter.setTotalCount(COUNT);
        presenter.loadMore();
        Mockito.verify(dataSource, Mockito.times(1))
                .loadFriends(callback.capture(), Mockito.eq(LIST.size()));
        Mockito.verify(view).showProgress(true);
        LIST.clear();
        LIST.add(new VKApiUser());
        callback.getValue().onComplete(LIST, COUNT + 1);
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).displayResult(LIST);
    }
}
