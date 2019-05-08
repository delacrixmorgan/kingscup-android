package com.delacrixmorgan.kingscup.menu

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import kotlinx.android.synthetic.main.dialog_credit.*
import kotlinx.android.synthetic.main.fragment_menu_setting.*

/**
 * MenuSettingFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
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
            context?.showFragmentSliding(MenuGuideFragment.newInstance(), Gravity.TOP)
        }

        this.shareViewGroup.setOnClickListener {
            val message = getString(R.string.preference_message_share_friend)
            context?.launchShareGameIntent(message)
        }

        this.creditViewGroup.setOnClickListener {
            displayCredits()
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
        setLocale(currentLanguage.countryIso, resources)

        this.updateSettingLanguage()
    }

    private fun updateSettingLanguage() {
        this.settingTextView.text = getString(R.string.preference_title)
        this.quickGuideTextView.text = getString(R.string.preference_title_quick_guide)
        this.creditTextView.text = getString(R.string.preference_title_credits_summary)
        this.shareTextView.text = getString(R.string.preference_title_share_friend)
        this.languageButton.text = getString(R.string.preference_current_language)
    }

    private fun displayCredits() {
        val context = this.context ?: return
        val creditDialog = Dialog(context)

        with(creditDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_credit)
            show()

            spartanImageView.setOnClickListener { context.launchWebsite("https://github.com/theleagueof/league-spartan") }
            kornerImageView.setOnClickListener { context.launchWebsite("https://github.com/JcMinarro/RoundKornerLayouts") }

            chineseImageView.setOnClickListener { context.launchWebsite("https://en.wikipedia.org/wiki/China") }
            portugueseImageView.setOnClickListener { context.launchWebsite("https://en.wikipedia.org/wiki/Brazil") }
            netherlandsImageView.setOnClickListener { context.launchWebsite("https://en.wikipedia.org/wiki/Netherlands") }
            spanishImageView.setOnClickListener { context.launchWebsite("https://en.wikipedia.org/wiki/Spain") }

            freesoundImageView.setOnClickListener { context.launchWebsite("https://freesound.org/") }
            freepikImageView.setOnClickListener { context.launchWebsite("https://freepik.com") }

            doneButton.setOnClickListener {
                creditDialog.dismiss()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
            }
        }
    }
}