package com.delacrixmorgan.kingscup.fragment

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.adapter.CardAdapter
import com.delacrixmorgan.kingscup.engine.GameEngine

import android.view.View.GONE

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class SelectFragment : Fragment() {

    private var mCardRecyclerView: RecyclerView? = null
    private var mQuitButton: FloatingActionButton? = null
    private var mProgressBar: ProgressBar? = null
    private var mCardAdapter: CardAdapter? = null
    private var mButtonEndGame: Button? = null
    private var mImageVolume: ImageView? = null
    private var mKingOne: ImageView? = null
    private var mKingTwo: ImageView? = null
    private var mKingThree: ImageView? = null
    private var mKingFour: ImageView? = null
    private var mTextStatusHeader: TextView? = null
    private var mTextStatusBody: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_select, container, false)

        mCardRecyclerView = rootView.findViewById(R.id.fragment_select_card_recycler_view)
        mQuitButton = rootView.findViewById(R.id.fragment_select_quit_button)
        mProgressBar = rootView.findViewById(R.id.fragment_select_card_progress_bar)

        mButtonEndGame = rootView.findViewById(R.id.fragment_select_button_endgame)
        mImageVolume = rootView.findViewById(R.id.fragment_select_image_cup_volume)

        mKingOne = rootView.findViewById(R.id.fragment_select_king_1)
        mKingTwo = rootView.findViewById(R.id.fragment_select_king_2)
        mKingThree = rootView.findViewById(R.id.fragment_select_king_3)
        mKingFour = rootView.findViewById(R.id.fragment_select_king_4)

        mTextStatusHeader = rootView.findViewById(R.id.fragment_select_status_header)
        mTextStatusBody = rootView.findViewById(R.id.fragment_select_status_body)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mQuitButton!!.setOnClickListener { activity.onBackPressed() }

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        mCardAdapter = CardAdapter(activity, mProgressBar)

        mCardRecyclerView!!.layoutManager = manager
        mCardRecyclerView!!.adapter = mCardAdapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCardRecyclerView!!.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (manager.findFirstVisibleItemPosition() == 0) {
                    mProgressBar!!.progress = 0
                } else {
                    mProgressBar!!.progress = manager.findLastVisibleItemPosition()
                }
            }
        } else {
            mProgressBar!!.visibility = GONE
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
                mKingOne!!.visibility = GONE
                mImageVolume!!.setBackgroundResource(R.drawable.cup_volume_4)
                mTextStatusBody!!.setText(R.string.game_over_header)
                mTextStatusHeader!!.setText(R.string.game_over_body)

                //Helper.animateButtonGrow(getActivity(), mButtonEndGame);
                mButtonEndGame!!.visibility = View.VISIBLE
                mButtonEndGame!!.setOnClickListener {
                    GameEngine.getInstance().playSound(activity, "CARD_WHOOSH")
                    activity.finish()
                }
            }

            1 -> {
                mKingTwo!!.visibility = GONE
                mImageVolume!!.setBackgroundResource(R.drawable.cup_volume_3)
                mTextStatusBody!!.text = getString(R.string.counter_1_king_left)
                mTextStatusHeader!!.text = GameEngine.getInstance().nextText
            }

            2 -> {
                mKingThree!!.visibility = GONE
                mImageVolume!!.setBackgroundResource(R.drawable.cup_volume_2)
                mTextStatusBody!!.text = getString(R.string.counter_2_king_left)
                mTextStatusHeader!!.text = GameEngine.getInstance().nextText
            }

            3 -> {
                mKingFour!!.visibility = GONE
                mImageVolume!!.setBackgroundResource(R.drawable.cup_volume_1)
                mTextStatusBody!!.text = getString(R.string.counter_3_king_left)
                mTextStatusHeader!!.text = GameEngine.getInstance().nextText
            }

            else -> {
                mImageVolume!!.setBackgroundResource(R.drawable.cup_whole)
                mTextStatusBody!!.text = getString(R.string.counter_4_king_left)
                mTextStatusHeader!!.text = GameEngine.getInstance().nextText
            }
        }
    }

    companion object {
        private val TAG = "SelectFragment"
    }
}
