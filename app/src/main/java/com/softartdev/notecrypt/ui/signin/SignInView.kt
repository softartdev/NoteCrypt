package com.softartdev.notecrypt.ui.signin

import com.softartdev.notecrypt.ui.base.MvpView

interface SignInView : MvpView {
    fun navMain()
    fun showEmptyPassError()
    fun showIncorrectPassError()
    fun hideError()
}
