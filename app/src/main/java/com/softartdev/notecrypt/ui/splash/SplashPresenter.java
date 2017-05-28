package com.softartdev.notecrypt.ui.splash;

import android.content.SharedPreferences;

import com.softartdev.notecrypt.App;

import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.exceptions.RealmFileException;

public class SplashPresenter {
    private SplashView mView;

    @Inject
    SharedPreferences sharedPreferences;

    SplashPresenter(SplashView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    private boolean isEncryption() {
        try {
            Realm realm = Realm.getDefaultInstance();
            return realm == null;
        } catch (RealmFileException e) {
            e.printStackTrace();
            return true;
        }
    }

    void checkEncryption() {
        if (isEncryption()) {
            mView.navSignIn();
        } else {
            mView.navMain();
        }
    }

    void setLocale() {
        Locale locale;
        String value = sharedPreferences.getString("language", "2");
        switch (value) {
            case "1":
                locale = Locale.ENGLISH;
                break;
            case "2":
                locale = new Locale("ru");
                break;
            default:
                locale = Locale.getDefault();
                break;
        }
        Locale.setDefault(locale);
        mView.onLocale(locale);
    }
}
