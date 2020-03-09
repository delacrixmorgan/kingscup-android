package com.delacrixmorgan.kingscup.game.dialog

interface GameDialogListeners {
    fun onGameResumed()
    fun onGameRestart()
    fun onGameQuit()
}