package com.delacrixmorgan.kingscup.engine

import android.content.Context
import android.widget.ImageView
import androidx.core.view.isVisible
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.findCardIndex
import com.delacrixmorgan.kingscup.model.ActionType
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SuitType
import com.delacrixmorgan.kingscup.model.TauntType
import java.util.*

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

    val taunt: String
        get() = taunts.shuffled().first()

    val hasWon: Boolean
        get() = turnsLeft == 0

    private val taunts = arrayListOf<String>()

    private val turnsLeft: Int
        get() {
            return cards.count { it.rank == "K" }
        }

    init {
        setupGame(context)
    }

    fun setupGame(context: Context) {
        taunts.clear()
        cards.clear()

        taunts.addAll(TauntType.values().map { it.getLocalisedText(context) })

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

        if (BuildConfig.DEBUG) {
            val spadesCard = Card(
                SuitType.Spade,
                ActionType.CATEGORY.getRankText(),
                ActionType.CATEGORY.getLocalisedHeaderText(context),
                ActionType.CATEGORY.getLocalisedBodyText(context)
            )
            val clubCard = Card(
                SuitType.Club,
                ActionType.JACK.getRankText(),
                ActionType.JACK.getLocalisedHeaderText(context),
                ActionType.JACK.getLocalisedBodyText(context)
            )
            val heartCard = Card(
                SuitType.Heart,
                ActionType.THUMB_MASTER.getRankText(),
                ActionType.THUMB_MASTER.getLocalisedHeaderText(context),
                ActionType.THUMB_MASTER.getLocalisedBodyText(context)
            )
            val diamondCard = Card(
                SuitType.Diamond,
                ActionType.CHICKS.getRankText(),
                ActionType.CHICKS.getLocalisedHeaderText(context),
                ActionType.CHICKS.getLocalisedBodyText(context)
            )
            Collections.swap(cards, 0, cards.findCardIndex(spadesCard))
            Collections.swap(cards, 1, cards.findCardIndex(clubCard))
            Collections.swap(cards, 2, cards.findCardIndex(heartCard))
            Collections.swap(cards, 3, cards.findCardIndex(diamondCard))
        } else {
            cards.shuffle()
        }
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

    fun hasTriggerWin(card: Card) = card.rank == GAME_CARD_KING && turnsLeft == 1
}