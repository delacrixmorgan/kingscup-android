package com.delacrixmorgan.kingscup.menu.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.model.LanguageType
import kotlinx.android.synthetic.main.fragment_menu_language.*

/**
 * MenuLanguageFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2018.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuLanguageFragment : Fragment(), LanguageListener {
    private lateinit var languageAdapter: LanguageRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deckAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.layout_animation_slide_right)
        val cellHeight = (resources.displayMetrics.heightPixels / 2.5).toInt()
        val cellWidth = (cellHeight * (10.0 / 16.0)).toInt()

        this.languageAdapter = LanguageRecyclerViewAdapter(
                cellHeight = cellHeight,
                cellWidth = cellWidth,
                languageTypes = LanguageType.values(),
                listener = this
        )

        this.languageRecyclerView.adapter = this.languageAdapter
        this.languageRecyclerView.layoutAnimation = deckAnimation
        this.languageRecyclerView.scheduleLayoutAnimation()

        this.translateButton.setOnClickListener {
            // TODO Email To Me
        }

        this.saveButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun savePreferenceLanguage(languageType: LanguageType) {
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
    }

    private fun updateLayoutLanguage() {
        this.nameTextView.text = getString(R.string.app_name)
        this.titleTextView.text = getString(R.string.fragment_menu_language_title_choose_language)
        this.translateButton.text = getString(R.string.fragment_menu_language_btn_help_translate)
    }

    override fun onLanguageSelected(languageType: LanguageType) {
        this.resources.setLocale(languageType.countryIso)
        updateLayoutLanguage()
    }
}