package com.softartdev.notecrypt.db;

import android.content.Context;

import com.softartdev.notecrypt.model.Note;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class RealmDbStore extends RealmDbStoreProvider {

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
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class, noteId);
                note.setTitle("");
                note.setText("");
            }
        });
        return loadNote(noteId);
    }

    @Override
    public void saveNote(final long noteId, final String title, final String text) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", noteId).findFirst();
                if (note == null) {
                    note = realm.createObject(Note.class, noteId);
                }
                note.setTitle(title);
                note.setText(text);
                realm.copyToRealmOrUpdate(note);
            }
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
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", noteId).findFirst();
                note.deleteFromRealm();
            }
        });
    }
}
