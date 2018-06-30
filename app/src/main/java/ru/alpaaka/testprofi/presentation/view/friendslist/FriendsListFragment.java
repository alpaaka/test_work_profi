package ru.alpaaka.testprofi.presentation.view.friendslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsContract;

public class FriendsListFragment extends Fragment implements FriendsContract.View{

    private FriendsContract.Presenter presenter;

    public static FriendsListFragment newInstance() {
        Bundle args = new Bundle();
        FriendsListFragment fragment = new FriendsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onStop() {
        presenter.dropView();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);
        return view;
    }

    @Override
    public void setPresenter(FriendsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
