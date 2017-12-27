package com.delacrixmorgan.kingscup.game

import android.content.Context

/**
 * Created by Delacrix Morgan on 27/12/2017.
 **/

enum class LoadType(val statusText: String) {

    NEW_GAME("Hold my Beer"),
    RESTART_GAME("Setting up New Game");

    fun localisedDisplayStatusText(context: Context): String {
        return when (this) {
            NEW_GAME -> ""
            RESTART_GAME -> ""
        }
    }
}