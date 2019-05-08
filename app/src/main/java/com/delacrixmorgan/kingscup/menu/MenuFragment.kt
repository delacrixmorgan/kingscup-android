package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.model.GameType
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * MenuFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.rateButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToMenuRateFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.settingButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToMenuSettingFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.startButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToGameLoadFragment(GameType.NEW_GAME)
            Navigation.findNavController(view).navigate(action)
        }
    }
}