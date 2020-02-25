package com.delacrixmorgan.kingscup.common

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.R
import java.util.*

fun setupProgressBar(layoutManager: RecyclerView.LayoutManager?, recyclerView: RecyclerView, progressBar: ProgressBar) {
    val manager = layoutManager as LinearLayoutManager

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

//region Resources
fun Resources.setLocale(language: String) {
    val locale = Locale(language)

    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    updateConfiguration(configuration, displayMetrics)

    Locale.setDefault(locale)
}
//endregion

//region View
fun View.performHapticContextClick() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
    } else {
        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }
}

fun View.animateButtonGrow() {
    val animGrow = AnimationSet(true)
    animGrow.addAnimation(AnimationUtils.loadAnimation(this.context, R.anim.pop_out))
    startAnimation(animGrow)
}
//endregion

//region Fragment
fun Fragment.launchShareGameIntent(message: String) {
    val intent = Intent(Intent.ACTION_SEND)

    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, message)

    startActivity(Intent.createChooser(intent, getString(R.string.preference_title_share_friend)))
}

fun Fragment.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)

    intent.data = Uri.parse(url)
    startActivity(intent)
}
//endregion