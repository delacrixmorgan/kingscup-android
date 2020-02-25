package com.delacrixmorgan.kingscup.game

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.model.Card
import kotlinx.android.synthetic.main.cell_card_game.view.*

/**
 * GameCardAdapter
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameCardAdapter(
        private val resources: Resources,
        private val listener: CardListener
) : RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>() {

    private var cards = arrayListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_card_game, parent, false)
        val cellHeight = (this.resources.displayMetrics.heightPixels / 2.5).toInt()
        val cellWidth = (cellHeight * (10.0 / 16.0)).toInt()

        itemView.layoutParams.height = cellHeight
        itemView.layoutParams.width = cellWidth

        return GameCardViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        val card = this.cards[position]
        holder.updateData(card)
    }

    override fun getItemCount() = this.cards.size

    fun updateDataSet(cards: ArrayList<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }

    fun removeCard(position: Int) {
        notifyItemRemoved(position)
    }

    class GameCardViewHolder(itemView: View, private val listener: CardListener) : RecyclerView.ViewHolder(itemView) {
        private lateinit var card: Card

        init {
            itemView.setOnClickListener {
                this.listener.onCardSelected(adapterPosition)
            }
        }

        fun updateData(card: Card) {
            this.card = card
            this.itemView.debugKingImageView.isVisible = BuildConfig.DEBUG == true && this.card.rank == GameEngine.GAME_CARD_KING
        }
    }
}