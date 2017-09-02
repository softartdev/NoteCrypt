package com.softartdev.notecrypt.db;

import android.content.Context;
import android.text.TextUtils;

import com.softartdev.notecrypt.model.Note;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import io.realm.RealmResults;
import timber.log.Timber;

public class RealmDbStore extends RealmDbRepository {

    public RealmDbStore(Context context) {
        super(context);
    }

    @Override
    public RealmResults<Note> getNotes() {
        return getRealm().where(Note.class).findAll();
    }

    @Override
    public Note createNote() {
        final long noteId = UUID.randomUUID().getLeastSignificantBits();
        getRealm().executeTransaction(realm -> {
            Note note = realm.createObject(Note.class, noteId);
            note.setTitle("");
            note.setText("");
        });
        return loadNote(noteId);
    }

    @Override
    public void saveNote(final long noteId, final String title, final String text) {
        getRealm().executeTransaction(realm -> {
            Note note = realm.where(Note.class).equalTo("id", noteId).findFirst();
            if (note == null) {
                note = realm.createObject(Note.class, noteId);
            }
            note.setTitle(title);
            note.setText(text);
            realm.copyToRealmOrUpdate(note);
        });
    }

    @Override
    public Note loadNote(long noteId) {
        Note managedNote = getRealm().where(Note.class).equalTo("id", noteId).findFirst();
        Note unmanagedNote = getRealm().copyFromRealm(managedNote);
        Timber.d("Unmanaged note: %s", unmanagedNote.getTitle());
        return unmanagedNote;
    }

    @Override
    public void deleteNote(final long noteId) {
        getRealm().executeTransaction(realm -> {
            Note note = realm.where(Note.class).equalTo("id", noteId).findFirst();
            note.deleteFromRealm();
        });
    }

    @Override
    public boolean isChanged(long id, @NotNull String title, @NotNull String text) {
        Note note = loadNote(id);
        String savedTitle = note.getTitle();
        String savedText = note.getText();
        return !title.equals(savedTitle) || !text.equals(savedText);
    }

    @Override
    public boolean isEmpty(long id) {
        Note note = loadNote(id);
        String savedTitle = note.getTitle();
        String savedText = note.getText();
        return TextUtils.isEmpty(savedTitle) && TextUtils.isEmpty(savedText);
    }
}
