package com.delacrixmorgan.kingscup.engine

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.media.MediaPlayer
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.Helper
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
    private var mDeck: ArrayList<Card>? = null
    private var mGuideArray: ArrayList<String>? = null
    private var mNextArray: ArrayList<String>? = null
    private val mMediaPlayers: HashMap<String, MediaPlayer>

    private val deckList = ArrayList<Card>()

    val nextText: String
        get() {
            Collections.shuffle(mNextArray!!, Random(System.nanoTime()))
            return mNextArray!![0]
        }

    val currentCard: Card
        get() = mDeck!![mCurrentCardPosition]

    init {
        mMediaPlayers = HashMap()
        mMediaPlayers.put("KING", MediaPlayer.create(context, R.raw.king))
        mMediaPlayers.put("BACK", MediaPlayer.create(context, R.raw.back))
        mMediaPlayers.put("GAME_OVER", MediaPlayer.create(context, R.raw.game_over))
        mMediaPlayers.put("CARD_FLIP", MediaPlayer.create(context, R.raw.card_flip))
        mMediaPlayers.put("CARD_WHOOSH", MediaPlayer.create(context, R.raw.card_whoosh))
        mMediaPlayers.put("CARD_SHUFFLE", MediaPlayer.create(context, R.raw.card_shuffle))

        buildDeck(context, context.packageName)
        buildArray(context, context.packageName)
    }

    fun playSound(context: Context, key: String) {
        if (context.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true)) {
            if (mMediaPlayers[key] != null) {
                mMediaPlayers[key].start()
            }
        }
    }

    private fun buildDeck(context: Context, packageName: String) {
        mKingCounter = 4
        mCardSelected = false

        val cardSuits = context.resources.getStringArray(R.array.suit)
        val cardRanks = context.resources.getStringArray(R.array.rank)
        val cardHeaders = context.resources.getStringArray(R.array.header)
        val cardBody = context.resources.getStringArray(R.array.body)

        cardSuits.forEach { cardSuit ->
            cardHeaders.indices.mapTo(deckList) { Card(cardSuit, cardRanks[it], cardHeaders[it], cardBody[it]) }
        }

        Collections.shuffle(deckList, Random(System.nanoTime()))
    }

    private fun buildArray(context: Context, packageName: String) {
        mGuideArray = ArrayList()
        mNextArray = ArrayList()

        for (i in 1..3) {
            mGuideArray!!.add(context.getString(context.resources.getIdentifier("guide_" + i, "string", packageName)))
        }

        for (j in 1..10) {
            mNextArray!!.add(context.getString(context.resources.getIdentifier("next_" + j, "string", packageName)))
        }
    }

    fun drawCard(context: Context, i: Int) {
        if ((!mCardSelected)!!) {
            mCurrentCardPosition = i
            mCardSelected = true

            if (mDeck!![i].getmName() == context.resources.getString(R.string.name_13)) {
                mKingCounter--
                playSound(context, "KING")
            }

            Helper.showAddFragmentSlideDown(context as Activity, GameCardFragment())
        }
    }

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

    companion object {
        private val TAG = "GameEngine"
        @get:Synchronized
        var instance: GameEngine? = null
            private set

        @Synchronized
        fun newInstance(context: Context): GameEngine {
            instance = GameEngine(context)
            return instance
        }
    }
}
