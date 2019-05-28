package com.delacrixmorgan.kingscup.game

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.transition.Slide
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.setupProgressBar
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.GameType
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.dialog_pause.*
import kotlinx.android.synthetic.main.fragment_game_board.*

/**
 * GameBoardFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameBoardFragment : Fragment(), View.OnClickListener, CardListener {

    private lateinit var statusTextAnimation: AlphaAnimation
    private lateinit var cardAdapter: GameCardAdapter
    private lateinit var menuDialog: Dialog

    private var isCardSelected: Boolean = false
    private var isConfettiLaunched = false
    private var statusText = ""

    private val stateMachine: GameStateMachine by lazy {
        ViewModelProviders.of(requireActivity()).get(GameStateMachine::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deckAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
        val cellHeight = (this.resources.displayMetrics.heightPixels / 2.5).toInt()
        val cellWidth = (cellHeight * (10.0 / 16.0)).toInt()

        this.volumeImageView.setImageResource(R.drawable.ic_cup_whole)
        this.statusText = getString(R.string.board_title_lets_begin)
        this.statusTextAnimation = AlphaAnimation(1.0f, 0.0f)
        this.isCardSelected = false
        this.cardAdapter = GameCardAdapter(
                cellHeight = cellHeight,
                cellWidth = cellWidth,
                deckSize = GameEngine.getInstance().getDeckSize(),
                listener = this
        )

        with(this.recyclerView) {
            removeAllViews()
            adapter = cardAdapter
            layoutAnimation = deckAnimation
            scheduleLayoutAnimation()
        }

        with(this.statusTextAnimation) {
            duration = 200
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }

        setupMenuDialog()
        setupProgressBar(this.recyclerView.layoutManager, recyclerView, progressBar)

        this.restartButton.setOnClickListener { startNewGame() }
        this.menuButton.setOnClickListener { this.menuDialog.show() }

        this.statusTextView.startAnimation(this.statusTextAnimation)
        this.statusTextAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) = Unit
            override fun onAnimationStart(animation: Animation?) = Unit
            override fun onAnimationRepeat(animation: Animation?) {
                this@GameBoardFragment.statusTextView.text = this@GameBoardFragment.statusText
            }
        })
    }

    override fun onCardSelected(position: Int) {
        val context = this.context ?: return

        if (!this.isCardSelected) {
            val card: Card? = GameEngine.getInstance().getCardByPosition(position)
            if (card != null) {
                this.isCardSelected = true

                val fragment = GameCardFragment.newInstance(card, position, this)
                fragment.enterTransition = Slide(Gravity.BOTTOM).setDuration(200)

                this.childFragmentManager.transaction {
                    add(this@GameBoardFragment.rootView.id, fragment, fragment::class.java.simpleName)
                    addToBackStack(fragment::class.java.simpleName)
                }

                GameEngine.getInstance().vibrateFeedback(context, this.rootView, VibrateType.SHORT)
                SoundEngine.getInstance().playSound(context, SoundType.FLIP)
            } else {
                Navigation.findNavController(this.rootView).navigateUp()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
            }
        }
    }

    override fun onCardDismissed(position: Int) {
        GameEngine.getInstance().removeCard(position)
        val args: Bundle? = GameEngine.getInstance().updateGraphicStatus(requireContext())

        this.isCardSelected = false
        this.cardAdapter.notifyItemRemoved(position)
        this.statusText = args?.getString(GameEngine.GAME_ENGINE_TAUNT)
                ?: getString(R.string.board_title_lets_begin)

        this.progressBar.max--
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
                    SoundEngine.getInstance().playSound(requireContext(), SoundType.GAME_OVER)
                }
            }
        }
    }

    private fun setupMenuDialog() {
        val context = this.context ?: return

        val preference = PreferenceHelper.getPreference(context)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        this.menuDialog = Dialog(context)

        with(this.menuDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_pause)

            quitButton.setOnClickListener(this@GameBoardFragment)
            volumeButton.setOnClickListener(this@GameBoardFragment)
            resumeButton.setOnClickListener(this@GameBoardFragment)
            vibrateButton.setOnClickListener(this@GameBoardFragment)
            startNewGameButton.setOnClickListener(this@GameBoardFragment)

            volumeButton.setImageResource(if (soundPreference) R.drawable.ic_volume_up else R.drawable.ic_volume_off)
            vibrateButton.setImageResource(if (vibratePreference) R.drawable.ic_vibration_enable else R.drawable.ic_vibration_disable)
        }
    }

    override fun onClick(view: View) {
        val context = view.context

        when (view.id) {
            R.id.startNewGameButton -> {
                menuDialog.dismiss()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
                startNewGame()
            }

            R.id.vibrateButton -> {
                this.updateVibratePreference()
                GameEngine.getInstance().vibrateFeedback(context, view, VibrateType.SHORT)
            }

            R.id.volumeButton -> {
                this.updateSoundPreference()
                SoundEngine.getInstance().playSound(context, SoundType.CLICK)
            }

            R.id.resumeButton -> {
                this.menuDialog.dismiss()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
            }

            R.id.quitButton -> {
                this.menuDialog.dismiss()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
                Navigation.findNavController(this.rootView).navigateUp()
            }
        }
    }

    private fun startNewGame() {
        val action = GameBoardFragmentDirections.actionGameBoardFragmentToGameLoadFragment(GameType.RESTART_GAME)
        Navigation.findNavController(this.rootView).navigate(action)
    }

    private fun updateVibratePreference() {
        val preference = PreferenceHelper.getPreference(requireContext())
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        if (vibratePreference) {
            this.menuDialog.vibrateButton.setImageResource(R.drawable.ic_vibration_disable)
        } else {
            this.menuDialog.vibrateButton.setImageResource(R.drawable.ic_vibration_enable)
        }

        preference[PreferenceHelper.VIBRATE] = !vibratePreference
    }

    private fun updateSoundPreference() {
        val preference = PreferenceHelper.getPreference(requireContext())
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (soundPreference) {
            this.menuDialog.volumeButton.setImageResource(R.drawable.ic_volume_off)
        } else {
            this.menuDialog.volumeButton.setImageResource(R.drawable.ic_volume_up)
        }

        preference[PreferenceHelper.SOUND] = !soundPreference
    }
}