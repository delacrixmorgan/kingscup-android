package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ProgressBar
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.game.GameCardAdapter
import com.delacrixmorgan.kingscup.game.VibrateType
import com.delacrixmorgan.kingscup.model.Card
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Delacrix Morgan on 09/10/2016.
 **/

class GameEngine private constructor(context: Context) {

    companion object {
        const val GAME_ENGINE_TAUNT = "GAME_ENGINE_TAUNT"
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

    private val guideList = ArrayList<String>()
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

        this.buildGameEngine(context)
    }

    private fun buildGameEngine(context: Context) {

        with(context.resources) {
            val cardSuits = getStringArray(R.array.suit)
            val cardRanks = getStringArray(R.array.rank)
            val cardHeaders = getStringArray(R.array.header)
            val cardBody = getStringArray(R.array.body)

            val gameGuides = getStringArray(R.array.guide)
            val gameTaunts = getStringArray(R.array.taunt)

            cardSuits.forEach { cardSuit -> cardHeaders.indices.mapTo(deckList) { Card(cardSuit, cardRanks[it], cardHeaders[it], cardBody[it]) } }
            gameGuides.forEach { gameGuide -> guideList.add(gameGuide) }
            gameTaunts.forEach { gameTaunt -> tauntList.add(gameTaunt) }
        }

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
            this.vibrator.vibrate(VibrationEffect.createOneShot(vibrateType.duration, -1))
        } else {
            this.vibrator.vibrate(vibrateType.duration)
        }
    }
}
