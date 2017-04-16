package com.softartdev.notecrypt.ui.note;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.model.Note;

import javax.inject.Inject;

import io.realm.Realm;

public class NotePresenter {
    private NoteView mView;

    @Inject
    Realm realm;

    public NotePresenter(NoteView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    public void saveNote(String title, String text) {
        final Note note = new Note();
        note.setTitle(title);
        note.setText(text);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(note);
            }
        });

        mView.onSaveNote(title);
    }
}
