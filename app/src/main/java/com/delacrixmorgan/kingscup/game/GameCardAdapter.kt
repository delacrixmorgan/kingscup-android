package com.delacrixmorgan.kingscup.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delacrixmorgan.kingscup.R

/**
 * GameCardAdapter
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameCardAdapter(
        private val cellHeight: Int,
        private val cellWidth: Int,
        private var deckSize: Int,
        private val listener: CardListener
) : RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_game_card, parent, false)

        itemView.layoutParams.height = cellHeight
        itemView.layoutParams.width = cellWidth

        return GameCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) = Unit

    override fun getItemCount() = this.deckSize

    inner class GameCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (this@GameCardAdapter.deckSize != 0) {
                    this@GameCardAdapter.deckSize--
                    this@GameCardAdapter.listener.onCardSelected(this.adapterPosition)
                }
            }
        }
    }
}