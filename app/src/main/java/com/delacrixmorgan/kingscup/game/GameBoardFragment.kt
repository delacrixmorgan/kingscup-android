package com.delacrixmorgan.kingscup.game

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.LoadType
import com.delacrixmorgan.kingscup.model.VibrateType
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
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
    companion object {
        fun newInstance() = GameBoardFragment()
    }

    private lateinit var statusTextAnimation: AlphaAnimation
    private lateinit var cardAdapter: GameCardAdapter
    private lateinit var menuDialog: Dialog

    private var isCardSelected: Boolean = false
    private var statusText = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayouts()
        setupListeners()

        this.statusTextView.startAnimation(this.statusTextAnimation)
    }

    private fun setupLayouts() {
        val deckAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
        val cellHeight = (resources.displayMetrics.heightPixels / 2.5).toInt()
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
            GravitySnapHelper(Gravity.START).attachToRecyclerView(this)
        }

        with(this.statusTextAnimation) {
            duration = 200
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }

        setupMenuDialog()
        setupProgressBar(this.recyclerView.layoutManager as LinearLayoutManager, recyclerView, progressBar)
    }

    private fun setupListeners() {
        this.restartButton.setOnClickListener { startNewGame() }
        this.menuButton.setOnClickListener { this.menuDialog.show() }

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
                context.showFragmentSliding(fragment, Gravity.BOTTOM)

                GameEngine.getInstance().vibrateFeedback(context, VibrateType.SHORT)
                SoundEngine.getInstance().playSound(context, SoundType.FLIP)
            } else {
                this.activity?.supportFragmentManager?.popBackStack()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
            }
        }
    }

    override fun onCardDismissed(position: Int) {
        GameEngine.getInstance().removeCard(position)
        val args: Bundle? = GameEngine.getInstance().updateGraphicStatus(this.context!!)

        this.isCardSelected = false
        this.cardAdapter.notifyItemRemoved(position)
        this.statusText = args?.getString(GameEngine.GAME_ENGINE_TAUNT) ?: getString(R.string.board_title_lets_begin)

        this.progressBar.max--
        this.statusTextView.startAnimation(this.statusTextAnimation)

        args?.getInt(GameEngine.GAME_ENGINE_CUP_VOLUME)?.let { volumeImageView.setImageResource(it) }

        when (args?.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
            3 -> this.kingFourImageView.visibility = View.GONE
            2 -> this.kingThreeImageView.visibility = View.GONE
            1 -> this.kingTwoImageView.visibility = View.GONE
            0 -> {
                this.kingOneImageView.visibility = View.GONE
                this.restartButton.visibility = View.VISIBLE
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
        this.context?.let {
            when (view.id) {
                R.id.startNewGameButton -> {
                    menuDialog.dismiss()

                    this.startNewGame()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }

                R.id.vibrateButton -> {
                    this.updateVibratePreference()
                    GameEngine.getInstance().vibrateFeedback(it, VibrateType.SHORT)
                }

                R.id.volumeButton -> {
                    this.updateSoundPreference()
                    SoundEngine.getInstance().playSound(it, SoundType.CLICK)
                }

                R.id.resumeButton -> {
                    this.menuDialog.dismiss()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }

                R.id.quitButton -> {
                    this.menuDialog.dismiss()
                    this.activity?.supportFragmentManager?.popBackStack()

                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }
            }
        }
    }

    private fun startNewGame() {
        this.activity?.supportFragmentManager?.popBackStack()
        this.context?.showFragmentSliding(GameLoadFragment.newInstance(LoadType.RESTART_GAME), Gravity.BOTTOM)
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