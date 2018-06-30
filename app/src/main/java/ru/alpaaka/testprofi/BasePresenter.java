package ru.alpaaka.testprofi;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
