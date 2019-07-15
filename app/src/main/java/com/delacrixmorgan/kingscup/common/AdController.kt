package com.delacrixmorgan.kingscup.common

import android.content.Context
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

/**
 * AdController
 * kingscup-android
 *
 * Created by Delacrix Morgan on 12/07/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class AdController private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: AdController? = null

        fun getInstance(context: Context): AdController {
            return INSTANCE ?: synchronized(this) {
                AdController(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    var interstitialAd: InterstitialAd = InterstitialAd(context)
    var adRequest: AdRequest = AdRequest.Builder().build()

    init {
        // TODO Disable AdMob
//        this.interstitialAd.adUnitId = if (BuildConfig.DEBUG != true) {
//            context.getString(R.string.ad_mob_interstitial_ad_id)
//        } else {
//            context.getString(R.string.ad_mob_interstitial_test_ad_id)
//        }
//        this.interstitialAd.loadAd(this.adRequest)
    }

    fun refreshInterstitialAd() {
        this.adRequest = AdRequest.Builder().build()
        this.interstitialAd.loadAd(this.adRequest)
    }
}
