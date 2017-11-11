package com.delacrixmorgan.kingscup.common

import android.content.Context
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

    private var mCurrentCardPosition: Int = 0
    private var mCardSelected: Boolean? = null

    private var kingCounter: Int = 4

    val deckList = ArrayList<Card>()
    val guideList = ArrayList<String>()
    val tauntList = ArrayList<String>()

    companion object : SingletonHolder<GameEngine, Context>(::GameEngine)

    init {
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
        Collections.shuffle(tauntList, Random(System.nanoTime()))
    }

    fun removeCard(position: Int, cardAdapter: GameCardAdapter, progressBar: ProgressBar) {
        deckList.removeAt(position)
        cardAdapter.notifyItemRemoved(position)
        progressBar.max--
    }

//    fun updateCardAdapter(adapter: GameCardAdapter) {
//        mDeck!!.removeAt(mCurrentCardPosition)
//        adapter.notifyItemRemoved(mCurrentCardPosition)
//
//        mCurrentCardPosition = 0
//        mCardSelected = false
//
//        if (kingCounter < 1) {
//            mDeck!!.clear()
//            adapter.notifyDataSetChanged()
//        }
//    }
}
