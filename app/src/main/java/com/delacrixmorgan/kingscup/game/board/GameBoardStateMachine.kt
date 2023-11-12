package com.delacrixmorgan.kingscup.game.board

import android.content.Context
import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.statemachine.FSMState
import com.delacrixmorgan.kingscup.statemachine.ObservableStateMachine

class GameBoardStateMachine : ObservableStateMachine<GameBoardStateMachine.State>(State.Start) {
    sealed class State : FSMState {
        object Start : State()
        object Presenting : State()
        class Updating(val card: Card) : State()
        class ShowingDetail(val card: Card) : State()
        object Pausing : State()
        object Winning : State()
        object Restarting : State()
        object Completed : State()
    }

    val context: Context
        get() = App.appContext

    val gameEngine by lazy {
        GameEngine.getInstance(context)
    }

    val soundEngine by lazy {
        SoundEngine.getInstance(context)
    }

    /**
     * Transitions
     */

    fun present() {
        when (state) {
            is State.Start, is State.Updating, is State.Winning -> {
                state = State.Presenting
            }
            else -> Unit
        }
    }

    fun drawCard(card: Card) {
        when (state) {
            is State.Presenting, is State.Updating -> {
                state = State.ShowingDetail(card)
            }
            else -> Unit
        }
    }

    fun dismissCard(card: Card) {
        when (state) {
            is State.ShowingDetail -> {
                state = State.Updating(card)
            }
            else -> Unit
        }
    }

    fun pauseGame() {
        when (state) {
            is State.Presenting, is State.Updating, is State.Winning -> {
                state = State.Pausing
            }
            else -> Unit
        }
    }

    fun resumeGame() {
        when (state) {
            is State.Pausing -> {
                state = if (gameEngine.hasWon) {
                    State.Winning
                } else {
                    State.Presenting
                }
            }
            else -> Unit
        }
    }

    fun restartGame() {
        when (state) {
            is State.Pausing -> {
                state = State.Restarting
            }
            is State.Winning -> {
                state = State.Restarting
            }
            else -> Unit
        }
    }

    fun endGame() {
        when (state) {
            is State.Presenting, is State.Updating -> {
                soundEngine.playSound(context, SoundType.GameOver)
                state = State.Winning
            }

            is State.Winning -> {
                state = State.Completed
            }
            else -> Unit
        }
    }

    fun complete() {
        when (state) {
            is State.Pausing -> {
                state = State.Completed
            }

            is State.Winning -> {
                soundEngine.playSound(context, SoundType.Whoosh)
                state = State.Completed
            }
            else -> Unit
        }
    }
}