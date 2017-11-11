package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.model.Card
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameCardFragment : Fragment(), View.OnTouchListener {

    companion object {
        const val GAMECARDFRAGMENT_CARD = "GameCardFragment.Card"
        fun newInstance(card: Card? = null): GameCardFragment {

            val fragment = GameCardFragment()
            val args = Bundle()

            args.putSerializable(GAMECARDFRAGMENT_CARD, card)

            fragment.arguments = args
            return fragment
        }
    }

    lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.card = arguments.getSerializable(GAMECARDFRAGMENT_CARD) as Card
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Helper.animateButtonGrow(activity, doneButton)
        doneButton.setOnTouchListener(this)

        nameTextView.text = card.header
        actionTextView.text = card.body

        lightLeftTextView.text = card.rank
        darkRightTextView!!.text = card.rank
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
                fragmentManager.popBackStack()
            }
        }
        return true
    }
}
