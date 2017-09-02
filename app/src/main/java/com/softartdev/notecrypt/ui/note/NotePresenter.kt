package com.softartdev.notecrypt.ui.note

import android.text.TextUtils
import com.softartdev.notecrypt.data.DataManager
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

@ConfigPersistent
internal class NotePresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<NoteView>() {
    private var mNote: Note? = null

    override fun attachView(mvpView: NoteView) {
        super.attachView(mvpView)
    }

    fun createNote() {
        addDisposable(dataManager.createNote()
                .subscribe({note ->
                    mNote = note
                }, {it.printStackTrace() }))
    }

    fun saveNote(title: String, text: String) {
        if (mNote == null) {
            createNote()
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(text)) {
            mvpView!!.onEmptyNote()
        } else {
            addDisposable(dataManager.saveNote(mNote!!.id, title, text)
                    .subscribe(
                            { mvpView!!.onSaveNote(title) },
                            { it.printStackTrace() }))
        }
    }

    fun loadNote(noteId: Long) {
        addDisposable(dataManager.loadNote(noteId)
                .subscribe({note ->
                            mNote = note
                            mvpView!!.onLoadNote(mNote!!.title, mNote!!.text)
                }, { it.printStackTrace() }))
    }

    fun deleteNote() {
        if (mNote != null) {
            addDisposable(dataManager.deleteNote(mNote!!.id)
                    .subscribe(
                            { Timber.d("Note deleted") }
                            , { it.printStackTrace() }))
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
