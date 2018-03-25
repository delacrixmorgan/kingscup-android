package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * SuitType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class SuitType {
    SPADE,
    HEART,
    CLUB,
    DIAMOND;

    fun getLocalisedText(context: Context): String {
        return when (this) {
            SPADE -> context.getString(R.string.suit_spade)
            HEART -> context.getString(R.string.suit_heart)
            CLUB -> context.getString(R.string.suit_club)
            DIAMOND -> context.getString(R.string.suit_diamond)
        }
    }
}