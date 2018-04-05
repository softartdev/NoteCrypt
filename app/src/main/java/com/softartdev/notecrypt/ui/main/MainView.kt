package com.softartdev.notecrypt.ui.main

import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.MvpView

interface MainView : MvpView {
    fun onAddNote()
    fun onUpdateNotes(notes: List<Note>)
}
