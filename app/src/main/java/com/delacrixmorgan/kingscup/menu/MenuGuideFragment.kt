package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.GuideType
import kotlinx.android.synthetic.main.fragment_menu_guide.*
import kotlinx.android.synthetic.main.view_guide_list.view.*

class MenuGuideFragment : Fragment() {

    private var guideList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GuideType.values().forEach {
            guideList.add(it.getLocalisedText(view.context))
        }

        viewPager.adapter = GuideAdapter()
        tabLayout.setupWithViewPager(viewPager, true)

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    private inner class GuideAdapter : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val rootView =
                LayoutInflater.from(activity).inflate(R.layout.view_guide_list, collection, false)

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