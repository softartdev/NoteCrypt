package com.softartdev.notecrypt.di.component

import android.app.Application
import android.content.Context
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.di.ApplicationContext
import com.softartdev.notecrypt.di.module.ApplicationModule
import com.softartdev.notecrypt.util.PreferencesHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun preferencesHelper(): PreferencesHelper

    fun dbStore(): DbStore
}
