package com.delacrixmorgan.kingscup.common

import android.app.Activity
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.support.design.widget.FloatingActionButton
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.delacrixmorgan.kingscup.R
import java.util.*

/**
 * Created by Delacrix Morgan on 17/03/2017.
 */

object Helper {
    val SHARED_PREFERENCE = "SHARED_PREFERENCE"
    val LANGUAGE_PREFERENCE = "LANGUAGE_PREFERENCE"
    val mLanguageItems = arrayOf<CharSequence>("Default Language", "English", "简体中文")

    fun showFragment(activity: Activity, fragment: Fragment) {
        activity.fragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, fragment.javaClass.simpleName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

    fun animateButtonGrow(activity: Activity, button: FloatingActionButton) {
        val animGrow = AnimationSet(true)
        animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out))
        button.startAnimation(animGrow)
    }

    fun setLocaleLanguage(context: Context) {
        var languageCode = Locale.getDefault().language

        when (context.getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 1)) {
            1 -> languageCode = "en"
            2 -> languageCode = "zh"
        }

        val configuration = Configuration(context.resources.configuration)
        configuration.locale = Locale(languageCode)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}
