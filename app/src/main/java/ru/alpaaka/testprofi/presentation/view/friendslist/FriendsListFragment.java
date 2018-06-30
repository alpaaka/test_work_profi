package ru.alpaaka.testprofi.presentation.view.friendslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.model.VKApiUser;

import java.util.ArrayList;

import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.presentation.presenter.friendslist.FriendsContract;
import ru.alpaaka.testprofi.ui.adapters.FriendsRecyclerViewAdapter;

public class FriendsListFragment extends Fragment implements FriendsContract.View{

    private FriendsContract.Presenter presenter;
    private FriendsRecyclerViewAdapter adapter;

    public static FriendsListFragment newInstance() {
        Bundle args = new Bundle();
        FriendsListFragment fragment = new FriendsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadFriends();
    }

    @Override
    public void onStop() {
        presenter.dropView();
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_friends);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        adapter = new FriendsRecyclerViewAdapter(new ArrayList<>(),
                recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void setPresenter(FriendsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(boolean progress) {
        adapter.showLoading(progress);
    }

    @Override
    public void displayResult(ArrayList<VKApiUser> list) {
        adapter.displayResult(list);
    }
}
