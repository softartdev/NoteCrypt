package com.softartdev.notecrypt.di.module

import android.app.Application
import android.content.Context
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.db.RealmDbStore
import com.softartdev.notecrypt.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
    internal fun provideDbStore(@ApplicationContext context: Context): DbStore {
        return RealmDbStore(context)
    }
}
