package com.delacrixmorgan.kingscup

import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.setLocale

/**
 * LaunchFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2018.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class LaunchFragment : Fragment() {
    private val preference: SharedPreferences by lazy {
        PreferenceHelper.getPreference(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SoundEngine.newInstance(view.context)
        requireActivity().volumeControlStream = AudioManager.STREAM_MUSIC

        if (this.preference[PreferenceHelper.ONBOARDING, PreferenceHelper.ONBOARDING_DEFAULT]) {
            setupLocale()
            val action = LaunchFragmentDirections.actionLaunchFragmentToMenuFragment()
            Navigation.findNavController(view).navigate(action)
        } else {
            this.preference[PreferenceHelper.ONBOARDING] = true
            val action = LaunchFragmentDirections.actionLaunchFragmentToMenuLanguageFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun setupLocale() {
        val preferenceCountryIso = this.preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]
        this.resources.setLocale(preferenceCountryIso)
    }
}