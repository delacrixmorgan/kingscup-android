package com.delacrixmorgan.kingscup.common

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import com.delacrixmorgan.kingscup.R
import java.util.*

/**
 * Created by Delacrix Morgan on 13/11/2017.
 */

fun setLocale(language: String?, resources: Resources) {
    val locale = Locale(language)

    with(resources) {
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        updateConfiguration(configuration, displayMetrics)
    }

    Locale.setDefault(locale)
}

fun animateButtonGrow(activity: Activity, button: FloatingActionButton) {
    val animGrow = AnimationSet(true)
    animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out))
    button.startAnimation(animGrow)
}

fun setupProgressBar(manager: LinearLayoutManager, recyclerView: RecyclerView, progressBar: ProgressBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (manager.findFirstVisibleItemPosition() == 0) {
                progressBar.progress = 0
            } else {
                progressBar.progress = manager.findLastVisibleItemPosition()
            }
        }
    } else {
        progressBar.visibility = View.GONE
    }
}