package com.softartdev.notecrypt.ui.main

import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.MvpView

import io.realm.RealmResults

interface MainView : MvpView {
    fun onNote(noteId: Long)

    fun onAddNote()

    fun onUpdateNotes(notes: RealmResults<Note>)
}
