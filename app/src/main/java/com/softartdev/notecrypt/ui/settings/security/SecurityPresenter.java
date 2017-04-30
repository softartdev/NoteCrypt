package com.softartdev.notecrypt.ui.settings.security;

class SecurityPresenter {
    private SecurityView mView;

    SecurityPresenter(SecurityView view) {
        mView = view;
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
