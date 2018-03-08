package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 08/03/2018.
 **/

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
            ONE -> context.getString(R.string.load_type_new_game)
            TWO -> context.getString(R.string.load_type_new_game)
            THREE -> context.getString(R.string.load_type_new_game)
            FOUR -> context.getString(R.string.load_type_new_game)
            FIVE -> context.getString(R.string.load_type_new_game)
            SIX -> context.getString(R.string.load_type_new_game)
            SEVEN -> context.getString(R.string.load_type_new_game)
            EIGHT -> context.getString(R.string.load_type_new_game)
            NINE -> context.getString(R.string.load_type_new_game)
            TEN -> context.getString(R.string.load_type_new_game)
        }
    }
}