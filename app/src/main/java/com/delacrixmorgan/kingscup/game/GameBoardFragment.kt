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
import kotlinx.android.synthetic.main.dialog_pause.*
import kotlinx.android.synthetic.main.fragment_game_board.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 **/

class GameBoardFragment : Fragment(), View.OnClickListener, CardListener {

    companion object {
        lateinit var FRAGMENT_TAG: String

        fun newInstance(): GameBoardFragment {
            val fragment = GameBoardFragment()
            this.FRAGMENT_TAG = fragment.javaClass.simpleName

            return fragment
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

        setupView()

        restartButton.setOnClickListener {
            startNewGame()
        }

        menuButton.setOnClickListener {
            menuDialog.show()
        }
    }

    private fun setupView() {
        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        cardAdapter = GameCardAdapter(this, GameEngine.getInstance().deckList.size)
        isCardSelected = false

        recyclerView.removeAllViews()
        recyclerView.layoutManager = manager
        recyclerView.adapter = cardAdapter

        volumeImageView.setImageResource(R.drawable.cup_whole)

        setupMenuDialog()
        setupProgressBar(manager, recyclerView, progressBar)
    }

    override fun onCardSelected(position: Int) {
        if (!isCardSelected && !isGameOver) {
            val card: Card? = GameEngine.getInstance().deckList[position]

            if (card != null) {
                context?.let {
                    val fragment = GameCardFragment.newInstance(card, position)
                    isCardSelected = true
                    showFragmentSliding(it, fragment, Gravity.BOTTOM)

                    GameEngine.getInstance().vibrateFeedback(VibrateType.SHORT)
                    SoundEngine.getInstance().playSound(it, SoundType.FLIP)
                }
            } else {
                activity?.supportFragmentManager?.popBackStack()
                SoundEngine.getInstance().playSound(context!!, SoundType.WHOOSH)
            }
        }
    }

    private fun setupMenuDialog() {
        val preference = PreferenceHelper.getPreference(context!!)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        menuDialog = Dialog(context!!)

        with(menuDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_pause)

            quitDialogButton.setOnClickListener(this@GameBoardFragment)
            rateDialogButton.setOnClickListener(this@GameBoardFragment)
            volumeDialogButton.setOnClickListener(this@GameBoardFragment)
            resumeDialogButton.setOnClickListener(this@GameBoardFragment)
            restartDialogButton.setOnClickListener(this@GameBoardFragment)

            volumeDialogButton.setImageResource(if (soundPreference) R.drawable.ic_volume_up_black_48dp else R.drawable.ic_volume_off_black_48dp)
        }
    }

    override fun onClick(view: View) {
        context?.let {
            when (view.id) {
                R.id.restartDialogButton -> {
                    menuDialog.dismiss()

                    startNewGame()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }

                R.id.rateDialogButton -> {
                    it.launchPlayStore()
                }

                R.id.volumeDialogButton -> {
                    updateSoundPreference()
                    SoundEngine.getInstance().playSound(it, SoundType.CLICK)
                }

                R.id.resumeDialogButton -> {
                    menuDialog.dismiss()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }

                R.id.quitDialogButton -> {
                    menuDialog.dismiss()

                    activity?.supportFragmentManager?.popBackStack()
                    SoundEngine.getInstance().playSound(it, SoundType.WHOOSH)
                }
            }
        }
    }

    private fun startNewGame() {
        activity?.supportFragmentManager?.popBackStack()
        showFragmentSliding(context!!, GameLoadFragment.newInstance(LoadType.RESTART_GAME), Gravity.BOTTOM)
    }

    private fun updateSoundPreference() {
        val preference = PreferenceHelper.getPreference(context!!)
        val soundPreference = preference[PreferenceHelper.SOUND, PreferenceHelper.SOUND_DEFAULT]

        if (soundPreference) {
            this.menuDialog.volumeDialogButton.setImageResource(R.drawable.ic_volume_off_black_48dp)
        } else {
            this.menuDialog.volumeDialogButton.setImageResource(R.drawable.ic_volume_up_black_48dp)
        }

        preference[PreferenceHelper.SOUND] = !soundPreference
    }

    fun removeCardFromDeck(position: Int) {
        GameEngine.getInstance().removeCard(position, cardAdapter, progressBar)

        val args: Bundle? = GameEngine.getInstance().updateGraphicStatus(context!!)
        this.isCardSelected = false

        statusTextView.text = args?.getString(GameEngine.GAME_ENGINE_TAUNT)
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
}
