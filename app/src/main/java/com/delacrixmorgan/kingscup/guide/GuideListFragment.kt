package com.delacrixmorgan.kingscup.guide

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.game.GameBoardFragment
import kotlinx.android.synthetic.main.fragment_guide_list.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class GuideListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_guide_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = GuideAdapter()
        tabLayout.setupWithViewPager(viewPager, true)

        setupHeartAnimation()
        skipButton.setOnClickListener { showGameBoardFragment() }
    }

    private fun setupHeartAnimation() {
        val handler = Handler()
        handler.post {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = 2500
            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE
            animation.repeatMode = Animation.REVERSE

            symbolImageView.startAnimation(animation)
        }
    }

    private fun showGameBoardFragment() {
        activity.fragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, GameBoardFragment(), GameBoardFragment::class.simpleName)
                .commit()
    }

    private inner class GuideAdapter : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val rootView = LayoutInflater.from(activity).inflate(R.layout.view_guide_list, collection, false)

            rootView.findViewById<TextView>(R.id.guideTextView).text = GameEngine.getInstance()?.guideList!![position]
            collection.addView(rootView)

            return rootView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as TextView)
        }

        override fun getCount(): Int {
            return GameEngine.getInstance()?.guideList!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}
