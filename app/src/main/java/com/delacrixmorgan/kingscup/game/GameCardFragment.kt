package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.databinding.DataBindingUtil.bind
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.databinding.FragmentGameCardBinding
import com.delacrixmorgan.kingscup.model.Card
import kotlinx.android.synthetic.main.fragment_game_card.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameCardFragment : Fragment(), View.OnTouchListener {

    private lateinit var card: Card
    private var dataBinding: FragmentGameCardBinding? = null
    private var position: Int = 0

    companion object {
        private const val GAME_CARD_FRAGMENT_CARD = "Card"
        private const val GAME_CARD_FRAGMENT_POSITION = "Position"

        fun newInstance(card: Card? = null, position: Int = 0): GameCardFragment {
            val fragment = GameCardFragment()
            val args = Bundle()

            args.putSerializable(GAME_CARD_FRAGMENT_CARD, card)
            args.putInt(GAME_CARD_FRAGMENT_POSITION, position)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.card = arguments.getSerializable(GAME_CARD_FRAGMENT_CARD) as Card
        this.position = arguments.getInt(GAME_CARD_FRAGMENT_POSITION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_game_card, container, false)

        this.dataBinding = bind(rootView)
        this.dataBinding?.card = this.card

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val suitList = activity.resources.getStringArray(R.array.suit)
        var suitDrawable: Int = R.drawable.spade_pink

        when (this.card.suit) {
            suitList[0] -> suitDrawable = R.drawable.spade_pink
            suitList[1] -> suitDrawable = R.drawable.heart_pink
            suitList[2] -> suitDrawable = R.drawable.club_pink
            suitList[3] -> suitDrawable = R.drawable.diamond_pink
        }

        this.lightCenterImageView.setImageResource(suitDrawable)
        this.lightLeftImageView.setImageResource(suitDrawable)
        this.darkRightImageView.setImageResource(suitDrawable)

        this.doneButton.setOnTouchListener(this)
        Helper.animateButtonGrow(activity, this.doneButton)

        if (GameEngine.getInstance()?.checkWin(this.card)!!) {
            this.doneButton.visibility = View.GONE

            Handler().postDelayed({
                this.backToBoardFragment()
            }, 2000)
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> this.backToBoardFragment()
        }
        return true
    }

    private fun backToBoardFragment() {
        val fragment = fragmentManager.findFragmentByTag(GameBoardFragment.FRAGMENT_TAG) as GameBoardFragment
        fragment.removeCardFromDeck(this.position)
        fragmentManager.popBackStack()
    }
}
