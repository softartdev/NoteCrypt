package com.softartdev.notecrypt.ui.settings.security;

import android.text.TextUtils;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.db.DbStore;

import javax.inject.Inject;

public class SecurityPresenter {
    private SecurityView mView;

    @Inject
    DbStore dbStore;

    SecurityPresenter(SecurityView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    boolean isEncryption() {
        return dbStore.isEncryption();
    }

    private boolean checkPass(String pass) {
        return dbStore.checkPass(pass);
    }

    private void changePassword(String odlPass, String newPass) {
        dbStore.changePass(odlPass, newPass);
    }

    void changeEncryption(boolean checked) {
        if (checked) {
            mView.showSetPasswordDialog();
        } else {
            if (isEncryption()) {
                mView.showPasswordDialog();
            } else {
                mView.showEncryptEnable(false);
            }
        }
    }

    void changePassword() {
        if (isEncryption()) {
            mView.showChangePasswordDialog();
        } else {
            mView.showSetPasswordDialog();
        }
    }

    // only to disable encryption
    boolean enterPassCorrect(SecurityView.DialogDirector pass) {
        pass.hideError();

        String password = pass.getTextString();
        if (TextUtils.isEmpty(password)) {
            pass.showEmptyPasswordError();
            mView.showEncryptEnable(true);
        } else if (checkPass(password)) {
            changePassword(password, null);
            mView.showEncryptEnable(false);
            return true;
        } else {
            pass.showIncorrectPasswordError();
            mView.showEncryptEnable(true);
        }

        return false;
    }

    // only to enable encryption
    boolean setPassCorrect(SecurityView.DialogDirector pass, SecurityView.DialogDirector repeatPass) {
        pass.hideError();
        repeatPass.hideError();

        String password = pass.getTextString();
        String repeatPassword = repeatPass.getTextString();
        if (password.equals(repeatPassword)) {
            if (TextUtils.isEmpty(password)) {
                pass.showEmptyPasswordError();
            } else {
                changePassword(null, password);
                mView.showEncryptEnable(true);
                return true;
            }
        } else {
            repeatPass.showPasswordsNoMatchError();
        }

        return false;
    }

    // only when encryption is enabled
    boolean changePassCorrect(SecurityView.DialogDirector oldPass, SecurityView.DialogDirector newPass, SecurityView.DialogDirector repeatNewPass) {
        oldPass.hideError();
        newPass.hideError();
        repeatNewPass.hideError();

        String oldPassword = oldPass.getTextString();
        String newPassword = newPass.getTextString();
        String repeatNewPassword = repeatNewPass.getTextString();
        if (TextUtils.isEmpty(oldPassword)) {
            oldPass.showEmptyPasswordError();
        } else if (checkPass(oldPassword)) {
            if (TextUtils.isEmpty(newPassword)) {
                newPass.showEmptyPasswordError();
            } else if (newPassword.equals(repeatNewPassword)) {
                changePassword(oldPassword, newPassword);
                return true;
            } else {
                repeatNewPass.showPasswordsNoMatchError();
            }
        } else {
            oldPass.showIncorrectPasswordError();
        }

        return false;
    }
}
