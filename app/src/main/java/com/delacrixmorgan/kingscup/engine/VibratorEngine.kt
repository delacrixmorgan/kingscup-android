package com.delacrixmorgan.kingscup.engine

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.performHapticContextClick
import com.delacrixmorgan.kingscup.model.VibrateType

object VibratorEngine {

    private val vibrator: Vibrator by lazy {
        App.appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun vibrate(view: View, vibrateType: VibrateType) {
        val preference = PreferenceHelper.getPreference(App.appContext)
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        if (vibratePreference) {
            when (vibrateType) {
                VibrateType.Short -> {
                    view.performHapticContextClick()
                }

                VibrateType.Long -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(vibrateType.duration, -1))
                    } else {
                        vibrator.vibrate(vibrateType.duration)
                    }
                }
            }
        }
    }
}