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
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class MenuFragment : BaseFragment() {

    companion object {
        fun newInstance(): MenuFragment = MenuFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateButton.setOnClickListener { showFragmentWithSlide(activity, MenuRateFragment.newInstance(), Gravity.START) }
        settingButton.setOnClickListener { showFragmentWithSlide(activity, MenuSettingFragment.newInstance(), Gravity.END) }

        startButton.setOnClickListener {
            GameEngine.newInstance(activity)
            showFragmentWithSlide(activity, GameBoardFragment.newInstance(), Gravity.BOTTOM)
        }
    }
}
