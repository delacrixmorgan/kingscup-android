package com.delacrixmorgan.kingscup.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.model.Card
import kotlinx.android.synthetic.main.cell_card_game.view.*

class GameCardAdapter(
    private val listener: CardListener
) : RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>() {

    private var cards = arrayListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_card_game, parent, false
        )
        val cellHeight = (App.appContext.resources.displayMetrics.heightPixels / 2.5).toInt()
        val cellWidth = (cellHeight * (10.0 / 16.0)).toInt()

        itemView.layoutParams.height = cellHeight
        itemView.layoutParams.width = cellWidth

        return GameCardViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount() = cards.size

    fun updateDataSet(cards: ArrayList<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }

    fun removeCard(card: Card) {
        val position = cards.indexOf(card)
        cards.remove(card)
        notifyItemRemoved(position)
    }

    class GameCardViewHolder(
        itemView: View,
        private val listener: CardListener
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: Card) {
            itemView.debugKingImageView.isVisible =
                BuildConfig.DEBUG == true && card.rank == GameEngine.GAME_CARD_KING

            itemView.setOnClickListener {
                listener.onCardSelected(card)
            }
        }
    }
}