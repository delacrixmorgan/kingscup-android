package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 08/03/2018.
 **/

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