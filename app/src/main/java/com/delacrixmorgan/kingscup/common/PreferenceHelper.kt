package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * PreferenceHelper
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

object PreferenceHelper {

    const val ONBOARDING = "Preference.OnBoarding"
    const val LANGUAGE = "Preference.Language"
    const val VIBRATE = "Preference.Vibrate"
    const val SOUND = "Preference.Sound"

    const val ONBOARDING_DEFAULT = false
    const val LANGUAGE_DEFAULT = "en"
    const val VIBRATE_DEFAULT = true
    const val SOUND_DEFAULT = true

    fun getPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { this.putString(key, value) }
            is Int -> edit { this.putInt(key, value) }
            is Boolean -> edit { this.putBoolean(key, value) }
            is Float -> edit { this.putFloat(key, value) }
            is Long -> edit { this.putLong(key, value) }
            else -> throw UnsupportedOperationException("SharedPreference (UnsupportedOperationException)")
        }
    }

    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
            else -> throw UnsupportedOperationException("SharedPreference (UnsupportedOperationException)")
        }
    }
}