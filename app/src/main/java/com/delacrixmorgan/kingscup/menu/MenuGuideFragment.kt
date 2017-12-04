package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_menu_guide.*
import kotlinx.android.synthetic.main.view_guide_list.view.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class MenuGuideFragment : BaseFragment() {
    companion object {
        fun newInstance(): MenuGuideFragment {
            return MenuGuideFragment()
        }
    }

    lateinit var guideList: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_menu_guide, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.guideList = context.resources.getStringArray(R.array.guide).toList()

        this.viewPager.adapter = GuideAdapter()
        this.tabLayout.setupWithViewPager(viewPager, true)

        this.backButton.setOnClickListener {
            this.fragmentManager.popBackStack()
        }
    }

    private inner class GuideAdapter : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val rootView = LayoutInflater.from(activity).inflate(R.layout.view_guide_list, collection, false)

            rootView.guideTextView.text = guideList[position]
            collection.addView(rootView)

            return rootView
        }

        override fun getCount(): Int = guideList.size
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as TextView)
        }
    }
}
