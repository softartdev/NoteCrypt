package com.softartdev.notecrypt.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.softartdev.notecrypt.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject
constructor(@ApplicationContext context: Context) {

    private val mPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun clear() {
        mPref.edit().clear().apply()
    }

}
