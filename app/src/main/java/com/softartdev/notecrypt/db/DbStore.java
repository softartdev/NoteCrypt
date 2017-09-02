package com.softartdev.notecrypt.db;

import com.softartdev.notecrypt.model.Note;

import io.realm.RealmResults;

public interface DbStore {
    RealmResults<Note> getNotes();

    Note createNote();

    void saveNote(long id, String title, String text);

    Note loadNote(long noteId);

    void deleteNote(long id);

    boolean checkPass(String pass);

    boolean isEncryption();

    void changePass(String odlPass, String newPass);
}
