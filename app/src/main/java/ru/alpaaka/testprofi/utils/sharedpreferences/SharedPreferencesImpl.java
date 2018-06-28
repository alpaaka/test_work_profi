package ru.alpaaka.testprofi.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Реализация доступа к shared preferences для сохранения токена доступа
 */
public class SharedPreferencesImpl implements ISharedPreferences {

    private static final String TOKEN = "ru.alpaaka.testprofi.token";

    private SharedPreferences prefs;

    public SharedPreferencesImpl(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    @Override
    public void removeToken() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN, null);
        editor.apply();
    }

    @Override
    public String getToken() {
        return prefs.getString(TOKEN, "");
    }
}
