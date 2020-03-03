package com.delacrixmorgan.kingscup.model

import android.content.Context
import android.os.Parcelable
import com.delacrixmorgan.kingscup.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class GameType : Parcelable {
    NewGame,
    RestartGame;

    fun getLocalisedText(context: Context): String {
        return when (this) {
            NewGame -> context.getString(R.string.load_type_new_game)
            RestartGame -> context.getString(R.string.load_type_restart_game)
        }
    }
}