package com.delacrixmorgan.kingscup.engine

import android.content.Context
import android.widget.ImageView
import androidx.core.view.isVisible
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

    fun toggleKingImageView(
        kingImageViews: List<ImageView>,
        volumeImageView: ImageView
    ) {
        when (turnsLeft) {
            4 -> {
                kingImageViews.forEach { it.isVisible = true }
                volumeImageView.setImageResource(R.drawable.ic_cup_whole)
            }
            3 -> {
                kingImageViews[3].isVisible = false
                volumeImageView.setImageResource(R.drawable.ic_cup_volume_1)
            }
            2 -> {
                kingImageViews[2].isVisible = false
                volumeImageView.setImageResource(R.drawable.ic_cup_volume_2)
            }
            1 -> {
                kingImageViews[1].isVisible = false
                volumeImageView.setImageResource(R.drawable.ic_cup_volume_3)
            }
            0 -> {
                kingImageViews[0].isVisible = false
                volumeImageView.setImageResource(R.drawable.ic_cup_volume_4)
            }
        }
    }

    // TODO: Restructure Win Factor
    fun checkWin(card: Card) = card.rank == GAME_CARD_KING && turnsLeft == 0
}