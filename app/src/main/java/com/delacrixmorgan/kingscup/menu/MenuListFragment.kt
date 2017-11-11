package com.delacrixmorgan.kingscup.menu

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.game.GameActivity
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class MenuListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GameEngine.newInstance(activity)

        rateButton.setOnClickListener {
            rateButton.isEnabled = false
        }

        startButton.setOnClickListener {
            GameEngine.newInstance(activity)

            val intent = GameActivity.newIntent(activity)
            startActivity(intent)
        }

        settingButton.setOnClickListener {
            settingButton.isEnabled = false
            Helper.showFragment(activity, MenuSettingFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        rateButton.isEnabled = true
        startButton.isEnabled = true
        settingButton.isEnabled = true
    }
}
