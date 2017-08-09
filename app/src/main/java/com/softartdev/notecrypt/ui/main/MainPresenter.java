package com.softartdev.notecrypt.ui.main;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.db.DbStore;
import com.softartdev.notecrypt.model.Note;

import javax.inject.Inject;

import io.realm.RealmResults;

public class MainPresenter {
    private MainView mView;

    @Inject
    DbStore dbStore;

    MainPresenter(MainView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    void updateNotes() {
        RealmResults<Note> notes = dbStore.getNotes();
        mView.onUpdateNotes(notes);
    }

    void addNote() {
        mView.onAddNote();
    }
}
