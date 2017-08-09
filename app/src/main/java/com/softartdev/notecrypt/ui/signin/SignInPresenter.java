package com.softartdev.notecrypt.ui.signin;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.db.DbStore;

import javax.inject.Inject;

public class SignInPresenter {
    private SignInView mView;

    @Inject
    DbStore dbStore;

    SignInPresenter(SignInView signInView) {
        App.getAppComponent().inject(this);
        mView = signInView;
    }

    void signIn(String pass) {
        mView.hideError();
        if (pass.length() == 0) {
            mView.showEmptyPassError();
        } else if (checkPass(pass)) {
            mView.navMain();
        } else {
            mView.showIncorrectPassError();
        }
    }

    private boolean checkPass(String pass) {
        return dbStore.checkPass(pass);
    }
}
