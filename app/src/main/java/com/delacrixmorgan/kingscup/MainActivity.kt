package com.delacrixmorgan.kingscup

import android.app.ActivityManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import com.crashlytics.android.Crashlytics
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.showFragmentSliding
import com.delacrixmorgan.kingscup.menu.MenuFragment
import io.fabric.sdk.android.Fabric

/**
 * Created by Delacrix Morgan on 26/03/2017.
 **/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())

        setContentView(R.layout.activity_main)
        showFragmentSliding(this@MainActivity, MenuFragment.newInstance(), Gravity.BOTTOM)

        SoundEngine.newInstance(this)
        volumeControlStream = AudioManager.STREAM_MUSIC

        this.changeAppOverview(this, theme)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTaskDescription(ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(resources, R.drawable.kingscup_logo_icon),
                    ContextCompat.getColor(this, R.color.colorPrimary)))
        }

        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun changeAppOverview(activity: AppCompatActivity, theme: Resources.Theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val typedValue = TypedValue()
            val colour = typedValue.data
            val bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.kingscup_logo_no_bg)
            activity.setTaskDescription(ActivityManager.TaskDescription(null, bitmap, colour))
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            bitmap.recycle()
        }
    }
}