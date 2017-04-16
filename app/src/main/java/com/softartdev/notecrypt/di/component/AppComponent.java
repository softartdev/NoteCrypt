package com.softartdev.notecrypt.di.component;

import com.softartdev.notecrypt.di.module.AppModule;
import com.softartdev.notecrypt.ui.main.MainPresenter;
import com.softartdev.notecrypt.ui.note.NotePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainPresenter mainPresenter);
    void inject(NotePresenter notePresenter);
}
