package com.softartdev.notecrypt.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.softartdev.notecrypt.di.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject
constructor(@ApplicationContext context: Context) {

    private val mPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun clear() {
        mPref.edit().clear().apply()
    }

    fun getLocale(): Locale {
        val value = mPref.getString("language", "2")
        val locale: Locale
        when (value) {
            "1" -> locale = Locale.ENGLISH
            "2" -> locale = Locale("ru")
            else -> locale = Locale.getDefault()
        }
        return locale
    }

}
