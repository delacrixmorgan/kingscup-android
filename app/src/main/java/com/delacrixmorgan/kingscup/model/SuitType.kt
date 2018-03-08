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
            SPADE -> context.getString(R.string.load_type_new_game)
            HEART -> context.getString(R.string.load_type_new_game)
            CLUB -> context.getString(R.string.load_type_new_game)
            DIAMOND -> context.getString(R.string.load_type_new_game)
        }
    }
}