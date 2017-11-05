package com.delacrixmorgan.kingscup.fragment

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.adapter.CardAdapter
import com.delacrixmorgan.kingscup.engine.GameEngine
import kotlinx.android.synthetic.main.fragment_select.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class SelectFragment : Fragment() {

    private var mCardAdapter: CardAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quitButton.setOnClickListener { activity.onBackPressed() }

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mCardAdapter = CardAdapter(activity, progressBar)

        recyclerView.layoutManager = manager
        recyclerView.adapter = mCardAdapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (manager.findFirstVisibleItemPosition() == 0) {
                    progressBar.progress = 0
                } else {
                    progressBar.progress = manager.findLastVisibleItemPosition()
                }
            }
        } else {
            progressBar.visibility = GONE
        }

        updateGraphics()
    }

    fun updateFragment() {
        GameEngine.getInstance().updateCardAdapter(mCardAdapter)
        updateGraphics()
    }

    private fun updateGraphics() {
        when (GameEngine.getInstance().getmKingCounter()) {
            0, -1 -> {
                kingOneImageView.visibility = GONE
                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_4)
                statusBodyTextView.setText(R.string.game_over_header)
                statusTauntTextView.setText(R.string.game_over_body)

                endGameButton.visibility = View.VISIBLE
                endGameButton.setOnClickListener {
                    GameEngine.getInstance().playSound(activity, "CARD_WHOOSH")
                    activity.finish()
                }
            }

            1 -> {
                kingTwoImageView.visibility = GONE
                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_3)
                statusBodyTextView.text = getString(R.string.counter_1_king_left)
                statusTauntTextView.text = GameEngine.getInstance().nextText
            }

            2 -> {
                kingThreeImageView.visibility = GONE
                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_2)
                statusBodyTextView.text = getString(R.string.counter_2_king_left)
                statusTauntTextView.text = GameEngine.getInstance().nextText
            }

            3 -> {
                kingFourImageView.visibility = GONE
                cupVolumeImageView.setBackgroundResource(R.drawable.cup_volume_1)
                statusBodyTextView.text = getString(R.string.counter_3_king_left)
                statusTauntTextView.text = GameEngine.getInstance().nextText
            }

            else -> {
                cupVolumeImageView.setBackgroundResource(R.drawable.cup_whole)
                statusBodyTextView.text = getString(R.string.counter_4_king_left)
                statusTauntTextView.text = GameEngine.getInstance().nextText
            }
        }
    }
}
