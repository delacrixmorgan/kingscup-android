package com.delacrixmorgan.kingscup.common

import com.delacrixmorgan.kingscup.R

/**
 * SoundType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class SoundType(var rawID: Int, var resourceID: Int) {
    KING(R.raw.king, 0),
    FLIP(R.raw.flip, 0),
    OOOH(R.raw.oooh, 0),
    CLICK(R.raw.click, 0),
    WHOOSH(R.raw.whoosh, 0),
    GAME_OVER(R.raw.game_over, 0);
}