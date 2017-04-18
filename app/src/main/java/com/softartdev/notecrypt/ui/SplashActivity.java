package com.softartdev.notecrypt.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.softartdev.notecrypt.ui.main.MainActivity;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLocale();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLocale() {
        Locale locale;
        String value = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("language", "2");
        switch (value) {
            case "1":
                locale = Locale.ENGLISH;
                break;
            case "2":
                locale = new Locale("ru");
                break;
            default:
                locale = Locale.getDefault();
                break;
        }
        Locale.setDefault(locale);
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
