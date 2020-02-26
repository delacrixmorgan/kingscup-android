package com.delacrixmorgan.kingscup.game.board

import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.Card
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

    val gameEngine by lazy {
        GameEngine.getInstance(App.appContext)
    }

    val soundEngine by lazy {
        SoundEngine.getInstance(App.appContext)
    }

    val cards: ArrayList<Card>
        get() {
            return gameEngine.getCards()
        }

    /**
     * Transitions
     */

    fun present() {
        when (state) {
            is State.Start -> {
                state = State.Presenting
            }

            is State.Winning -> {
                state = State.Presenting
            }
        }
    }

    fun drawCard(card: Card) {
        when (state) {
            is State.Presenting -> {
                state = State.ShowingDetail
            }
        }
    }

    fun dismissCard(card: Card) {
        when (state) {
            is State.ShowingDetail -> {
                state = State.Presenting
            }
        }
    }

    fun pauseGame() {
        when (state) {
            is State.Presenting -> {
                state = State.Pausing
            }
        }
    }

    fun resumeGame() {
        when (state) {
            is State.Pausing -> {
                state = State.Presenting
            }
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
        }
    }

    fun endGame() {
        when (state) {
            is State.Presenting -> {
                state = State.Winning
            }

            is State.Winning -> {
                state = State.Completed
            }
        }
    }

    fun complete() {
        when (state) {
            is State.Pausing -> {
                state = State.Completed
            }

            is State.Winning -> {
                state = State.Completed
            }
        }
    }
}