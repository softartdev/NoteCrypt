package com.softartdev.notecrypt.di.component;

import com.softartdev.notecrypt.di.module.AppModule;
import com.softartdev.notecrypt.di.module.DbModule;
import com.softartdev.notecrypt.ui.main.MainPresenter;
import com.softartdev.notecrypt.ui.note.NotePresenter;
import com.softartdev.notecrypt.ui.settings.security.SecurityPresenter;
import com.softartdev.notecrypt.ui.splash.SplashPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(SplashPresenter splashPresenter);

    DbComponent plusDbComponent(DbModule dbModule);
}
