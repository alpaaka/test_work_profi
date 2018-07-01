package ru.alpaaka.testprofi.presentation.view.image;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.alpaaka.testprofi.presentation.presenter.image.ImageContract;

public class ImageFragment extends Fragment implements ImageContract.View {

    private static final String ID = "id";

    private ImageContract.Presenter presenter;

    public static ImageFragment newInstance(int id) {
        Bundle args = new Bundle();
        ImageFragment fragment = new ImageFragment();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void setPresenter(ImageContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
