package com.softartdev.notecrypt.di.component;

import com.softartdev.notecrypt.di.module.DbModule;
import com.softartdev.notecrypt.di.scope.DbScope;
import com.softartdev.notecrypt.ui.main.MainPresenter;
import com.softartdev.notecrypt.ui.note.NotePresenter;
import com.softartdev.notecrypt.ui.settings.security.SecurityPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = DbModule.class)
@DbScope
public interface DbComponent {
    void inject(MainPresenter mainPresenter);
    void inject(NotePresenter notePresenter);
    void inject(SecurityPresenter securityPresenter);
}

