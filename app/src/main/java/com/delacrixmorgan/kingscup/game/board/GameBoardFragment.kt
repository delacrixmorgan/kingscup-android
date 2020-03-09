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
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.VibratorEngine
import com.delacrixmorgan.kingscup.game.card.GameCardAdapter
import com.delacrixmorgan.kingscup.game.card.GameCardFragment
import com.delacrixmorgan.kingscup.game.card.GameCardListener
import com.delacrixmorgan.kingscup.game.dialog.GameDialogFragment
import com.delacrixmorgan.kingscup.game.dialog.GameDialogListeners
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.GameType
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_board.*

class GameBoardFragment : Fragment(), Observer<GameBoardStateMachine.State>,
    Animation.AnimationListener,
    GameCardListener, GameDialogListeners {

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

    private val cardAdapter: GameCardAdapter by lazy {
        GameCardAdapter(this)
    }

    private val gameMenuDialog: DialogFragment by lazy {
        GameDialogFragment.create(this)
    }

    private val kingImageViews by lazy {
        listOf(kingOneImageView, kingTwoImageView, kingThreeImageView, kingFourImageView)
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

        debugTextView.isVisible = BuildConfig.DEBUG
        debugTextView.setOnClickListener { debugTextView.isVisible = false }
    }

    /**
     * State Machine
     */

    override fun onChanged(state: GameBoardStateMachine.State) {
        val context = requireContext()
        debugTextView.text = "State: ${state.javaClass.simpleName}"

        when (state) {
            is GameBoardStateMachine.State.Start -> {
                cardAdapter.updateDataSet(stateMachine.gameEngine.cards)
                statusText = getString(R.string.board_title_lets_begin)
                stateMachine.gameEngine.toggleKingImageView(kingImageViews, volumeImageView)

                stateMachine.present()
            }
            is GameBoardStateMachine.State.Presenting -> Unit
            
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
                statusText = stateMachine.taunt
                cardAdapter.removeCard(state.card)
                progressBar.max = cardAdapter.itemCount - 1
                stateMachine.gameEngine.toggleKingImageView(kingImageViews, volumeImageView)

                if (state.hasWin == true) {
                    stateMachine.endGame()
                } else {
                    stateMachine.present()
                }
            }
            is GameBoardStateMachine.State.Pausing -> {
                gameMenuDialog.show(requireActivity().supportFragmentManager, javaClass.simpleName)
            }
            is GameBoardStateMachine.State.Winning -> {
                restartButton.show()
                confettiAnimationView.isVisible = true
                confettiAnimationView.playAnimation()

                statusText = context.getString(R.string.game_over_body)
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

    override fun onCardDismissed(card: Card, hasWin: Boolean?) {
        stateMachine.dismissCard(card, hasWin)
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