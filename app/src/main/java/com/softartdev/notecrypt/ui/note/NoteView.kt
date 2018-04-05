package com.softartdev.notecrypt.ui.note

import com.softartdev.notecrypt.ui.base.MvpView

interface NoteView : MvpView {
    fun onLoadNote(title: String, text: String)
    fun onSaveNote(title: String)
    fun onEmptyNote()
    fun onDeleteNote()
    fun onCheckSaveChange()
    fun onNavBack()
}
