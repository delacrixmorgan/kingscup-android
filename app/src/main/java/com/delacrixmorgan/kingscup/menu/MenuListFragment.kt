package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.game.GameBoardFragment
import kotlinx.android.synthetic.main.fragment_menu_list.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class MenuListFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_menu_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GameEngine.newInstance(activity)

        rateButton.setOnClickListener { showFragmentWithSlide(activity, MenuRateFragment.newInstance(), Gravity.START) }
        startButton.setOnClickListener { showFragmentWithSlide(activity, GameBoardFragment.newInstance(), Gravity.BOTTOM) }
        settingButton.setOnClickListener { showFragmentWithSlide(activity, MenuSettingFragment.newInstance(), Gravity.END) }


    }
}
