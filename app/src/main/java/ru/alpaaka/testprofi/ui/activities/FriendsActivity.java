package ru.alpaaka.testprofi.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.alpaaka.testprofi.App;
import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsContract;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsPresenter;
import ru.alpaaka.testprofi.presentation.view.friendslist.FriendsListFragment;

public class FriendsActivity extends AppCompatActivity {

    private static final int CONTAINER = R.id.container;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        fragmentManager = getSupportFragmentManager();
        setListFragment();
    }

    private void setListFragment() {
        FriendsContract.Presenter presenter = new FriendsPresenter(((App) getApplication())
                .getDataSourceManager().getDataSource());
        FriendsListFragment fragment = (FriendsListFragment)
                fragmentManager.findFragmentByTag("FriendsListFragment");
        if (fragment == null) {
            fragment = FriendsListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(CONTAINER, fragment, "FriendsListFragment")
                    .commit();
        }
        fragment.setPresenter(presenter);
    }
}
