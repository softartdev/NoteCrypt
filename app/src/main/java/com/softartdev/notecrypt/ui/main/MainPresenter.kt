package com.softartdev.notecrypt.ui.main

import com.softartdev.notecrypt.data.DataManager
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainView>() {

    fun updateNotes() {
        checkViewAttached()
        addDisposable(dataManager.notes()
                .subscribe(
                        {notes -> mvpView?.onUpdateNotes(notes) }
                        , { it.printStackTrace() }))
    }
}
