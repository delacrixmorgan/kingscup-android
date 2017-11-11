package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.databinding.DataBindingUtil.bind
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.databinding.FragmentCardBinding
import com.delacrixmorgan.kingscup.model.Card
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameCardFragment : Fragment(), View.OnTouchListener {
    
    private lateinit var card: Card
    private var dataBinding: FragmentCardBinding? = null
    private var position: Int = 0

    companion object {
        private const val GAMECARDFRAGMENT_CARD = "GameCardFragment.Card"
        private const val GAMECARDFRAGMENT_POSITION = "GameCardFragment.Position"

        fun newInstance(card: Card? = null, position: Int = 0): GameCardFragment {

            val fragment = GameCardFragment()
            val args = Bundle()

            args.putSerializable(GAMECARDFRAGMENT_CARD, card)
            args.putInt(GAMECARDFRAGMENT_POSITION, position)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.card = arguments.getSerializable(GAMECARDFRAGMENT_CARD) as Card
        this.position = arguments.getInt(GAMECARDFRAGMENT_POSITION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_card, container, false)

        this.dataBinding = bind(rootView)
        this.dataBinding?.card = this.card

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.animateButtonGrow(activity, doneButton)
        doneButton.setOnTouchListener(this)
//
//        when (card.getmSuit()) {
//            "Spade" -> {
//                lightCenterImageView.setBackgroundResource(R.drawable.spade_pink)
//                lightLeftImageView.setBackgroundResource(R.drawable.spade_pink)
//                darkRightImageView!!.setBackgroundResource(R.drawable.spade_dark)
//            }
//
//            "Heart" -> {
//                lightCenterImageView.setBackgroundResource(R.drawable.heart_pink)
//                lightLeftImageView.setBackgroundResource(R.drawable.heart_pink)
//                darkRightImageView!!.setBackgroundResource(R.drawable.heart_dark)
//            }
//
//            "Club" -> {
//                lightCenterImageView.setBackgroundResource(R.drawable.club_pink)
//                lightLeftImageView.setBackgroundResource(R.drawable.club_pink)
//                darkRightImageView!!.setBackgroundResource(R.drawable.club_dark)
//            }
//
//            "Diamond" -> {
//                lightCenterImageView.setBackgroundResource(R.drawable.diamond_pink)
//                lightLeftImageView.setBackgroundResource(R.drawable.diamond_pink)
//                darkRightImageView!!.setBackgroundResource(R.drawable.diamond_dark)
//            }
//        }
//
//
//        if (GameEngine.instance.getmKingCounter() < 1) {
//            val vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//            vibrator.vibrate(2000)
//
//            doneButton.visibility = View.GONE
//            doneButton.isEnabled = false
//
//            val handler = Handler()
//            handler.post {
//                val animGrow = AnimationSet(true)
//                animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out))
//                lightCenterImageView.startAnimation(animGrow)
//            }
//
//            handler.postDelayed({
//                val fragment = fragmentManager.findFragmentByTag("GameBoardFragment") as GameBoardFragment
//                fragment.updateFragment()
//                fragmentManager.popBackStack()
//            }, 2000)
//
//        } else {
//            doneButton.setOnTouchListener(this)
//            doneButton.isEnabled = true
//        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                val fragmentTag = GameBoardFragment().javaClass.simpleName
                val fragment = fragmentManager.findFragmentByTag(fragmentTag) as GameBoardFragment

                fragment.removeCardFromDeck(this.position)
                fragmentManager.popBackStack()
            }
        }
        return true
    }
}
