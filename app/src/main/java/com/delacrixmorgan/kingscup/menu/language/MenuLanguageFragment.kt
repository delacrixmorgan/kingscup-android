package com.delacrixmorgan.kingscup.menu.language

import android.content.Intent
import android.net.Uri
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
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.performHapticContextClick
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.model.LanguageType
import com.delacrixmorgan.kingscup.model.SoundType
import com.delacrixmorgan.kingscup.model.formatText
import kotlinx.android.synthetic.main.fragment_menu_language.*

/**
 * MenuLanguageFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2018.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuLanguageFragment : Fragment(), LanguageListener {
    companion object {
        private const val TRANSLATION_CONTACT_EMAIL = "delacrixmorgan@gmail.com"
    }

    private lateinit var languageAdapter: LanguageRecyclerViewAdapter
    private val selectedLanguage: LanguageType
        get() {
            val preference = PreferenceHelper.getPreference(requireContext())
            val preferenceCountryIso = preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]
            return LanguageType.values().firstOrNull {
                it.countryIso == preferenceCountryIso
            } ?: LanguageType.ENGLISH
        }

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

        this.languageTextView.text = this.selectedLanguage.formatText()

        this.translateButton.setOnClickListener {
            val intent = newEmailIntent(TRANSLATION_CONTACT_EMAIL, "King's Cup - Translation Help", "Hey mate,\n\nI would love to translate King's Cup to [x] language.")
            startActivity(Intent.createChooser(intent, "Help Translate"))
        }

        this.saveButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun savePreferenceLanguage(languageType: LanguageType) {
        val preference = PreferenceHelper.getPreference(requireContext())

        preference[PreferenceHelper.LANGUAGE] = languageType.countryIso
        this.resources.setLocale(languageType.countryIso)
        updateLayoutLanguage()
    }

    private fun updateLayoutLanguage() {
        this.nameTextView.text = getString(R.string.app_name)
        this.titleTextView.text = getString(R.string.fragment_menu_language_title_choose_language)
        this.translateButton.text = getString(R.string.fragment_menu_language_btn_help_translate)
        this.languageTextView.text = this.selectedLanguage.formatText()
    }

    private fun newEmailIntent(recipient: String, subject: String?, body: String?): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))

        if (!subject.isNullOrBlank()) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        }

        if (!body.isNullOrBlank()) {
            intent.putExtra(Intent.EXTRA_TEXT, body)
        }

        return intent
    }

    override fun onLanguageSelected(languageType: LanguageType) {
        SoundEngine.getInstance().playSound(requireContext(), SoundType.WHOOSH)
        savePreferenceLanguage(languageType)

        this.rootView.performHapticContextClick()
    }
}