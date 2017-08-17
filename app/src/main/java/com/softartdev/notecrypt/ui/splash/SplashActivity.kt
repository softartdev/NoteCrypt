package com.softartdev.notecrypt.ui.splash

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.main.MainActivity
import com.softartdev.notecrypt.ui.signin.SignInActivity
import java.util.*
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {
    @Inject lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mPresenter.attachView(this)

        mPresenter.setLocale()
        mPresenter.checkEncryption()
    }

    override fun navSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLocale(locale: Locale) {
        val configuration = resources.configuration
        configuration.locale = locale
        onConfigurationChanged(configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        baseContext.resources.updateConfiguration(newConfig, resources.displayMetrics)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}
