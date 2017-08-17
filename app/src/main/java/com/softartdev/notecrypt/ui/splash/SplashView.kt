package com.softartdev.notecrypt.ui.splash

import com.softartdev.notecrypt.ui.base.MvpView

import java.util.Locale

interface SplashView : MvpView {
    fun onLocale(locale: Locale)
    fun navSignIn()
    fun navMain()
}
