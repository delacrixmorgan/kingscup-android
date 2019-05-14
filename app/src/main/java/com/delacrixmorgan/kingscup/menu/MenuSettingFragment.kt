package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.launchShareGameIntent
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.model.LanguageType
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

        this.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        this.guideViewGroup.setOnClickListener {
            val action = MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuGuideFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.shareViewGroup.setOnClickListener {
            val message = getString(R.string.preference_message_share_friend)
            launchShareGameIntent(message)
        }

        this.creditViewGroup.setOnClickListener {
            val action = MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuCreditsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        this.languageButton.setOnClickListener {
            changeLanguage()
        }
    }

    private fun changeLanguage() {
        val context = this.context ?: return
        val preference = PreferenceHelper.getPreference(context)
        val languageTypes = LanguageType.values()
        var currentLanguage: LanguageType = languageTypes.first {
            it.countryIso == preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]
        }

        currentLanguage = if (currentLanguage == languageTypes.last()) {
            languageTypes.first()
        } else {
            languageTypes[currentLanguage.ordinal + 1]
        }

        preference[PreferenceHelper.LANGUAGE] = currentLanguage.countryIso
        this.resources.setLocale(currentLanguage.countryIso)

        this.updateSettingLanguage()
    }

    private fun updateSettingLanguage() {
        this.settingTextView.text = getString(R.string.preference_title)
        this.quickGuideTextView.text = getString(R.string.preference_title_quick_guide)
        this.creditTextView.text = getString(R.string.preference_title_credits_summary)
        this.shareTextView.text = getString(R.string.preference_title_share_friend)
        this.languageButton.text = getString(R.string.preference_current_language)
    }
}