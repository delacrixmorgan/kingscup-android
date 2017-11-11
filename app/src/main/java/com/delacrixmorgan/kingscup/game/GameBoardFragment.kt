package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import kotlinx.android.synthetic.main.fragment_select.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class GameBoardFragment : Fragment(), GameCardSelectionListener {
    private lateinit var cardAdapter: GameCardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        cardAdapter = GameCardAdapter(this)
        quitButton.setOnClickListener { activity.onBackPressed() }

        recyclerView.layoutManager = manager
        recyclerView.adapter = cardAdapter

        setupProgressBar(manager)
        updateGraphics()
    }

    override fun onCardSelected(position: Int) {
        val fragment = GameCardFragment.newInstance(GameEngine.getInstance()?.deckList?.get(position), position)
        Helper.showAddFragmentSlideDown(activity, fragment)
    }

    fun removeCardFromDeck(position: Int) {
        GameEngine.newInstance(activity).removeCard(position, cardAdapter, progressBar)
    }

    private fun setupProgressBar(manager: LinearLayoutManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                if (manager.findFirstVisibleItemPosition() == 0) {
                    progressBar.progress = 0
                } else {
                    progressBar.progress = manager.findLastVisibleItemPosition()
                }
            }
        } else {
            progressBar.visibility = GONE
        }
    }

    private fun updateGraphics() {
//        when (GameEngine.getInstance()?.getmKingCounter()) {
//            0, -1 -> {
//                kingOneImageView.visibility = GONE
//                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_4)
//                statusBodyTextView.setText(R.string.game_over_header)
//                statusTauntTextView.setText(R.string.game_over_body)
//
//                endGameButton.visibility = View.VISIBLE
//                endGameButton.setOnClickListener {
//                    activity.finish()
//                }
//            }
//
//            1 -> {
//                kingTwoImageView.visibility = GONE
//                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_3)
//                statusBodyTextView.text = getString(R.string.counter_1_king_left)
//                statusTauntTextView.text = GameEngine.instance.nextText
//            }
//
//            2 -> {
//                kingThreeImageView.visibility = GONE
//                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_2)
//                statusBodyTextView.text = getString(R.string.counter_2_king_left)
//                statusTauntTextView.text = GameEngine.instance.nextText
//            }
//
//            3 -> {
//                kingFourImageView.visibility = GONE
//                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_1)
//                statusBodyTextView.text = getString(R.string.counter_3_king_left)
//                statusTauntTextView.text = GameEngine.instance.nextText
//            }
//
//            else -> {
//                cupVolumeImageView.setBackgroundResource(R.drawable.cup_whole)
//                statusBodyTextView.text = getString(R.string.counter_4_king_left)
//                statusTauntTextView.text = GameEngine.instance.nextText
//            }
//        }
    }
}
