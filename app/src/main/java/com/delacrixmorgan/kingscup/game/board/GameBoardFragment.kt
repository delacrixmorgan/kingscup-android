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
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.transition.Slide
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.setupProgressBar
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.VibratorEngine
import com.delacrixmorgan.kingscup.game.CardListener
import com.delacrixmorgan.kingscup.game.GameCardAdapter
import com.delacrixmorgan.kingscup.game.GameCardFragment
import com.delacrixmorgan.kingscup.game.GameMenuDialog
import com.delacrixmorgan.kingscup.model.GameType
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_board.*

class GameBoardFragment : Fragment(), Observer<GameBoardStateMachine.State>, CardListener {

    private lateinit var cardAdapter: GameCardAdapter

    private var isCardSelected: Boolean = false
    private var isConfettiLaunched = false
    private var statusText = ""

    private val statusTextAnimation: AlphaAnimation by lazy {
        AlphaAnimation(1F, 0F).apply {
            duration = 200
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
    }

    private lateinit var stateMachine: GameBoardStateMachine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stateMachine = ViewModelProvider(this).get(GameBoardStateMachine::class.java)
        stateMachine.observe(this, this)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                launchGameDialogFragment()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.volumeImageView.setImageResource(R.drawable.ic_cup_whole)

        this.statusText = getString(R.string.board_title_lets_begin)
        this.isCardSelected = false
        this.cardAdapter = GameCardAdapter(resources = this.resources, listener = this)
        this.cardAdapter.updateDataSet(stateMachine.cards)

        with(recyclerView) {
            removeAllViews()
            adapter = cardAdapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
            scheduleLayoutAnimation()
        }

        setupProgressBar(recyclerView.layoutManager, recyclerView, progressBar)

        this.restartButton.setOnClickListener { startNewGame() }
        this.menuButton.setOnClickListener { launchGameDialogFragment() }

        this.statusTextView.startAnimation(this.statusTextAnimation)
        this.statusTextAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                statusTextView.text = statusText
            }

            override fun onAnimationEnd(animation: Animation?) = Unit
            override fun onAnimationStart(animation: Animation?) = Unit
        })
    }

    private fun launchGameDialogFragment() {
        val dialog = GameMenuDialog()
        dialog.show(requireNotNull(activity?.supportFragmentManager), dialog.javaClass.simpleName)
    }

    private fun startNewGame() {
        val action = GameBoardFragmentDirections.actionGameBoardFragmentToGameLoadFragment(GameType.RESTART_GAME)
        Navigation.findNavController(this.rootView).navigate(action)
    }

    override fun onChanged(state: GameBoardStateMachine.State) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * CardListener
     */

    override fun onCardSelected(position: Int) {
        val context = requireContext()

        if (!this.isCardSelected) {
            val card = stateMachine.gameEngine.getCardByPosition(position)
            if (card != null) {
                this.isCardSelected = true

                val fragment = GameCardFragment.newInstance(card, position, this)
                fragment.enterTransition = Slide(Gravity.BOTTOM).setDuration(200)

                childFragmentManager.commit {
                    add(this@GameBoardFragment.rootView.id, fragment, fragment::class.java.simpleName)
                    addToBackStack(fragment::class.java.simpleName)
                }

                VibratorEngine.vibrate(this.rootView, VibrateType.SHORT)
                stateMachine.soundEngine.playSound(context, SoundType.FLIP)
            } else {
                Navigation.findNavController(this.rootView).navigateUp()
                stateMachine.soundEngine.playSound(context, SoundType.WHOOSH)
            }
        }
    }

    override fun onCardDismissed(position: Int) {
        stateMachine.gameEngine.removeCard(position)
        val args: Bundle? = stateMachine.gameEngine.updateGraphicStatus(requireContext())

        this.isCardSelected = false
        this.cardAdapter.removeCard(position)
        this.statusText = args?.getString(GameEngine.GAME_ENGINE_TAUNT)
                ?: getString(R.string.board_title_lets_begin)

        this.progressBar.max = this.cardAdapter.itemCount - 1
        this.statusTextView.startAnimation(this.statusTextAnimation)

        args?.getInt(GameEngine.GAME_ENGINE_CUP_VOLUME)?.let { volumeImageView.setImageResource(it) }

        when (args?.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
            3 -> this.kingFourImageView.isVisible = false
            2 -> this.kingThreeImageView.isVisible = false
            1 -> this.kingTwoImageView.isVisible = false
            0 -> {
                this.kingOneImageView.isVisible = false
                this.restartButton.show()

                if (!this.isConfettiLaunched) {
                    this.isConfettiLaunched = true
                    this.confettiAnimationView.isVisible = true
                    this.confettiAnimationView.playAnimation()
                    stateMachine.soundEngine.playSound(requireContext(), SoundType.GAME_OVER)
                }
            }
        }
    }
}