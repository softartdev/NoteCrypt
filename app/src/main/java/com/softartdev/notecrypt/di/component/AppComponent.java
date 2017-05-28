package com.softartdev.notecrypt.di.component;

import com.softartdev.notecrypt.di.module.AppModule;
import com.softartdev.notecrypt.di.module.DbModule;
import com.softartdev.notecrypt.ui.signin.SignInPresenter;
import com.softartdev.notecrypt.ui.splash.SplashPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    DbComponent plusDbComponent(DbModule dbModule);

    void inject(SplashPresenter splashPresenter);
    void inject(SignInPresenter signInPresenter);
}
