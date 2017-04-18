package com.softartdev.notecrypt.ui.main;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.model.Note;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainPresenter {
    MainView mView;

    @Inject
    Realm realm;

    public MainPresenter(MainView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    public void updateNotes() {
        RealmResults<Note> notes = realm.where(Note.class).findAll();
        mView.onUpdateNotes(notes);
    }

    public void addNote() {
        mView.onAddNote();
    }
}
