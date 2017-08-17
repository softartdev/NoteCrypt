package com.softartdev.notecrypt.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.db.RealmDbStore

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import com.softartdev.notecrypt.injection.ApplicationContext

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    internal fun provideDbStore(context: Context): DbStore {
        return RealmDbStore(context)
    }
}
