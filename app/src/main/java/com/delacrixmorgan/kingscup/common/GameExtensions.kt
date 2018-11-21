package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.delacrixmorgan.kingscup.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

/**
 * GameExtensions
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

fun View.performHapticContextClick() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    } else {
        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
}

fun setLocale(language: String?, resources: Resources) {
    val locale = Locale(language)

    with(resources) {
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        updateConfiguration(configuration, displayMetrics)
    }

    Locale.setDefault(locale)
}

fun animateButtonGrow(context: Context, button: FloatingActionButton) {
    val animGrow = AnimationSet(true)
    animGrow.addAnimation(AnimationUtils.loadAnimation(context, R.anim.pop_out))
    button.startAnimation(animGrow)
}

fun setupProgressBar(manager: LinearLayoutManager, recyclerView: RecyclerView, progressBar: ProgressBar) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (manager.findFirstVisibleItemPosition() == 0) {
                progressBar.progress = 0
            } else {
                progressBar.progress = manager.findLastVisibleItemPosition()
            }
        }
    })
}

//region Context
fun Context.showFragmentSliding(fragment: Fragment, gravity: Int) {
    fragment.enterTransition = Slide(gravity).setDuration(200)

    (this as FragmentActivity).supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainer, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
}

fun Context.launchShareGameIntent(message: String) {
    val intent = Intent(Intent.ACTION_SEND)

    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, message)

    startActivity(Intent.createChooser(intent, getString(R.string.preference_title_share_friend)))
}

fun Context.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)

    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Context.launchPlayStore(packageName: String) {
    val url = "https://play.google.com/store/apps/details?id=$packageName"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    intent.flags = FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)

    SoundEngine.getInstance().playSound(this, SoundType.OOOH)
}

//enregion