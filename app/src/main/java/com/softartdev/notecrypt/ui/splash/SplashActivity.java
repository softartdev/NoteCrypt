package com.softartdev.notecrypt.ui.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.softartdev.notecrypt.ui.main.MainActivity;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements SplashView {
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SplashPresenter(this);
        mPresenter.setLocale();
        mPresenter.checkEncryption();
    }

    @Override
    public void navSignIn() {
        //TODO
    }

    @Override
    public void navMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLocale(Locale locale) {
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getResources().getDisplayMetrics());
    }
}
