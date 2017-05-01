package com.softartdev.notecrypt.ui.settings.security;

interface SecurityView {
    void showEncryptEnable(boolean encryption);
    void onPass();

    interface DialogDirector {
        String getTextString();
        void showIncorrectPasswordError();
        void showEmptyPasswordError();
        void showPasswordsNoMatchError();
        void hideError();
    }
}
