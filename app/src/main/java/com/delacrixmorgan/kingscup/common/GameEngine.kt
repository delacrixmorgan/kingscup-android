package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Bundle
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

    val GAME_ENGINE_STATUS = "GAME_ENGINE_STATUS"
    val GAME_ENGINE_TAUNT = "GAME_ENGINE_TAUNT"
    val GAME_ENGINE_KING_COUNTER = "GAME_ENGINE_KING_COUNTER"
    val GAME_ENGINE_CUP_VOLUME = "GAME_ENGINE_CUP_VOLUME"

    val deckList = ArrayList<Card>()
    val guideList = ArrayList<String>()
    val tauntList = ArrayList<String>()
    var kingCounter: Int = 4

    companion object : SingletonHolder<GameEngine, Context>(::GameEngine)

    init {
        this.kingCounter = 4
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

    fun removeCard(context: Context, position: Int, cardAdapter: GameCardAdapter, progressBar: ProgressBar) {
        if (this.deckList[position].rank == context.resources.getStringArray(R.array.rank).last()) this.kingCounter--
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
}
