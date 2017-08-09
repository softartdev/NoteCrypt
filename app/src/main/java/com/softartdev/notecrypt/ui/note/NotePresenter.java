package com.softartdev.notecrypt.ui.note;

import android.text.TextUtils;

import com.softartdev.notecrypt.App;
import com.softartdev.notecrypt.db.DbStore;
import com.softartdev.notecrypt.model.Note;

import javax.inject.Inject;

public class NotePresenter {
    private NoteView mView;
    private Note mNote;

    @Inject
    DbStore dbStore;

    NotePresenter(NoteView view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    void createNote() {
        mNote = dbStore.createNote();
    }

    void saveNote(final String title, final String text) {
        if (mNote == null) {
            createNote();
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(text)) {
            mView.onEmptyNote();
        } else {
            dbStore.saveNote(mNote.getId(), title, text);
            mView.onSaveNote(title);
        }
    }

    void loadNote(final long noteId) {
        mNote = dbStore.loadNote(noteId);
        mView.onLoadNote(mNote.getTitle(), mNote.getText());
    }

    void deleteNote() {
        if (mNote != null) {
            dbStore.deleteNote(mNote.getId());
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
