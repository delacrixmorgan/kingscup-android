package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.game.GameActivity
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

        rateButton.setOnClickListener { showAddFragmentSlideDown(activity, MenuRateFragment()) }
        startButton.setOnClickListener { startActivity(GameActivity.newIntent(activity)) }
        settingButton.setOnClickListener { showAddFragmentSlideDown(activity, MenuSettingFragment()) }
    }
}
