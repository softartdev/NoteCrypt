package com.softartdev.notecrypt.ui.note;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.model.Note;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;

public class NotePresenter {
    private NoteView mView;

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
                Note note = realm.createObject(Note.class, UUID.randomUUID().getLeastSignificantBits());
                note.setTitle(title);
                note.setText(text);
                realm.copyToRealmOrUpdate(note);
            }
        });

        mView.onSaveNote(title);
    }
}
