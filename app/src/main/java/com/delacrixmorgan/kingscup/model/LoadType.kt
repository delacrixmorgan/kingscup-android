package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 27/12/2017.
 **/

enum class LoadType {
    NEW_GAME,
    RESTART_GAME;

    fun getLocalisedText(context: Context): String {
        return when (this) {
            NEW_GAME -> context.getString(R.string.load_type_new_game)
            RESTART_GAME -> context.getString(R.string.load_type_restart_game)
        }
    }
}