package com.softartdev.notecrypt.ui.main;

import com.softartdev.notecrypt.model.Note;

import io.realm.RealmResults;

public interface MainView {
    void onAddNote();

    void onUpdateNotes(RealmResults<Note> notes);
}
