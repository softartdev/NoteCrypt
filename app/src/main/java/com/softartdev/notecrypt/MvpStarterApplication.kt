package com.softartdev.notecrypt

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.softartdev.notecrypt.injection.component.ApplicationComponent
import com.softartdev.notecrypt.di.component.DaggerAppComponent
import com.softartdev.notecrypt.injection.component.DaggerApplicationComponent
import com.softartdev.notecrypt.injection.module.ApplicationModule
import timber.log.Timber

class MvpStarterApplication : Application() {

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
                mApplicationComponent = DaggerAppComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            mApplicationComponent = applicationComponent
        }

    companion object {

        operator fun get(context: Context): MvpStarterApplication {
            return context.applicationContext as MvpStarterApplication
        }
    }
}
