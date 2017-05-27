package com.softartdev.notecrypt.ui.note;

interface NoteView {
    void onLoadNote(String title, String text);
    void onSaveNote(String title);
    void onEmptyNote();
    void onDeleteNote();
    void onCheckSaveChange();
    void onNavBack();
}
