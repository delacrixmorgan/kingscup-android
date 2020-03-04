package com.delacrixmorgan.kingscup.game.board

import android.content.Context
import com.delacrixmorgan.kingscup.App
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.TauntType
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

    // TODO: Game Over Needs a Different Taunt context.getString(R.string.game_over_body)
    val taunt: String
        get() = TauntType.values().toList().shuffled().first().getLocalisedText(context)

    /**
     * Transitions
     */

    fun present() {
        when (state) {
            is State.Start, is State.Updating, is State.Winning -> {
                state = State.Presenting
            }
        }
    }

    fun drawCard(card: Card) {
        when (state) {
            is State.Presenting, is State.Updating -> {
                state = State.ShowingDetail(card)
            }
        }
    }

    fun dismissCard(card: Card) {
        when (state) {
            is State.ShowingDetail -> {
                state = State.Updating(card)
            }
        }
    }

    fun pauseGame() {
        when (state) {
            is State.Presenting, is State.Updating -> {
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
                soundEngine.playSound(context, SoundType.GameOver)
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
                soundEngine.playSound(context, SoundType.Whoosh)
                state = State.Completed
            }
        }
    }
}