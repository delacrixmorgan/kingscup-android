package com.delacrixmorgan.kingscup.common

import android.app.Activity
import android.content.Context
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.game.GameCardAdapter
import com.delacrixmorgan.kingscup.game.GameCardFragment
import com.delacrixmorgan.kingscup.model.Card
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameEngine private constructor(context: Context) {

    private var mKingCounter: Int = 0
    private var mCurrentCardPosition: Int = 0
    private var mCardSelected: Boolean? = null

    val deckList = ArrayList<Card>()
    private val guideList = ArrayList<String>()
    private val tauntList = ArrayList<String>()

    companion object : SingletonHolder<GameEngine, Context>(::GameEngine)

    init {
        mKingCounter = 4
        mCardSelected = false

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

    fun drawCard(position: Int, cardAdapter: GameCardAdapter) {
        deckList.removeAt(position)
        cardAdapter.notifyItemRemoved(position)
        
        if ((!mCardSelected)!!) {
            mCurrentCardPosition = i
            mCardSelected = true

            Helper.showAddFragmentSlideDown(context as Activity, GameCardFragment())
        }
    }

    val nextText: String
        get() {
            Collections.shuffle(mNextArray!!, Random(System.nanoTime()))
            return mNextArray!![0]
        }

    val currentCard: Card
        get() = mDeck!![mCurrentCardPosition]

    fun updateCardAdapter(adapter: GameCardAdapter) {
        mDeck!!.removeAt(mCurrentCardPosition)
        adapter.notifyItemRemoved(mCurrentCardPosition)

        mCurrentCardPosition = 0
        mCardSelected = false

        if (mKingCounter < 1) {
            mDeck!!.clear()
            adapter.notifyDataSetChanged()
        }
    }

    fun getmKingCounter(): Int {
        return mKingCounter
    }

    fun getmDeck(): ArrayList<Card>? {
        return mDeck
    }

    fun getmGuideArray(): ArrayList<String>? {
        return mGuideArray
    }
}
