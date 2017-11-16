package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_game_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        cardAdapter = GameCardAdapter(this)
        quitButton.setOnClickListener { activity.onBackPressed() }

        recyclerView.layoutManager = manager
        recyclerView.adapter = cardAdapter

        statusTextView.text = activity.resources.getStringArray(R.array.taunt).first()

        volumeImageView.setImageResource(R.drawable.cup_whole)
        setupProgressBar(manager, recyclerView, progressBar)
    }

    override fun onCardSelected(position: Int) {
        val fragment = GameCardFragment.newInstance(GameEngine.getInstance()?.deckList?.get(position), position)
        Helper.showAddFragmentSlideDown(activity, fragment)
    }

    fun removeCardFromDeck(position: Int) {
        GameEngine.getInstance()?.removeCard(activity, position, cardAdapter, progressBar)

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
