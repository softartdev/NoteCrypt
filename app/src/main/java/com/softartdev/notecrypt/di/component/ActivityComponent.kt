package com.softartdev.notecrypt.di.component

import com.softartdev.notecrypt.di.PerActivity
import com.softartdev.notecrypt.di.module.ActivityModule
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.main.MainActivity
import com.softartdev.notecrypt.ui.note.NoteActivity
import com.softartdev.notecrypt.ui.settings.security.SecurityActivity
import com.softartdev.notecrypt.ui.signin.SignInActivity
import com.softartdev.notecrypt.ui.splash.SplashActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(signInActivity: SignInActivity)

    fun inject(splashActivity: SplashActivity)

    fun inject(noteActivity: NoteActivity)

    fun inject(securityActivity: SecurityActivity)
}
