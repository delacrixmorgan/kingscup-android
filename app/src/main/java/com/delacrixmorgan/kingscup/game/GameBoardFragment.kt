package com.delacrixmorgan.kingscup.game

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.LoadType
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

    companion object {
        fun newInstance(): GameBoardFragment {
            return GameBoardFragment()
        }
    }

    private lateinit var cardAdapter: GameCardAdapter
    private lateinit var menuDialog: Dialog

    private var isCardSelected: Boolean = false
    private var isGameOver: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupView()
    }

    private fun setupView() {
        val manager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)

        this.cardAdapter = GameCardAdapter(this, GameEngine.getInstance().getDeckSize())
        this.isCardSelected = false

        this.recyclerView.removeAllViews()
        this.recyclerView.layoutManager = manager
        this.recyclerView.adapter = cardAdapter

        this.volumeImageView.setImageResource(R.drawable.ic_cup_whole)
        this.setupMenuDialog()

        setupProgressBar(manager, recyclerView, progressBar)

        this.restartButton.setOnClickListener {
            this.startNewGame()
        }

        this.menuButton.setOnClickListener {
            this.menuDialog.show()
        }
    }

    override fun onCardSelected(position: Int) {
        if (!this.isCardSelected && !this.isGameOver) {
            val card: Card? = GameEngine.getInstance().getCardByPosition(position)

            this.context?.let {
                if (card != null) {
                    this.isCardSelected = true

                    val fragment = GameCardFragment.newInstance(card, position, this)
                    it.showFragmentSliding(fragment, Gravity.BOTTOM)

                    GameEngine.getInstance().vibrateFeedback(it, VibrateType.SHORT)
                    SoundEngine.getInstance().playSound(it, SoundType.FLIP)
                } else {
                    this.activity?.supportFragmentManager?.popBackStack()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }
            }
        }
    }

    override fun onCardDismissed(position: Int) {
        GameEngine.getInstance().removeCard(position)
        val args: Bundle? = GameEngine.getInstance().updateGraphicStatus(this.context!!)

        this.isCardSelected = false
        this.cardAdapter.notifyItemRemoved(position)

        this.progressBar.max--
        this.statusTextView.text = args?.getString(GameEngine.GAME_ENGINE_TAUNT)

        args?.getInt(GameEngine.GAME_ENGINE_CUP_VOLUME)?.let { volumeImageView.setImageResource(it) }

        when (args?.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
            3 -> this.kingFourImageView.visibility = View.GONE
            2 -> this.kingThreeImageView.visibility = View.GONE
            1 -> this.kingTwoImageView.visibility = View.GONE
            0 -> {
                this.kingOneImageView.visibility = View.GONE
                this.restartButton.visibility = View.VISIBLE
                this.isGameOver = true
            }
        }
    }

    private fun setupMenuDialog() {
        val preference = PreferenceHelper.getPreference(this.context!!)
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        this.menuDialog = Dialog(context!!)

        with(this.menuDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_pause)

            quitButton.setOnClickListener(this@GameBoardFragment)
            vibrateButton.setOnClickListener(this@GameBoardFragment)
            volumeButton.setOnClickListener(this@GameBoardFragment)
            resumeButton.setOnClickListener(this@GameBoardFragment)
            startNewGameButton.setOnClickListener(this@GameBoardFragment)

            vibrateButton.setImageResource(if (vibratePreference) R.drawable.ic_vibration_enable else R.drawable.ic_vibration_disable)
            volumeButton.setImageResource(if (soundPreference) R.drawable.ic_volume_up else R.drawable.ic_volume_off)
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
        val preference = PreferenceHelper.getPreference(this.context!!)
        val vibratePreference = preference[PreferenceHelper.VIBRATE, PreferenceHelper.VIBRATE_DEFAULT]

        if (vibratePreference) {
            this.menuDialog.vibrateButton.setImageResource(R.drawable.ic_vibration_disable)
        } else {
            this.menuDialog.vibrateButton.setImageResource(R.drawable.ic_vibration_enable)
        }

        preference[PreferenceHelper.VIBRATE] = !vibratePreference
    }

    private fun updateSoundPreference() {
        val preference = PreferenceHelper.getPreference(this.context!!)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (soundPreference) {
            this.menuDialog.volumeButton.setImageResource(R.drawable.ic_volume_off)
        } else {
            this.menuDialog.volumeButton.setImageResource(R.drawable.ic_volume_up)
        }

        preference[PreferenceHelper.SOUND] = !soundPreference
    }
}
