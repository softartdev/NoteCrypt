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

    void createNote() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mNote = realm.createObject(Note.class, UUID.randomUUID().getLeastSignificantBits());
                mNote.setTitle("");
                mNote.setText("");
            }
        });
    }

    void saveNote(final String title, final String text) {
        if (mNote == null) {
            createNote();
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mNote.setTitle(title);
                mNote.setText(text);
            }
        });
        if (title.equals("") && text.equals("")) {
            mView.onEmptyNote();
        } else {
            realm.copyToRealmOrUpdate(mNote);
            mView.onSaveNote(title);
        }
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
        mView.onNavBack();
    }

    void checkSaveChange(String title, String text) {
        String savedTitle = mNote.getTitle();
        String savedText = mNote.getText();
        if (!title.equals(savedTitle) || !text.equals(savedText)) {
            mView.onCheckSaveChange();
            return;
        }
        if (savedTitle.equals("") && savedText.equals("")) {
            deleteNote();
        }
        mView.onNavBack();
    }
}
