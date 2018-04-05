package com.softartdev.notecrypt.ui.splash

import com.softartdev.notecrypt.data.DataManager
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
class SplashPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<SplashView>() {

    fun checkEncryption() {
        checkViewAttached()
        if (dataManager.isEncryption()) {
            mvpView!!.navSignIn()
        } else {
            mvpView!!.navMain()
        }
    }
}
