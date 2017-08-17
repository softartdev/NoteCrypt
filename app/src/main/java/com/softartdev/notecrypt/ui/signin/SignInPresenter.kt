package com.softartdev.notecrypt.ui.signin

import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.ui.base.BasePresenter

import javax.inject.Inject

@ConfigPersistent
class SignInPresenter @Inject
constructor(private val dbStore: DbStore) : BasePresenter<SignInView>() {

    override fun attachView(mvpView: SignInView) {
        super.attachView(mvpView)
    }

    fun signIn(pass: String) {
        mvpView!!.hideError()
        if (pass.isEmpty()) {
            mvpView!!.showEmptyPassError()
        } else if (checkPass(pass)) {
            mvpView!!.navMain()
        } else {
            mvpView!!.showIncorrectPassError()
        }
    }

    private fun checkPass(pass: String): Boolean {
        return dbStore.checkPass(pass)
    }
}
