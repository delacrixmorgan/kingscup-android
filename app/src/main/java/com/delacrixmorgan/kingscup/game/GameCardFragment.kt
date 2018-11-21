package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.bind
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.SoundType
import com.delacrixmorgan.kingscup.common.animateButtonGrow
import com.delacrixmorgan.kingscup.databinding.FragmentGameCardBinding
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SuitType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_card.*

/**
 * GameCardFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameCardFragment : Fragment(), View.OnTouchListener {

    companion object {
        private const val GAME_CARD_FRAGMENT_CARD = "GameCardFragment.Card"
        private const val GAME_CARD_FRAGMENT_POSITION = "GameCardFragment.Position"
        private const val GAME_CARD_KING = "K"

        fun newInstance(card: Card? = null, position: Int = 0, cardListener: CardListener): GameCardFragment {
            val fragment = GameCardFragment()
            val args = Bundle()

            args.putParcelable(GAME_CARD_FRAGMENT_CARD, card)
            args.putInt(GAME_CARD_FRAGMENT_POSITION, position)

            fragment.cardListener = cardListener
            fragment.arguments = args
            return fragment
        }
    }

    var cardListener: CardListener? = null

    private lateinit var card: Card
    private var dataBinding: FragmentGameCardBinding? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.arguments?.let {
            this.card = it.getParcelable(GAME_CARD_FRAGMENT_CARD)
            this.position = it.getInt(GAME_CARD_FRAGMENT_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_game_card, container, false)

        this.dataBinding = bind(rootView)
        this.dataBinding?.card = this.card

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = this.context ?: return

        this.doneButton.setOnTouchListener(this)

        setupView()
        animateButtonGrow(context, doneButton)

        when {
            GameEngine.getInstance().checkWin(card) -> {
                doneButton.hide()

                GameEngine.getInstance().vibrateFeedback(context, VibrateType.LONG)
                SoundEngine.getInstance().playSound(context, SoundType.GAME_OVER)

                Handler().postDelayed({
                    backToBoardFragment()
                }, 2000)
            }
            card.rank == GAME_CARD_KING -> SoundEngine.getInstance().playSound(context, SoundType.OOOH)
        }
    }

    private fun setupView() {
        val suitList = SuitType.values()
        var suitDrawable: Int = R.drawable.ic_card_spade

        this.context?.let {
            when (card.suit) {
                suitList[0].getLocalisedText(it) -> suitDrawable = R.drawable.ic_card_spade
                suitList[1].getLocalisedText(it) -> suitDrawable = R.drawable.ic_card_heart
                suitList[2].getLocalisedText(it) -> suitDrawable = R.drawable.ic_card_club
                suitList[3].getLocalisedText(it) -> suitDrawable = R.drawable.ic_card_diamond
            }
        }

        this.lightCenterImageView.setImageResource(suitDrawable)
        this.lightLeftImageView.setImageResource(suitDrawable)
        this.darkRightImageView.setImageResource(suitDrawable)
    }

    private fun backToBoardFragment() {
        this.cardListener?.onCardDismissed(this.position)
        this.activity?.supportFragmentManager?.popBackStack()

        SoundEngine.getInstance().playSound(this.context!!, SoundType.WHOOSH)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> this.backToBoardFragment()
        }
        return true
    }
}