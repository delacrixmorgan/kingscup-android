package com.delacrixmorgan.kingscup.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 08/11/2017.
 */

@SuppressLint("Registered")
open class BaseActivity : Activity() {
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
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
}