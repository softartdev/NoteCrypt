package com.softartdev.notecrypt.ui.note;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.model.Note;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;

public class NotePresenter {
    private NoteView mView;
    private Note mNote;

    @Inject
    Realm realm;

    NotePresenter(NoteView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    void saveNote(final String title, final String text) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (mNote == null) {
                    mNote = realm.createObject(Note.class, UUID.randomUUID().getLeastSignificantBits());
                }
                mNote.setTitle(title);
                mNote.setText(text);
                realm.copyToRealmOrUpdate(mNote);
            }
        });

        mView.onSaveNote(title);
    }

    void loadNote(final long noteId) {
        mNote = realm.where(Note.class).equalTo("id", noteId).findFirst();
        mView.onLoadNote(mNote.getTitle(), mNote.getText());
    }

    void deleteNote() {
        if (mNote != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    mNote.deleteFromRealm();
                }
            });
        }
        mNote = null;
        mView.onDeleteNote();
    }
}
