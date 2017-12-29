package com.delacrixmorgan.kingscup.game

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 27/12/2017.
 **/

enum class LoadType(val statusText: String) {
    NEW_GAME("Hold my Beer"),
    RESTART_GAME("Setting up New Game");

    fun localisedDisplayStatusText(context: Context): String {
        return when (this) {
            NEW_GAME -> context.getString(R.string.load_type_new_game)
            RESTART_GAME -> context.getString(R.string.load_type_restart_game)
        }
    }
}