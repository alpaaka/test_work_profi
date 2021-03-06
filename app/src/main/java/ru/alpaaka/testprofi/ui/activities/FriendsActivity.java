package ru.alpaaka.testprofi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vk.sdk.api.VKError;

import java.util.Objects;

import ru.alpaaka.testprofi.App;
import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsContract;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsPresenter;
import ru.alpaaka.testprofi.presentation.presenter.image.ImageContract;
import ru.alpaaka.testprofi.presentation.presenter.image.ImagePresenter;
import ru.alpaaka.testprofi.presentation.view.friendslist.FriendsListFragment;
import ru.alpaaka.testprofi.presentation.view.image.ImageFragment;

public class FriendsActivity extends AppCompatActivity
        implements FriendsListFragment.OnFragmentInteractionListener,
        ImageFragment.OnFragmentInteractionListener {

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

    @Override
    public boolean onSupportNavigateUp() {
        fragmentManager.popBackStack();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        super.onBackPressed();
    }

    @Override
    public void displayError(int code) {
        switch (code) {
            case VKError.VK_REQUEST_HTTP_FAILED:
                Snackbar.make(findViewById(CONTAINER),
                        R.string.connection_lost, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.check,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                        startActivity(intent);
                                    }
                                })
                        .show();
                break;
            default:
            case VKError.VK_API_ERROR:
            case VKError.VK_JSON_FAILED:
            case VKError.VK_REQUEST_NOT_PREPARED:
                Snackbar.make(findViewById(CONTAINER),
                        R.string.internal_error, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case VKError.VK_CANCELED:
                break;
        }
    }

    @Override
    public void showImage(int id, View view, String url) {
        ImageContract.Presenter presenter = new ImagePresenter(((App) getApplication())
                .getDataSourceManager().getDataSource());
        FriendsListFragment listFragment =
                (FriendsListFragment) fragmentManager.findFragmentByTag("FriendsListFragment");
        ImageFragment fragment = ImageFragment.newInstance(id, view.getTransitionName(), url);
        addTransition(Objects.requireNonNull(listFragment), fragment);
        fragmentManager.beginTransaction()
                .addSharedElement(view, view.getTransitionName())
                .replace(CONTAINER, fragment, "ImageFragment")
                .addToBackStack("ImageFragment")
                .commit();
        fragment.setPresenter(presenter);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    //Добавление анимации перехода между фрагментами с анимацией передачи view
    private void addTransition(Fragment first, Fragment target) {
        Transition changeTransform = TransitionInflater.from(this).
                inflateTransition(R.transition.image_transition);
        Transition explodeTransform = TransitionInflater.from(this).
                inflateTransition(android.R.transition.explode);
        first.setSharedElementReturnTransition(changeTransform);
        first.setExitTransition(explodeTransform);
        target.setSharedElementEnterTransition(changeTransform);
        target.setEnterTransition(explodeTransform);
    }
}
