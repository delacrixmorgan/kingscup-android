package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.game.GameLoadFragment
import com.delacrixmorgan.kingscup.model.LoadType
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * MenuFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuFragment : Fragment(), FragmentListener {

    private var isGameStarted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        this.rateButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToMenuRateFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.settingButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToMenuSettingFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.startButton.setOnClickListener {
            if (!this.isGameStarted) {
                this.isGameStarted = !this.isGameStarted
                context.showFragmentSliding(GameLoadFragment.newInstance(LoadType.NEW_GAME), Gravity.BOTTOM)

                Handler().postDelayed({
                    run {
                        isGameStarted = false
                    }
                }, 2000)
            }
        }
    }

    override fun onBackPressed() {
        this.activity?.supportFragmentManager?.popBackStack()
        getString(R.string.app_name).let {
            this.appNameTextView.text = it
            this.activity?.title = it
        }
    }
}