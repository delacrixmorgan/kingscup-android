package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Resources
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.transition.Slide
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import com.delacrixmorgan.kingscup.R
import java.util.*

/**
 * Created by Delacrix Morgan on 13/11/2017.
 **/

fun showFragmentSliding(context: Context, fragment: Fragment, gravity: Int) {
    val activity = context as FragmentActivity

    fragment.enterTransition = Slide(gravity).setDuration(200)

    activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainer, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
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
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (manager.findFirstVisibleItemPosition() == 0) {
                progressBar.progress = 0
            } else {
                progressBar.progress = manager.findLastVisibleItemPosition()
            }
        }
    })
}

fun Context.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)

    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Context.launchPlayStore() {
    val url = "https://play.google.com/store/apps/details?id=${this.packageName}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    intent.flags = FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)

    SoundEngine.getInstance().playSound(this, SoundType.OOOH)
}