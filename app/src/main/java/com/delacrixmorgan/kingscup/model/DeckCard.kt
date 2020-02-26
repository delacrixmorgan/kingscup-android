package com.delacrixmorgan.kingscup.model

import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.R

enum class DeckCard(
    val rank: String,
    val title: String,
    val description: String
) {
    Ace("A", App.appContext.getString(R.string.header_waterfall), App.appContext.getString(R.string.body_waterfall));

    fun getLocalisedDefault(context: Context): String{
    }

    fun getLocalisedPersonalised(context: Context): String{
    }
}