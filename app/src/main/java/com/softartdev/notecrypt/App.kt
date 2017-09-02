package com.softartdev.notecrypt

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.softartdev.notecrypt.di.component.ApplicationComponent
import com.softartdev.notecrypt.di.component.DaggerApplicationComponent
import com.softartdev.notecrypt.di.module.ApplicationModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class App : Application() {

    internal var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        }
    }

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            mApplicationComponent = applicationComponent
        }

    companion object {

        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}
