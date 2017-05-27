package com.softartdev.notecrypt.ui.settings.security;

import android.content.SharedPreferences;

import com.softartdev.notecrypt.App;

import javax.inject.Inject;

public class SecurityPresenter {
    private SecurityView mView;
    public static final String ENCRYPTION = "encryption";
    public static final String PASSWORD = "password";

    @Inject
    SharedPreferences sharedPreferences;

    SecurityPresenter(SecurityView view) {
        App.createDbComponent(null).inject(this);
        mView = view;
    }

    boolean isEncryption() {
        return sharedPreferences.getBoolean(ENCRYPTION, false);
    }

    private void setEncryption(boolean encryption) {
        sharedPreferences.edit().putBoolean(ENCRYPTION, encryption).apply();
    }

    private String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    private void setPassword(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();
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

        if (pass.getTextString().length() == 0) {
            pass.showEmptyPasswordError();
            mView.showEncryptEnable(true);
        } else if (pass.getTextString().equals(getPassword())) {
            setEncryption(false);
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

        if (pass.getTextString().equals(repeatPass.getTextString())) {
            if (pass.getTextString().length() == 0) {
                pass.showEmptyPasswordError();
            } else {
                setPassword(pass.getTextString());
                setEncryption(true);
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

        if (oldPass.getTextString().length() == 0) {
            oldPass.showEmptyPasswordError();
        } else if (oldPass.getTextString().equals(getPassword())) {
            if (newPass.getTextString().length() == 0) {
                newPass.showEmptyPasswordError();
            } else if (newPass.getTextString().equals(repeatNewPass.getTextString())) {
                setPassword(newPass.getTextString());
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
