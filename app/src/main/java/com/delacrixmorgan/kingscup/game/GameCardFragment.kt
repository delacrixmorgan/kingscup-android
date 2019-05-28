package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.bind
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.GameEngine.Companion.GAME_CARD_KING
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.animateButtonGrow
import com.delacrixmorgan.kingscup.databinding.FragmentGameCardBinding
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SoundType
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

class GameCardFragment : Fragment() {

    companion object {
        private const val GAME_CARD_FRAGMENT_CARD = "GameCardFragment.Card"
        private const val GAME_CARD_FRAGMENT_POSITION = "GameCardFragment.Position"

        fun newInstance(card: Card, position: Int = 0, cardListener: CardListener): GameCardFragment {
            return GameCardFragment().apply {
                this.arguments = bundleOf(
                    GAME_CARD_FRAGMENT_CARD to card,
                    GAME_CARD_FRAGMENT_POSITION to position
                )
                this.cardListener = cardListener
            }
        }
    }

    private lateinit var card: Card

    private var position: Int = 0
    private var cardListener: CardListener? = null
    private var dataBinding: FragmentGameCardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.arguments?.let {
            this.card = it.getParcelable(GAME_CARD_FRAGMENT_CARD) ?: throw Exception("Card Missing")
            this.position = it.getInt(GAME_CARD_FRAGMENT_POSITION)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToBoardFragment()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_game_card, container, false)

        this.dataBinding = bind(rootView)
        this.dataBinding?.card = this.card

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        when (this.card.suitType) {
            SuitType.SPADE -> R.drawable.ic_card_spade
            SuitType.HEART -> R.drawable.ic_card_heart
            SuitType.CLUB -> R.drawable.ic_card_club
            SuitType.DIAMOND -> R.drawable.ic_card_diamond
        }.apply {
            lightCenterImageView.setImageResource(this)
            lightLeftImageView.setImageResource(this)
            darkRightImageView.setImageResource(this)
        }

        this.doneButton.animateButtonGrow()
        this.doneButton.setOnClickListener {
            backToBoardFragment()
        }

        when {
            GameEngine.getInstance().checkWin(card) -> {
                doneButton.hide()

                GameEngine.getInstance().vibrateFeedback(context, view, VibrateType.LONG)
                SoundEngine.getInstance().playSound(context, SoundType.GAME_OVER)

                Handler().postDelayed({
                    backToBoardFragment()
                }, 2000)
            }
            this.card.rank == GAME_CARD_KING -> SoundEngine.getInstance().playSound(context, SoundType.OOOH)
        }
    }

    private fun backToBoardFragment() {
        SoundEngine.getInstance().playSound(requireContext(), SoundType.WHOOSH)
        this.cardListener?.onCardDismissed(this.position)
        this.activity?.supportFragmentManager?.popBackStack()
    }
}