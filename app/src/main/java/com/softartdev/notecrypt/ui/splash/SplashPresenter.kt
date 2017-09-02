package com.softartdev.notecrypt.ui.splash

import com.softartdev.notecrypt.data.DataManager
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter
import com.softartdev.notecrypt.util.PreferencesHelper
import java.util.*
import javax.inject.Inject

@ConfigPersistent
class SplashPresenter @Inject
constructor(private val preferencesHelper: PreferencesHelper, private val dataManager: DataManager) : BasePresenter<SplashView>() {

    override fun attachView(mvpView: SplashView) {
        super.attachView(mvpView)
    }

    fun checkEncryption() {
        checkViewAttached()
        if (dataManager.isEncryption()) {
            mvpView!!.navSignIn()
        } else {
            mvpView!!.navMain()
        }
    }

    //TODO: remove
    fun setLocale() {
        val locale: Locale = preferencesHelper.getLocale()
        Locale.setDefault(locale)
        mvpView!!.onLocale(locale)
    }
}
