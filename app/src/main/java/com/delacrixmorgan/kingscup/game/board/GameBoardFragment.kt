package com.delacrixmorgan.kingscup.game.board

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.VibratorEngine
import com.delacrixmorgan.kingscup.game.CardListener
import com.delacrixmorgan.kingscup.game.GameCardAdapter
import com.delacrixmorgan.kingscup.game.GameCardFragment
import com.delacrixmorgan.kingscup.game.dialog.GameDialogFragment
import com.delacrixmorgan.kingscup.game.dialog.GameDialogListeners
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.GameType
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_board.*

class GameBoardFragment : Fragment(), Observer<GameBoardStateMachine.State>,
    Animation.AnimationListener, CardListener, GameDialogListeners {

    private val cardAdapter: GameCardAdapter by lazy {
        GameCardAdapter(this)
    }

    private var statusText: String = ""
        set(value) {
            field = value
            statusTextView.startAnimation(statusTextAnimation)
        }

    private val statusTextAnimation: AlphaAnimation by lazy {
        AlphaAnimation(1F, 0F).apply {
            duration = 200
            repeatCount = 1
            repeatMode = Animation.REVERSE
            setAnimationListener(this@GameBoardFragment)
        }
    }

    private val gameMenuDialog: DialogFragment by lazy {
        GameDialogFragment.create(this)
    }

    private lateinit var stateMachine: GameBoardStateMachine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stateMachine = ViewModelProvider(this).get(GameBoardStateMachine::class.java)
        stateMachine.observe(this, this)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    stateMachine.pauseGame()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = cardAdapter
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            context, R.anim.layout_animation_slide_right
        )
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (manager.findFirstVisibleItemPosition() == 0) {
                    progressBar.progress = 0
                } else {
                    progressBar.progress = manager.findLastVisibleItemPosition()
                }
            }
        })
        recyclerView.scheduleLayoutAnimation()

        menuButton.setOnClickListener { stateMachine.pauseGame() }
        restartButton.setOnClickListener { stateMachine.restartGame() }
    }

    /**
     * State Machine
     */

    private val kingImageViews by lazy {
        listOf(kingOneImageView, kingTwoImageView, kingThreeImageView, kingFourImageView)
    }

    override fun onChanged(state: GameBoardStateMachine.State) {
        val context = requireContext()

        when (state) {
            is GameBoardStateMachine.State.Start -> {
                cardAdapter.updateDataSet(stateMachine.gameEngine.cards)
                stateMachine.present()
            }
            is GameBoardStateMachine.State.Presenting -> {
                stateMachine.gameEngine.toggleKingImageView(kingImageViews, volumeImageView)

                // TODO: Toggle King's Cup and End Game
//                when (args.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
//                    3 -> this.kingFourImageView.isVisible = false
//                    2 -> this.kingThreeImageView.isVisible = false
//                    1 -> this.kingTwoImageView.isVisible = false
//                    0 -> {
//                        this.kingOneImageView.isVisible = false
//                        stateMachine.endGame()
//                    }
//                }
            }
            is GameBoardStateMachine.State.ShowingDetail -> {
                val fragment = GameCardFragment.newInstance(state.card, 1, this)
                fragment.enterTransition = Slide(Gravity.BOTTOM).setDuration(200)
                childFragmentManager.commit {
                    add(rootView.id, fragment, fragment.javaClass.simpleName)
                    addToBackStack(fragment.javaClass.simpleName)
                }

                VibratorEngine.vibrate(rootView, VibrateType.Short)
                stateMachine.soundEngine.playSound(context, SoundType.Flip)
            }
            is GameBoardStateMachine.State.Updating -> {
                cardAdapter.removeCard(state.card)

                statusText = stateMachine.taunt
                progressBar.max = cardAdapter.itemCount - 1

                stateMachine.present()
            }
            is GameBoardStateMachine.State.Pausing -> {
                gameMenuDialog.show(requireActivity().supportFragmentManager, javaClass.simpleName)
            }
            is GameBoardStateMachine.State.Winning -> {
                restartButton.show()
                confettiAnimationView.isVisible = true
                confettiAnimationView.playAnimation()
            }
            is GameBoardStateMachine.State.Restarting -> {
                val action = GameBoardFragmentDirections.actionGameBoardFragmentToGameLoadFragment(
                    GameType.RestartGame
                )
                Navigation.findNavController(rootView).navigate(action)
            }
            is GameBoardStateMachine.State.Completed -> {
                Navigation.findNavController(rootView).navigateUp()
            }
        }
    }

    /**
     * CardListener
     */

    override fun onCardSelected(card: Card) {
        stateMachine.drawCard(card)
    }

    override fun onCardDismissed(card: Card) {
        stateMachine.dismissCard(card)
    }

    /**
     * GameMenuDialogListeners
     */
    override fun onGameResumed() {
        stateMachine.resumeGame()
    }

    override fun onGameRestart() {
        stateMachine.restartGame()
    }

    override fun onGameQuit() {
        stateMachine.complete()
    }

    /**
     * Animation.AnimationListener
     */

    override fun onAnimationRepeat(animation: Animation?) {
        statusTextView.text = statusText
    }

    override fun onAnimationEnd(animation: Animation?) = Unit

    override fun onAnimationStart(animation: Animation?) = Unit
}