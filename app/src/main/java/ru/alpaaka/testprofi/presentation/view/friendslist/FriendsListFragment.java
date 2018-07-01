package ru.alpaaka.testprofi.presentation.view.friendslist;

import android.content.Context;
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

public class FriendsListFragment extends Fragment implements FriendsContract.View {

    private FriendsContract.Presenter presenter;
    private FriendsRecyclerViewAdapter adapter;

    private OnFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private OnImageClickListener clickListener = new OnImageClickListener() {
        @Override
        public void onImageClick(int id, View view, String url) {
            if (listener != null) {
                listener.showImage(id, view, url);
            }
        }
    };

    private boolean isLoading;

    public static FriendsListFragment newInstance() {
        Bundle args = new Bundle();
        FriendsListFragment fragment = new FriendsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.listener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.init();
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
        recyclerView = view.findViewById(R.id.rv_friends);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        adapter = new FriendsRecyclerViewAdapter(new ArrayList<>(),
                recyclerView.getContext(),
                clickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        presenter.loadMore();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void setPresenter(FriendsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(final boolean progress) {
        this.isLoading = progress;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.showLoading(progress);
            }
        });
    }

    @Override
    public void displayResult(final ArrayList<VKApiUser> list) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.displayResult(list);

            }
        });
    }

    @Override
    public void displayError(int code) {
        listener.displayError(code);
    }

    public interface OnFragmentInteractionListener {
        void displayError(int code);

        void showImage(int id, View view, String url);
    }
}
