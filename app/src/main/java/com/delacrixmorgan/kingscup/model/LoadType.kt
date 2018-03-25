package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * LoadType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

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