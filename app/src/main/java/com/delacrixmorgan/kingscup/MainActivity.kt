package com.delacrixmorgan.kingscup

import android.media.AudioManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.setLocale
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * MainActivity
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)

        SoundEngine.newInstance(this)
        this.volumeControlStream = AudioManager.STREAM_MUSIC

        val preference = PreferenceHelper.getPreference(this)
        val preferenceCountryIso = preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]

        this.resources.setLocale(preferenceCountryIso)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}