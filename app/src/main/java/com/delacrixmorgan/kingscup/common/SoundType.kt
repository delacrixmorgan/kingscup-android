package com.delacrixmorgan.kingscup.common

import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 28/12/2017.
 **/

enum class SoundType(var rawID: Int, var resourceID: Int) {
    KING(R.raw.king, 0),
    FLIP(R.raw.flip, 0),
    OOOH(R.raw.oooh, 0),
    WHOOSH(R.raw.whoosh, 0),
    GAME_OVER(R.raw.game_over, 0);
}