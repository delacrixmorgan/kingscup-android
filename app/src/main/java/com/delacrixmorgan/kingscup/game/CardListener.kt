package com.delacrixmorgan.kingscup.game

import com.delacrixmorgan.kingscup.model.Card

interface CardListener {
    fun onCardSelected(card: Card)
    fun onCardDismissed(card: Card)
}