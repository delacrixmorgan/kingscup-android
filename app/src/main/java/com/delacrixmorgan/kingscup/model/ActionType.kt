package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 08/03/2018.
 **/

enum class ActionType {
    WATERFALL,
    YOU,
    ME,
    THUMB_MASTER,
    DUDES,
    CHICKS,
    HEAVEN,
    MATE,
    SNAKE_EYES,
    CATEGORY,
    JACK,
    QUESTIONS,
    KING;

    fun getRankText(): String {
        return when (this) {
            WATERFALL -> "A"
            YOU -> "2"
            ME -> "3"
            THUMB_MASTER -> "4"
            DUDES -> "5"
            CHICKS -> "6"
            HEAVEN -> "7"
            MATE -> "8"
            SNAKE_EYES -> "9"
            CATEGORY -> "10"
            JACK -> "J"
            QUESTIONS -> "Q"
            KING -> "K"
        }
    }

    fun getLocalisedHeaderText(context: Context): String {
        return when (this) {
            WATERFALL -> context.getString(R.string.load_type_new_game)
            YOU -> context.getString(R.string.load_type_new_game)
            ME -> context.getString(R.string.load_type_new_game)
            THUMB_MASTER -> context.getString(R.string.load_type_new_game)
            DUDES -> context.getString(R.string.load_type_new_game)
            CHICKS -> context.getString(R.string.load_type_new_game)
            HEAVEN -> context.getString(R.string.load_type_new_game)
            MATE -> context.getString(R.string.load_type_new_game)
            SNAKE_EYES -> context.getString(R.string.load_type_new_game)
            CATEGORY -> context.getString(R.string.load_type_new_game)
            JACK -> context.getString(R.string.load_type_new_game)
            QUESTIONS -> context.getString(R.string.load_type_new_game)
            KING -> context.getString(R.string.load_type_new_game)
        }
    }

    fun getLocalisedBodyText(context: Context): String {
        return when (this) {
            WATERFALL -> context.getString(R.string.load_type_new_game)
            YOU -> context.getString(R.string.load_type_new_game)
            ME -> context.getString(R.string.load_type_new_game)
            THUMB_MASTER -> context.getString(R.string.load_type_new_game)
            DUDES -> context.getString(R.string.load_type_new_game)
            CHICKS -> context.getString(R.string.load_type_new_game)
            HEAVEN -> context.getString(R.string.load_type_new_game)
            MATE -> context.getString(R.string.load_type_new_game)
            SNAKE_EYES -> context.getString(R.string.load_type_new_game)
            CATEGORY -> context.getString(R.string.load_type_new_game)
            JACK -> context.getString(R.string.load_type_new_game)
            QUESTIONS -> context.getString(R.string.load_type_new_game)
            KING -> context.getString(R.string.load_type_new_game)
        }
    }
}