package com.softartdev.notecrypt.ui.settings.security;

import android.content.SharedPreferences;

import com.softartdev.notecrypt.App;

import javax.inject.Inject;

public class SecurityPresenter {
    private SecurityView mView;
    public static final String ENCRYPTION = "encryption";
    private static final String PASSWORD = "password";

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
        mView.showEncryptEnable(encryption);
        sharedPreferences.edit().putBoolean(ENCRYPTION, encryption).apply();
    }

    private String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    private void setPassword(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    void pass() {
        mView.onPass();
    }

    boolean enterPass(SecurityView.DialogDirector pass) {
        pass.hideError();
        if (pass.getTextString().length() == 0) {
            pass.showEmptyPasswordError();
            return false;
        }
        if (pass.getTextString().equals(getPassword())) {
            setEncryption(false);
            return true;
        } else {
            pass.showIncorrectPasswordError();
            return false;
        }
    }

    boolean setPass(SecurityView.DialogDirector pass, SecurityView.DialogDirector repeatPass) {
        pass.hideError();
        if (pass.getTextString().equals(repeatPass.getTextString())) {
            if (pass.getTextString().length() == 0) {
                pass.showEmptyPasswordError();
                return false;
            } else {
                setPassword(pass.getTextString());
                setEncryption(true);
                return true;
            }
        } else {
            repeatPass.showPasswordsNoMatchError();
            return false;
        }
    }

    boolean changePass(SecurityView.DialogDirector oldPass, SecurityView.DialogDirector newPass, SecurityView.DialogDirector repeatNewPass) {
        return enterPass(oldPass) && setPass(newPass, repeatNewPass);
    }
}
