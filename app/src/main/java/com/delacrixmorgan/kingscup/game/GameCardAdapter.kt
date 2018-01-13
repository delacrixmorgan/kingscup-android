package com.delacrixmorgan.kingscup.game

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 17/03/2017.
 **/

class GameCardAdapter(private val selectionListener: CardListener, private val deckSize: Int) : RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        return GameCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_game_card, parent, false))
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        holder.selectCard.setOnClickListener { this.selectionListener.onCardSelected(holder.adapterPosition) }
    }

    override fun getItemCount(): Int {
        return deckSize
    }
    
    class GameCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var selectCard: LinearLayout = itemView.findViewById(R.id.view_card_layout)
    }
}