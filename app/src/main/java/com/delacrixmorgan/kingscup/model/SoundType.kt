package com.delacrixmorgan.kingscup.model

import com.delacrixmorgan.kingscup.R

enum class SoundType(var rawID: Int, var resourceID: Int) {
    King(R.raw.king, 0),
    Flip(R.raw.flip, 0),
    Oooh(R.raw.oooh, 0),
    Click(R.raw.click, 0),
    Whoosh(R.raw.whoosh, 0),
    GameOver(R.raw.game_over, 0)
}