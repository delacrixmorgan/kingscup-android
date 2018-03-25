package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.delacrixmorgan.kingscup.R
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
        const val GAME_ENGINE_TAUNT = "GAME_ENGINE_TAUNT"
        const val GAME_ENGINE_CUP_VOLUME = "GAME_ENGINE_CUP_VOLUME"
        const val GAME_ENGINE_KING_COUNTER = "GAME_ENGINE_KING_COUNTER"

        @Volatile
        private lateinit var GameEngineInstance: GameEngine

        fun newInstance(context: Context): GameEngine {
            GameEngineInstance = GameEngine(context)
            return GameEngineInstance
        }

        fun getInstance(): GameEngine = this.GameEngineInstance
    }

    private val guideList = ArrayList<String>()
    private val tauntList = ArrayList<String>()
    private val deckList = ArrayList<Card>()
    private var kingCounter: Int = 4
    private var kingRank: String = "K"
    private var vibrator: Vibrator

    init {
        kingCounter = 4
        kingRank = ActionType.values().last().getRankText()
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        deckList.clear()
        guideList.clear()

        buildGameEngine(context)
    }

    private fun buildGameEngine(context: Context) {
        
        SuitType.values().forEach { suit ->
            ActionType.values().let { actionTypes ->
                actionTypes.indices.mapTo(deckList) {
                    Card(suit.getLocalisedText(context),
                            actionTypes[it].getRankText(),
                            actionTypes[it].getLocalisedHeaderText(context),
                            actionTypes[it].getLocalisedBodyText(context))
                }
            }
        }

        GuideType.values().forEach { guide ->
            guideList.add(guide.getLocalisedText(context))
        }

        TauntType.values().forEach { taunt ->
            tauntList.add(taunt.getLocalisedText(context))
        }

        deckList.shuffle(Random(System.nanoTime()))
    }

    fun checkWin(card: Card): Boolean {
        return card.rank == kingRank && kingCounter == 1
    }

    fun removeCard(position: Int) {
        if (deckList[position].rank == kingRank) {
            kingCounter--
        }

        deckList.removeAt(position)
    }

    fun updateGraphicStatus(context: Context): Bundle {
        tauntList.shuffle(Random(System.nanoTime()))

        val volume: Int
        val args = Bundle()
        var taunt = tauntList.first()

        when (kingCounter) {
            0 -> {
                taunt = context.getString(R.string.game_over_body)
                volume = R.drawable.cup_volume_4
            }

            1 -> volume = R.drawable.cup_volume_3
            2 -> volume = R.drawable.cup_volume_2
            3 -> volume = R.drawable.cup_volume_1
            else -> volume = R.drawable.cup_whole
        }

        args.putString(GAME_ENGINE_TAUNT, taunt)
        args.putInt(GAME_ENGINE_CUP_VOLUME, volume)
        args.putInt(GAME_ENGINE_KING_COUNTER, kingCounter)

        return args
    }

    fun vibrateFeedback(vibrateType: VibrateType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrateType.duration, -1))
        } else {
            vibrator.vibrate(vibrateType.duration)
        }
    }

    fun getCardByPosition(position: Int): Card {
        return deckList[position]
    }

    fun getDeckSize(): Int {
        return deckList.size
    }
}