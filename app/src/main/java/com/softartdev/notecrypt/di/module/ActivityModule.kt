package com.softartdev.notecrypt.di.module

import android.app.Activity
import android.content.Context
import com.softartdev.notecrypt.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return mActivity
    }
}
