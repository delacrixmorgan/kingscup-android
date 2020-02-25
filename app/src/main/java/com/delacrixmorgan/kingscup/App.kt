package com.delacrixmorgan.kingscup

import android.app.Application
import android.content.Context
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.google.firebase.analytics.FirebaseAnalytics

class App : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        FirebaseAnalytics.getInstance(this)
        GameEngine.getInstance(this)
        SoundEngine.getInstance(this)
    }
}