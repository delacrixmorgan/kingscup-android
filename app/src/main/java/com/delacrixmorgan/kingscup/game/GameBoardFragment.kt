package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.common.setupProgressBar
import kotlinx.android.synthetic.main.fragment_game_board.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class GameBoardFragment : Fragment(), GameCardSelectionListener {
    private lateinit var cardAdapter: GameCardAdapter
    private var isCardSelected: Boolean = false

    companion object {
        lateinit var FRAGMENT_TAG: String
        fun newInstance(): GameBoardFragment {
            val fragment = GameBoardFragment()

            this.FRAGMENT_TAG = fragment.javaClass.simpleName
            return GameBoardFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_game_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        this.cardAdapter = GameCardAdapter(this)
        this.isCardSelected = false

        this.recyclerView.layoutManager = manager
        this.recyclerView.adapter = this.cardAdapter

        setupProgressBar(manager, recyclerView, progressBar)

        this.statusTextView.text = activity.resources.getStringArray(R.array.taunt).first()
        this.quitButton.setOnClickListener { activity.onBackPressed() }
        this.volumeImageView.setImageResource(R.drawable.cup_whole)
    }

    override fun onCardSelected(position: Int) {
        if (!this.isCardSelected) {
            this.isCardSelected = true

            val vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(250, -1))
            } else {
                vibrator.vibrate(250)
            }

            val fragment = GameCardFragment.newInstance(GameEngine.getInstance()?.deckList?.get(position), position)
            Helper.showAddFragmentSlideDown(activity, fragment)
        }
    }

    fun removeCardFromDeck(position: Int) {
        this.isCardSelected = false
        GameEngine.getInstance()?.removeCard(position, cardAdapter, progressBar)

        val args: Bundle? = GameEngine.getInstance()?.updateGraphicStatus(activity)

        statusTextView.text = args?.getString(GameEngine.GAME_ENGINE_TAUNT)
        args?.getInt(GameEngine.GAME_ENGINE_CUP_VOLUME)?.let { volumeImageView.setImageResource(it) }

        when (args?.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
            0 -> kingOneImageView.visibility = View.GONE
            1 -> kingTwoImageView.visibility = View.GONE
            2 -> kingThreeImageView.visibility = View.GONE
            3 -> kingFourImageView.visibility = View.GONE
        }
    }
}
