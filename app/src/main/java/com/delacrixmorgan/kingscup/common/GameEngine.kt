package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.model.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * GameEngine
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameEngine private constructor(context: Context) {

    companion object {
        const val GAME_CARD_KING = "K"

        const val GAME_ENGINE_TAUNT = "GAME_ENGINE_TAUNT"
        const val GAME_ENGINE_CUP_VOLUME = "GAME_ENGINE_CUP_VOLUME"
        const val GAME_ENGINE_KING_COUNTER = "GAME_ENGINE_KING_COUNTER"

        @Volatile
        private var INSTANCE: GameEngine? = null

        fun getInstance(context: Context): GameEngine {
            return INSTANCE ?: synchronized(this) {
                GameEngine(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val tauntList = ArrayList<String>()
    private val deckList = ArrayList<Card>()
    private var kingCounter: Int = 4
    private var vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    init {
        setupGame(context)
    }

    fun setupGame(context: Context) {
        this.kingCounter = 4
        this.deckList.clear()

        buildGameEngine(context)
    }

    private fun buildGameEngine(context: Context) {
        SuitType.values().forEach { suit ->
            ActionType.values().let { actionTypes ->
                actionTypes.indices.mapTo(this.deckList) {
                    Card(suit, actionTypes[it].getRankText(), actionTypes[it].getLocalisedHeaderText(context), actionTypes[it].getLocalisedBodyText(context))
                }
            }
        }

        TauntType.values().forEach { taunt ->
            this.tauntList.add(taunt.getLocalisedText(context))
        }

        this.deckList.shuffle(Random(System.nanoTime()))
    }

    fun removeCard(position: Int) {
        if (this.deckList[position].rank == GAME_CARD_KING) {
            this.kingCounter--
        }

        this.deckList.removeAt(position)
    }

    fun updateGraphicStatus(context: Context): Bundle {
        this.tauntList.shuffle(Random(System.nanoTime()))

        val volume: Int
        val args = Bundle()
        var taunt = this.tauntList.first()

        when (this.kingCounter) {
            0 -> {
                taunt = context.getString(R.string.game_over_body)
                volume = R.drawable.ic_cup_volume_4
            }

            1 -> volume = R.drawable.ic_cup_volume_3
            2 -> volume = R.drawable.ic_cup_volume_2
            3 -> volume = R.drawable.ic_cup_volume_1
            else -> volume = R.drawable.ic_cup_whole
        }

        args.putString(GAME_ENGINE_TAUNT, taunt)
        args.putInt(GAME_ENGINE_CUP_VOLUME, volume)
        args.putInt(GAME_ENGINE_KING_COUNTER, this.kingCounter)

        return args
    }

    fun vibrateFeedback(context: Context, view: View, vibrateType: VibrateType) {
        val preference = PreferenceHelper.getPreference(context)
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        if (vibratePreference) {
            when (vibrateType) {
                VibrateType.SHORT -> {
                    view.performHapticContextClick()
                }

                VibrateType.LONG -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        this.vibrator.vibrate(VibrationEffect.createOneShot(vibrateType.duration, -1))
                    } else {
                        this.vibrator.vibrate(vibrateType.duration)
                    }
                }
            }
        }
    }

    fun getCards() = this.deckList
    fun getCardByPosition(position: Int) = this.deckList.getOrNull(position)
    fun checkWin(card: Card) = card.rank == GAME_CARD_KING && this.kingCounter == 1
}