package com.softartdev.notecrypt.ui.main

import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.injection.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dbStore: DbStore) : BasePresenter<MainView>() {

    override fun attachView(mvpView: MainView) {
        super.attachView(mvpView)
    }

    fun updateNotes() {
        checkViewAttached()
        val notes = dbStore.notes
        mvpView!!.onUpdateNotes(notes)
    }

    fun addNote() {
        checkViewAttached()
        mvpView!!.onAddNote()
    }
}
