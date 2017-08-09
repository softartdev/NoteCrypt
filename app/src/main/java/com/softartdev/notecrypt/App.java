package com.softartdev.notecrypt;

import android.app.Application;

import com.softartdev.notecrypt.di.component.AppComponent;
import com.softartdev.notecrypt.di.component.DaggerAppComponent;
import com.softartdev.notecrypt.di.module.AppModule;

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
