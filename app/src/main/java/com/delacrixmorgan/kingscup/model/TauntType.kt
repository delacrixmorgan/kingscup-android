package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * TauntType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class TauntType {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN;

    fun getLocalisedText(context: Context): String {
        return when (this) {
            ONE -> context.getString(R.string.taunt_one)
            TWO -> context.getString(R.string.taunt_two)
            THREE -> context.getString(R.string.taunt_three)
            FOUR -> context.getString(R.string.taunt_four)
            FIVE -> context.getString(R.string.taunt_five)
            SIX -> context.getString(R.string.taunt_six)
            SEVEN -> context.getString(R.string.taunt_seven)
            EIGHT -> context.getString(R.string.taunt_eight)
            NINE -> context.getString(R.string.taunt_nine)
            TEN -> context.getString(R.string.taunt_ten)
        }
    }
}