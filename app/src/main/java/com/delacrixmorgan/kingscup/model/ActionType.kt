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
            WATERFALL -> context.getString(R.string.header_waterfall)
            YOU -> context.getString(R.string.header_you)
            ME -> context.getString(R.string.header_me)
            THUMB_MASTER -> context.getString(R.string.header_thumb_master)
            DUDES -> context.getString(R.string.header_dudes)
            CHICKS -> context.getString(R.string.header_chicks)
            HEAVEN -> context.getString(R.string.header_heaven)
            MATE -> context.getString(R.string.header_mate)
            SNAKE_EYES -> context.getString(R.string.header_snake_eyes)
            CATEGORY -> context.getString(R.string.header_category)
            JACK -> context.getString(R.string.header_jack)
            QUESTIONS -> context.getString(R.string.header_questions)
            KING -> context.getString(R.string.header_king)
        }
    }

    fun getLocalisedBodyText(context: Context): String {
        return when (this) {
            WATERFALL -> context.getString(R.string.body_waterfall)
            YOU -> context.getString(R.string.body_you)
            ME -> context.getString(R.string.body_me)
            THUMB_MASTER -> context.getString(R.string.body_thumb_master)
            DUDES -> context.getString(R.string.body_dudes)
            CHICKS -> context.getString(R.string.body_chicks)
            HEAVEN -> context.getString(R.string.body_heaven)
            MATE -> context.getString(R.string.body_mate)
            SNAKE_EYES -> context.getString(R.string.body_snake_eyes)
            CATEGORY -> context.getString(R.string.body_category)
            JACK -> context.getString(R.string.body_jack)
            QUESTIONS -> context.getString(R.string.body_questions)
            KING -> context.getString(R.string.body_king)
        }
    }
}