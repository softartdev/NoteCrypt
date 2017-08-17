package com.softartdev.notecrypt.ui.splash

import android.content.SharedPreferences
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter
import java.util.*
import javax.inject.Inject

@ConfigPersistent
class SplashPresenter @Inject
constructor(private val sharedPreferences: SharedPreferences, private val dbStore: DbStore) : BasePresenter<SplashView>() {

    override fun attachView(mvpView: SplashView) {
        super.attachView(mvpView)
    }

    private val isEncryption: Boolean
        get() = dbStore.isEncryption

    fun checkEncryption() {
        if (isEncryption) {
            mvpView!!.navSignIn()
        } else {
            mvpView!!.navMain()
        }
    }

    //TODO: remove
    fun setLocale() {
        val locale: Locale
        val value = sharedPreferences.getString("language", "2")
        when (value) {
            "1" -> locale = Locale.ENGLISH
            "2" -> locale = Locale("ru")
            else -> locale = Locale.getDefault()
        }
        Locale.setDefault(locale)
        mvpView!!.onLocale(locale)
    }
}
