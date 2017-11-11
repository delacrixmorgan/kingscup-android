package com.delacrixmorgan.kingscup.guide

import android.app.Fragment
import android.content.Context
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
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.game.GameBoardFragment
import kotlinx.android.synthetic.main.fragment_guide.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class TutorialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = sGuideAdapter(activity)
        tabLayout.setupWithViewPager(viewPager, true)

        val handler = Handler()
        handler.post {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = 2500
            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE
            animation.repeatMode = Animation.REVERSE

            symbolImageView.startAnimation(animation)
        }

        skipButton.setOnClickListener {

            activity.fragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContainer, GameBoardFragment(), GameBoardFragment::class.simpleName)
                    .commit()
        }
    }

    private inner class sGuideAdapter internal constructor(private val mContext: Context) : PagerAdapter() {
        private var mGuideText: TextView? = null

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val rootView = inflater.inflate(R.layout.view_guide, collection, false)

            mGuideText = rootView.findViewById<View>(R.id.tv_guide) as TextView
            mGuideText!!.text = GameEngine.getInstance().getmGuideArray()[position]

            collection.addView(rootView)

            return rootView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as TextView)
        }

        override fun getCount(): Int {
            return GameEngine.getInstance().getmGuideArray().size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}
