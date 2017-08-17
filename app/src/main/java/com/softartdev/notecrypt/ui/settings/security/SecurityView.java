package com.softartdev.notecrypt.ui.settings.security;

import com.softartdev.notecrypt.ui.base.MvpView;

interface SecurityView extends MvpView {
    void showEncryptEnable(boolean encryption);
    void showPasswordDialog();
    void showSetPasswordDialog();
    void showChangePasswordDialog();

    interface DialogDirector {
        String getTextString();
        void showIncorrectPasswordError();
        void showEmptyPasswordError();
        void showPasswordsNoMatchError();
        void hideError();
    }
}
