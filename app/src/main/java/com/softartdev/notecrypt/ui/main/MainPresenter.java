package com.softartdev.notecrypt.ui.main;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.model.Note;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainPresenter {
    private MainView mView;

    @Inject
    Realm realm;

    MainPresenter(MainView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    void updateNotes() {
        RealmResults<Note> notes = realm.where(Note.class).findAll();
        mView.onUpdateNotes(notes);
    }

    void addNote() {
        mView.onAddNote();
    }
}
