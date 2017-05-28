package com.softartdev.notecrypt.ui.signin;

import com.softartdev.notecrypt.App;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.exceptions.RealmFileException;

public class SignInPresenter {
    private SignInView mView;

    @Inject
    Realm realm;

    SignInPresenter(SignInView signInView) {
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
        boolean check;
        try {
            App.clearDbComponent();
            App.createDbComponent(pass).inject(this);
            check = realm != null;
        } catch (RealmFileException e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }
}
