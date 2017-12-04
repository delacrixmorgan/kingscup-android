package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ProgressBar
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.game.GameCardAdapter
import com.delacrixmorgan.kingscup.model.Card
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameEngine private constructor(context: Context) {
    companion object {
        const val GAME_ENGINE_TAUNT = "GAME_ENGINE_TAUNT"
        const val GAME_ENGINE_STATUS = "GAME_ENGINE_STATUS"
        const val GAME_ENGINE_CUP_VOLUME = "GAME_ENGINE_CUP_VOLUME"
        const val GAME_ENGINE_KING_COUNTER = "GAME_ENGINE_KING_COUNTER"

        @Volatile private lateinit var GameEngineInstance: GameEngine

        fun newInstance(context: Context): GameEngine {
            this.GameEngineInstance = GameEngine(context)
            return this.GameEngineInstance
        }

        fun getInstance(): GameEngine = this.GameEngineInstance
    }

    val deckList = ArrayList<Card>()
    val guideList = ArrayList<String>()

    private val tauntList = ArrayList<String>()
    private var kingCounter: Int = 4
    private var kingRank: String = "K"
    private var vibrator: Vibrator

    init {
        this.kingCounter = 4
        this.kingRank = context.resources.getStringArray(R.array.rank).last()
        this.vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        this.deckList.clear()
        this.guideList.clear()
        this.buildGame(context)
    }

    private fun buildGame(context: Context) {
        val cardSuits = context.resources.getStringArray(R.array.suit)
        val cardRanks = context.resources.getStringArray(R.array.rank)
        val cardHeaders = context.resources.getStringArray(R.array.header)
        val cardBody = context.resources.getStringArray(R.array.body)

        val gameGuides = context.resources.getStringArray(R.array.guide)
        val gameTaunts = context.resources.getStringArray(R.array.taunt)

        cardSuits.forEach { cardSuit -> cardHeaders.indices.mapTo(deckList) { Card(cardSuit, cardRanks[it], cardHeaders[it], cardBody[it]) } }
        gameGuides.forEach { gameGuide -> guideList.add(gameGuide) }
        gameTaunts.forEach { gameTaunt -> tauntList.add(gameTaunt) }

        Collections.shuffle(deckList, Random(System.nanoTime()))
    }

    fun checkWin(card: Card): Boolean {
        if (card.rank == this.kingRank && kingCounter == 1) {
            return true
        }
        return false
    }

    fun removeCard(position: Int, cardAdapter: GameCardAdapter, progressBar: ProgressBar) {
        if (this.deckList[position].rank == this.kingRank) {
            this.kingCounter--
        }

        this.deckList.removeAt(position)

        cardAdapter.notifyItemRemoved(position)
        progressBar.max--
    }

    fun updateGraphicStatus(context: Context): Bundle {
        Collections.shuffle(tauntList, Random(System.nanoTime()))
        val args = Bundle()

        val status: String
        var taunt = tauntList.first()
        val volume: Int

        when (kingCounter) {
            0 -> {
                status = context.getString(R.string.game_over_header)
                taunt = context.getString(R.string.game_over_body)
                volume = R.drawable.cup_volume_4
            }

            1 -> {
                status = context.getString(R.string.counter_1_king_left)
                volume = R.drawable.cup_volume_3
            }

            2 -> {
                status = context.getString(R.string.counter_2_king_left)
                volume = R.drawable.cup_volume_2
            }

            3 -> {
                status = context.getString(R.string.counter_3_king_left)
                volume = R.drawable.cup_volume_1
            }

            else -> {
                status = context.getString(R.string.counter_4_king_left)
                volume = R.drawable.cup_whole
            }
        }

        args.putString(GAME_ENGINE_STATUS, status)
        args.putString(GAME_ENGINE_TAUNT, taunt)
        args.putInt(GAME_ENGINE_KING_COUNTER, kingCounter)
        args.putInt(GAME_ENGINE_CUP_VOLUME, volume)

        return args
    }

    fun vibrateFeedback() {
        val vibrateDuration = 250L

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.vibrator.vibrate(VibrationEffect.createOneShot(vibrateDuration, -1))
        } else {
            this.vibrator.vibrate(vibrateDuration)
        }
    }
}
