package com.delacrixmorgan.kingscup.fragment

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.shared.Helper

import android.content.Context.MODE_PRIVATE

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class GuideFragment : Fragment() {

    private var mSymbol: ImageView? = null
    private var mSkipButton: Button? = null
    private var mViewPager: ViewPager? = null
    private var mTabLayout: TabLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_guide, container, false)

        mSymbol = rootView.findViewById(R.id.iv_guide_symbol)
        mSkipButton = rootView.findViewById(R.id.btn_skip)
        mViewPager = rootView.findViewById(R.id.view_pager)
        mTabLayout = rootView.findViewById(R.id.tab_layout)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.QUICK_GUIDE_PREFERENCE, true)) {
            Helper.showFragment(activity, SelectFragment(), GuideFragment.TAG)
        }

        //Helper.animateButtonGrow(getActivity(), mSkipButton);

        mViewPager!!.adapter = sGuideAdapter(activity)
        mTabLayout!!.setupWithViewPager(mViewPager, true)

        val handler = Handler()
        handler.post {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = 2500
            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE
            animation.repeatMode = Animation.REVERSE

            mSymbol!!.startAnimation(animation)
        }

        mSkipButton!!.setOnClickListener {
            GameEngine.getInstance().playSound(activity, "BUTTON_CLICK")
            val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
            editor.putBoolean(Helper.QUICK_GUIDE_PREFERENCE, false)
            editor.apply()

            Helper.showFragment(activity, SelectFragment(), GuideFragment.TAG)
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

    companion object {
        private val TAG = "GuideFragment"
    }


}
