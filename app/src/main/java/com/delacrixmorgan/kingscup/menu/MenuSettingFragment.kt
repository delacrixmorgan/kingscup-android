package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.launchShareGameIntent
import com.delacrixmorgan.kingscup.common.launchWebsite
import kotlinx.android.synthetic.main.fragment_menu_setting.*

/**
 * MenuSettingFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuSettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.buildNumberTextView.text = "v${BuildConfig.VERSION_NAME}#${BuildConfig.VERSION_CODE}"

        this.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        this.guideCardView.setOnClickListener {
            val action = MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuGuideFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.languageCardView.setOnClickListener {
            val action = MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuLanguageFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.creditsCardView.setOnClickListener {
            val action = MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuCreditsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.sourceCodeCardView.setOnClickListener {
            launchWebsite("https://github.com/delacrixmorgan/kingscup-android")
        }

        this.shareCardView.setOnClickListener {
            val message = getString(R.string.preference_message_share_friend)
            launchShareGameIntent(message)
        }
    }
}