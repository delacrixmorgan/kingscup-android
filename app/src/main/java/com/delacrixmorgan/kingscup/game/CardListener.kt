package com.delacrixmorgan.kingscup.game

/**
 * CardListener
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

interface CardListener {
    fun onCardSelected(position: Int)
    fun onCardDismissed(position: Int)
}