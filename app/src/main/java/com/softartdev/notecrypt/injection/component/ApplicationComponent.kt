package com.softartdev.notecrypt.injection.component

import com.softartdev.notecrypt.injection.ApplicationContext
import com.softartdev.notecrypt.injection.module.ApplicationModule
import android.app.Application
import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}
