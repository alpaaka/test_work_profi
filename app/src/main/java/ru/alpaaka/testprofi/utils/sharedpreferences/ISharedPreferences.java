package ru.alpaaka.testprofi.utils.sharedpreferences;

public interface ISharedPreferences {

    void saveToken(String token);
    void removeToken();
    String getToken();
}
