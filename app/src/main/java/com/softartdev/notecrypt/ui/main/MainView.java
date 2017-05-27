package com.softartdev.notecrypt.ui.main;

import com.softartdev.notecrypt.model.Note;

import io.realm.RealmResults;

interface MainView {
    void onNote(long noteId);

    void onAddNote();

    void onUpdateNotes(RealmResults<Note> notes);
}
