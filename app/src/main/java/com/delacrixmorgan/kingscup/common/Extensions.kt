package com.delacrixmorgan.kingscup.common

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.Card
import java.util.*

/**
 * Resource
 */
fun Resources.setLocale(language: String) {
    val locale = Locale(language)

    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    updateConfiguration(configuration, displayMetrics)

    Locale.setDefault(locale)
}

/**
 * View
 */
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

/**
 * Fragment
 */
fun Fragment.launchShareGameIntent(message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    startActivity(Intent.createChooser(intent, getString(R.string.preference_title_share_friend)))
}

fun Fragment.launchWebsite(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    startActivity(intent)
}

/**
 * Card
 */
fun ArrayList<Card>.findCardIndex(card: Card): Int {
    return indexOf(firstOrNull {
        it.suitType == card.suitType && it.rank == card.rank
    })
}