package com.softartdev.notecrypt.ui.settings.security;

interface SecurityView {
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
