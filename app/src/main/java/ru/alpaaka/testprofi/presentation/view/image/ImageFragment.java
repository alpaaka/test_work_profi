package ru.alpaaka.testprofi.presentation.view.image;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ru.alpaaka.testprofi.R;
import ru.alpaaka.testprofi.data.source.images.ImageLoader;
import ru.alpaaka.testprofi.presentation.presenter.image.ImageContract;

public class ImageFragment extends Fragment implements ImageContract.View {

    private static final String ID = "id";
    private static final String TRANSITION_NAME = "transition_name";
    private static final String BASIC_PHOTO = "basic_photo";
    private ImageContract.Presenter presenter;
    private int id;
    private String transitionName;
    private String basicPhoto;

    private ProgressBar progressBar;
    private ImageView photoView;
    private OnFragmentInteractionListener listener;

    public static ImageFragment newInstance(int id, String transitionName, String basicPhoto) {
        Bundle args = new Bundle();
        ImageFragment fragment = new ImageFragment();
        args.putInt(ID, id);
        args.putString(TRANSITION_NAME, transitionName);
        args.putString(BASIC_PHOTO, basicPhoto);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ID);
            transitionName = getArguments().getString(TRANSITION_NAME);
            basicPhoto = getArguments().getString(BASIC_PHOTO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadImage(id);
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
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        progressBar = view.findViewById(R.id.progress);
        photoView = view.findViewById(R.id.iv_photo);
        ViewCompat.setTransitionName(photoView, transitionName);
        ImageLoader.getInstance().bind(photoView, basicPhoto);
        return view;
    }

    @Override
    public void setPresenter(ImageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void displayError(int code) {
        listener.displayError(code);
    }

    @Override
    public void showImage(String url) {
        ImageLoader.getInstance().bind(photoView, url);
    }

    public interface OnFragmentInteractionListener {
        void displayError(int code);
    }
}
