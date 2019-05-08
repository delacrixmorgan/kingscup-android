package com.delacrixmorgan.kingscup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

/**
 * LaunchFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2018.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class LaunchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Check Language
//        val preference = PreferenceHelper.getPreference(context!!)
//        val selectedLanguage = LanguageType.values().asList().first {
//            it.countryIso == preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]
//        }
//
//        setLocale(selectedLanguage.countryIso, resources)

        val action = LaunchFragmentDirections.actionLaunchFragmentToMenuFragment()
        Navigation.findNavController(view).navigate(action)
    }
}