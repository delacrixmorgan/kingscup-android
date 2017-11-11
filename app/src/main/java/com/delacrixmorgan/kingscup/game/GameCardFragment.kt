package com.delacrixmorgan.kingscup.game

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class GameCardFragment : Fragment(), View.OnTouchListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card = GameEngine.getInstance().currentCard

        Helper.animateButtonGrow(activity, doneButton)

        nameTextView.text = card.getmName()
        actionTextView.text = card.getmAction()

        lightLeftTextView.text = card.getmValue()
        darkRightTextView!!.text = card.getmValue()

        when (card.getmSuit()) {
            "Spade" -> {
                lightCenterImageView.setBackgroundResource(R.drawable.spade_pink)
                lightLeftImageView.setBackgroundResource(R.drawable.spade_pink)
                darkRightImageView!!.setBackgroundResource(R.drawable.spade_dark)
            }

            "Heart" -> {
                lightCenterImageView.setBackgroundResource(R.drawable.heart_pink)
                lightLeftImageView.setBackgroundResource(R.drawable.heart_pink)
                darkRightImageView!!.setBackgroundResource(R.drawable.heart_dark)
            }

            "Club" -> {
                lightCenterImageView.setBackgroundResource(R.drawable.club_pink)
                lightLeftImageView.setBackgroundResource(R.drawable.club_pink)
                darkRightImageView!!.setBackgroundResource(R.drawable.club_dark)
            }

            "Diamond" -> {
                lightCenterImageView.setBackgroundResource(R.drawable.diamond_pink)
                lightLeftImageView.setBackgroundResource(R.drawable.diamond_pink)
                darkRightImageView!!.setBackgroundResource(R.drawable.diamond_dark)
            }
        }


        if (GameEngine.getInstance().getmKingCounter() < 1) {
            val vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2000)

            doneButton.visibility = View.GONE
            doneButton.isEnabled = false

            val handler = Handler()
            handler.post {
                val animGrow = AnimationSet(true)
                animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out))
                lightCenterImageView.startAnimation(animGrow)
            }

            handler.postDelayed({
                GameEngine.getInstance().playSound(activity, "GAME_OVER")
                val fragment = fragmentManager.findFragmentByTag("GameBoardFragment") as GameBoardFragment
                fragment.updateFragment()
                fragmentManager.popBackStack()
            }, 2000)

        } else {
            doneButton.setOnTouchListener(this)
            doneButton.isEnabled = true
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                GameEngine.getInstance().playSound(activity, "CARD_WHOOSH")
                val fragment = fragmentManager.findFragmentByTag("GameBoardFragment") as GameBoardFragment
                fragment.updateFragment()
                fragmentManager.popBackStack()
            }
        }
        return true
    }
}
