package com.delacrixmorgan.kingscup.game.card

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
import com.delacrixmorgan.kingscup.common.Keys
import com.delacrixmorgan.kingscup.common.animateButtonGrow
import com.delacrixmorgan.kingscup.databinding.FragmentGameCardBinding
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.engine.GameEngine.Companion.GAME_CARD_KING
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.engine.VibratorEngine
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.SuitType
import com.delacrixmorgan.kingscup.model.VibrateType
import kotlinx.android.synthetic.main.fragment_game_card.*

class GameCardFragment : Fragment() {

    companion object {
        fun create(card: Card, position: Int, listener: GameCardListener) =
            GameCardFragment().apply {
                arguments = bundleOf(
                    Keys.GameCard.Card.name to card,
                    Keys.GameCard.Position.name to position
                )
                this.listener = listener
            }
    }

    private lateinit var card: Card
    private lateinit var listener: GameCardListener

    private var dataBinding: FragmentGameCardBinding? = null

    private val gameEngine by lazy {
        GameEngine.getInstance(requireContext())
    }

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_card, container, false).apply {
            dataBinding = bind(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToBoardFragment()
                }
            })

        val context = view.context

        card = requireNotNull(arguments?.getParcelable(Keys.GameCard.Card.name))
        dataBinding?.card = card

        when (this.card.suitType) {
            SuitType.Spade -> R.drawable.ic_card_spade
            SuitType.Heart -> R.drawable.ic_card_heart
            SuitType.Club -> R.drawable.ic_card_club
            SuitType.Diamond -> R.drawable.ic_card_diamond
        }.apply {
            lightCenterImageView.setImageResource(this)
            lightLeftImageView.setImageResource(this)
            darkRightImageView.setImageResource(this)
        }

        doneButton.animateButtonGrow()
        doneButton.setOnClickListener {
            backToBoardFragment()
        }

        when {
            gameEngine.hasTriggerWin(card) -> {
                doneButton.hide()
                VibratorEngine.vibrate(view, VibrateType.Long)
                soundEngine.playSound(context, SoundType.Oooh)

                Handler().postDelayed({
                    backToBoardFragment()
                }, 2_000)
            }
            card.rank == GAME_CARD_KING -> soundEngine.playSound(context, SoundType.Oooh)
        }
    }

    private fun backToBoardFragment() {
        soundEngine.playSound(requireContext(), SoundType.Whoosh)
        listener.onCardDismissed(card)
        activity?.supportFragmentManager?.popBackStack()
    }
}