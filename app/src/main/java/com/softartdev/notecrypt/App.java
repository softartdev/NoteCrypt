package com.softartdev.notecrypt;

import android.app.Application;
import android.support.annotation.Nullable;

import com.softartdev.notecrypt.di.component.AppComponent;
import com.softartdev.notecrypt.di.component.DaggerAppComponent;
import com.softartdev.notecrypt.di.component.DbComponent;
import com.softartdev.notecrypt.di.module.AppModule;
import com.softartdev.notecrypt.di.module.DbModule;

import io.realm.Realm;

public class App extends Application {
    private static AppComponent appComponent;
    private static DbComponent dbComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        Realm.init(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static DbComponent createDbComponent(@Nullable String pass) {
        if (dbComponent == null) {
            dbComponent = appComponent.plusDbComponent(new DbModule(pass));
        }
        return dbComponent;
    }

    public static void clearDbComponent() {
        dbComponent = null;
    }
}
