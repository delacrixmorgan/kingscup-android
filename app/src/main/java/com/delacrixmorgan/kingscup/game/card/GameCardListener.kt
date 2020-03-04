package com.delacrixmorgan.kingscup.game.card

import com.delacrixmorgan.kingscup.model.Card

interface GameCardListener {
    fun onCardSelected(card: Card)
    fun onCardDismissed(card: Card)
}