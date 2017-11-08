package com.delacrixmorgan.kingscup.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.GameActivity
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.shared.Helper
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class MenuFragment : Fragment() {

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }

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
            startActivity(Intent(activity, GameActivity::class.java))
        }

        settingButton.setOnClickListener {
            settingButton.isEnabled = false
            Helper.showFragment(activity, SettingFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        rateButton.isEnabled = true
        startButton.isEnabled = true
        settingButton.isEnabled = true
    }
}
