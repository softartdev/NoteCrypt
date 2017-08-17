package com.softartdev.notecrypt.ui.note

import android.text.TextUtils
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
internal class NotePresenter @Inject
constructor(private val dbStore: DbStore) : BasePresenter<NoteView>() {
    private var mNote: Note? = null

    override fun attachView(mvpView: NoteView) {
        super.attachView(mvpView)
    }

    fun createNote() {
        mNote = dbStore.createNote()
    }

    fun saveNote(title: String, text: String) {
        if (mNote == null) {
            createNote()
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(text)) {
            mvpView!!.onEmptyNote()
        } else {
            dbStore.saveNote(mNote!!.id, title, text)
            mvpView!!.onSaveNote(title)
        }
    }

    fun loadNote(noteId: Long) {
        mNote = dbStore.loadNote(noteId)
        mvpView!!.onLoadNote(mNote!!.title, mNote!!.text)
    }

    fun deleteNote() {
        if (mNote != null) {
            dbStore.deleteNote(mNote!!.id)
        }
        mNote = null
        mvpView!!.onDeleteNote()
        mvpView!!.onNavBack()
    }

    fun checkSaveChange(title: String, text: String) {
        val savedTitle = mNote!!.title
        val savedText = mNote!!.text
        if (title != savedTitle || text != savedText) {
            mvpView!!.onCheckSaveChange()
            return
        }
        if (savedTitle == "" && savedText == "") {
            deleteNote()
        }
        mvpView!!.onNavBack()
    }
}
