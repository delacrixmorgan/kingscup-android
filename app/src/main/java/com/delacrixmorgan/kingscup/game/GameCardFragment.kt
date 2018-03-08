package com.delacrixmorgan.kingscup.game

import android.databinding.DataBindingUtil.bind
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.SoundType
import com.delacrixmorgan.kingscup.common.animateButtonGrow
import com.delacrixmorgan.kingscup.databinding.FragmentGameCardBinding
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_card.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 **/

class GameCardFragment : Fragment(), View.OnTouchListener {

    companion object {
        private const val GAME_CARD_FRAGMENT_CARD = "Card"
        private const val GAME_CARD_FRAGMENT_POSITION = "Position"

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

        arguments?.let {
            this.card = it.getParcelable(GAME_CARD_FRAGMENT_CARD)
            this.position = it.getInt(GAME_CARD_FRAGMENT_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_game_card, container, false)

        dataBinding = bind(rootView)
        dataBinding?.card = this.card

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        doneButton.setOnTouchListener(this)

        context?.let {
            animateButtonGrow(it, doneButton)

            if (GameEngine.getInstance().checkWin(card)) {
                doneButton.visibility = View.GONE

                GameEngine.getInstance().vibrateFeedback(VibrateType.LONG)
                SoundEngine.getInstance().playSound(it, SoundType.GAME_OVER)

                Handler().postDelayed({
                    backToBoardFragment()
                }, 2000)
            }

            if (card.rank == "K") {
                SoundEngine.getInstance().playSound(it, SoundType.OOOH)
            }
        }
    }

    private fun setupView() {
        val suitList = resources.getStringArray(R.array.suit)
        var suitDrawable: Int = R.drawable.spade_pink

        when (card.suit) {
            suitList[0] -> suitDrawable = R.drawable.spade_pink
            suitList[1] -> suitDrawable = R.drawable.heart_pink
            suitList[2] -> suitDrawable = R.drawable.club_pink
            suitList[3] -> suitDrawable = R.drawable.diamond_pink
        }

        this.lightCenterImageView.setImageResource(suitDrawable)
        this.lightLeftImageView.setImageResource(suitDrawable)
        this.darkRightImageView.setImageResource(suitDrawable)
    }

    private fun backToBoardFragment() {
        cardListener?.onCardDismissed(position)
        activity?.supportFragmentManager?.popBackStack()

        SoundEngine.getInstance().playSound(context!!, SoundType.WHOOSH)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> this.backToBoardFragment()
        }
        return true
    }
}
