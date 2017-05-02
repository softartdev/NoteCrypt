package com.softartdev.notecrypt.ui.signin;

interface SignInView {
    void navMain();
    void showEmptyPassError();
    void showIncorrectPassError();
    void hideError();
}
