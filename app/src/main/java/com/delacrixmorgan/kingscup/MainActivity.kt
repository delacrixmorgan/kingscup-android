package com.delacrixmorgan.kingscup

import android.app.Activity
import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.View

import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.fragment.MenuFragment
import com.delacrixmorgan.kingscup.shared.Helper

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Helper.setLocaleLanguage(this)
        volumeControlStream = AudioManager.STREAM_MUSIC

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_main_vg_fragment, MenuFragment())
                    .commit()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTaskDescription(ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(resources, R.drawable.kingscup_logo_icon),
                    resources.getColor(R.color.colorPrimary)))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        GameEngine.getInstance().playSound(this, "CARD_WHOOSH")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}
