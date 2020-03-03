package com.delacrixmorgan.kingscup.engine

import android.content.Context
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.ActionType
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SuitType

class GameEngine private constructor(context: Context) {

    companion object {
        const val GAME_CARD_KING = "K"

        @Volatile
        private var INSTANCE: GameEngine? = null

        fun getInstance(context: Context): GameEngine {
            return INSTANCE
                ?: synchronized(this) {
                    GameEngine(context).also {
                        INSTANCE = it
                    }
                }
        }
    }

    val cards = arrayListOf<Card>()

    private val turnsLeft: Int
        get() {
            return cards.count { it.rank == "K" }
        }

    val cupVolumeResId: Int
        get() {
            return when (turnsLeft) {
                0 -> R.drawable.ic_cup_volume_4
                1 -> R.drawable.ic_cup_volume_3
                2 -> R.drawable.ic_cup_volume_2
                3 -> R.drawable.ic_cup_volume_1
                else -> R.drawable.ic_cup_whole
            }
        }

    init {
        setupGame(context)
    }

    fun setupGame(context: Context) {
        cards.clear()

        SuitType.values().forEach { suit ->
            ActionType.values().let { actionTypes ->
                actionTypes.indices.mapTo(cards) {
                    Card(
                        suit,
                        actionTypes[it].getRankText(),
                        actionTypes[it].getLocalisedHeaderText(context),
                        actionTypes[it].getLocalisedBodyText(context)
                    )
                }
            }
        }
        cards.shuffle()
    }

    // TODO: Restructure Win Factor
    fun checkWin(card: Card) = card.rank == GAME_CARD_KING && turnsLeft == 0
}