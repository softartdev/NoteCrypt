package com.softartdev.notecrypt.ui.settings.security;

import android.content.SharedPreferences;

import com.softartdev.notecrypt.App;

import javax.inject.Inject;

public class SecurityPresenter {
    private SecurityView mView;
    public static final String ENCRYPTION = "encryption";

    @Inject
    SharedPreferences sharedPreferences;

    SecurityPresenter(SecurityView view) {
        App.createDbComponent(null).inject(this);
        mView = view;
    }

    boolean isEncryption() {
        return sharedPreferences.getBoolean(ENCRYPTION, false);
    }

    void setEncryption(boolean encryption) {
        sharedPreferences.edit().putBoolean(ENCRYPTION, encryption).apply();
    }

    void pass() {
        mView.onPass();
    }

    void enterPass(String pass) {

    }

    void setPass(String pass, String repeatPass) {

    }

    void changePass(String oldPass, String newPass, String repeatNewPass) {

    }
}
