package com.softartdev.notecrypt.ui.signin;

import android.content.SharedPreferences;

import com.softartdev.notecrypt.App;

import javax.inject.Inject;

import static com.softartdev.notecrypt.ui.settings.security.SecurityPresenter.PASSWORD;

public class SignInPresenter {
    private SignInView mView;

    @Inject
    SharedPreferences sharedPreferences;

    SignInPresenter(SignInView signInView) {
        App.getAppComponent().inject(this);
        mView = signInView;
    }

    private String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    void signIn(String pass) {
        mView.hideError();
        if (pass.length() == 0) {
            mView.showEmptyPassError();
        } else if (pass.equals(getPassword())) {
            mView.navMain();
        } else {
            mView.showIncorrectPassError();
        }
    }
}
