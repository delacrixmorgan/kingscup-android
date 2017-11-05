package com.delacrixmorgan.kingscup.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.model.Card
import com.delacrixmorgan.kingscup.shared.Helper

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class CardFragment : Fragment(), View.OnTouchListener {

    private var mTextName: TextView? = null
    private var mTextAction: TextView? = null
    private var mTextLightValue: TextView? = null
    private var mTextDarkValue: TextView? = null
    private var mLightLargeSymbol: ImageView? = null
    private var mLightSmallSymbol: ImageView? = null
    private var mDarkSmallSymbol: ImageView? = null
    private var mDoneButton: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_card, container, false)

        mTextName = rootView.findViewById(R.id.card_name)
        mTextAction = rootView.findViewById(R.id.card_action)
        mTextLightValue = rootView.findViewById(R.id.card_light_value)
        mTextDarkValue = rootView.findViewById(R.id.card_dark_value)

        mLightLargeSymbol = rootView.findViewById(R.id.card_light_lg_symbol)
        mLightSmallSymbol = rootView.findViewById(R.id.card_light_sm_symbol)
        mDarkSmallSymbol = rootView.findViewById(R.id.card_dark_sm_symbol)

        mDoneButton = rootView.findViewById(R.id.fragment_card_done_button)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card = GameEngine.getInstance().currentCard

        Helper.animateButtonGrow(activity, mDoneButton)

        mTextName!!.text = card.getmName()
        mTextAction!!.text = card.getmAction()
        mTextLightValue!!.text = card.getmValue()
        mTextDarkValue!!.text = card.getmValue()

        when (card.getmSuit()) {
            "Spade" -> {
                mLightLargeSymbol!!.setBackgroundResource(R.drawable.spade_pink)
                mLightSmallSymbol!!.setBackgroundResource(R.drawable.spade_pink)
                mDarkSmallSymbol!!.setBackgroundResource(R.drawable.spade_dark)
            }

            "Heart" -> {
                mLightLargeSymbol!!.setBackgroundResource(R.drawable.heart_pink)
                mLightSmallSymbol!!.setBackgroundResource(R.drawable.heart_pink)
                mDarkSmallSymbol!!.setBackgroundResource(R.drawable.heart_dark)
            }

            "Club" -> {
                mLightLargeSymbol!!.setBackgroundResource(R.drawable.club_pink)
                mLightSmallSymbol!!.setBackgroundResource(R.drawable.club_pink)
                mDarkSmallSymbol!!.setBackgroundResource(R.drawable.club_dark)
            }

            "Diamond" -> {
                mLightLargeSymbol!!.setBackgroundResource(R.drawable.diamond_pink)
                mLightSmallSymbol!!.setBackgroundResource(R.drawable.diamond_pink)
                mDarkSmallSymbol!!.setBackgroundResource(R.drawable.diamond_dark)
            }
        }


        if (GameEngine.getInstance().getmKingCounter() < 1) {
            val vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2000)

            mDoneButton!!.visibility = View.GONE
            mDoneButton!!.isEnabled = false

            val handler = Handler()
            handler.post {
                val animGrow = AnimationSet(true)
                animGrow.addAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_out))
                mLightLargeSymbol!!.startAnimation(animGrow)
            }

            handler.postDelayed({
                GameEngine.getInstance().playSound(activity, "GAME_OVER")
                val fragment = fragmentManager.findFragmentByTag("SelectFragment") as SelectFragment
                fragment.updateFragment()
                fragmentManager.popBackStack()
            }, 2000)

        } else {
            mDoneButton!!.setOnTouchListener(this)
            mDoneButton!!.isEnabled = true
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                GameEngine.getInstance().playSound(activity, "CARD_WHOOSH")
                val fragment = fragmentManager.findFragmentByTag("SelectFragment") as SelectFragment
                fragment.updateFragment()
                fragmentManager.popBackStack()
            }
        }
        return true
    }
}
