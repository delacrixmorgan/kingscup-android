package com.delacrixmorgan.kingscup.menu.language

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.newEmailIntent
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.LanguageType
import com.delacrixmorgan.kingscup.model.SoundType

class MenuLanguageFragment : Fragment(), LanguageListener {
    companion object {
        private const val TRANSLATION_CONTACT_EMAIL = "delacrixmorgan@gmail.com"
    }

    private val languageTypes = ArrayList(LanguageType.values().asList())

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    private val adapter by lazy {
        LanguageRecyclerViewAdapter(languageTypes, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        languageRecyclerView.adapter = adapter
//        languageRecyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(
//            context, R.anim.layout_animation_slide_right
//        )
//        languageRecyclerView.scheduleLayoutAnimation()
//        languageRecyclerView.addItemDecoration(
//            GridSpacingItemDecoration(
//                columnCount = 1,
//                spacing = 16,
//                shouldShowHorizontalMargin = true
//            )
//        )
//
//        saveButton.setOnClickListener {
//            soundEngine.playSound(it.context, SoundType.King)
//            Navigation.findNavController(view).navigateUp()
//        }

        setupLanguage()
    }

    private fun setupLanguage() {
        val preference = PreferenceHelper.getPreference(requireContext())
        val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
        val languageCode = preference[PreferenceHelper.LANGUAGE, currentLocale?.language]
        val languageType = LanguageType.values().firstOrNull { it.countryIso == languageCode }
            ?: LanguageType.English

        languageTypes.remove(languageType)
        languageTypes.add(0, languageType)

        adapter.selectedLanguageType = languageType
    }

    private fun savePreferenceLanguage(languageType: LanguageType) {
        val preference = PreferenceHelper.getPreference(requireContext())

        preference[PreferenceHelper.LANGUAGE] = languageType.countryIso
        resources.setLocale(languageType.countryIso)
        updateLayoutLanguage()
    }

    private fun updateLayoutLanguage() {
//        nameTextView.text = getString(R.string.app_name)
//        titleTextView.text = getString(R.string.fragment_menu_language_title_choose_language)
    }

    override fun onLanguageSelected(position: Int, languageType: LanguageType) {
//        languageRecyclerView.smoothScrollToPosition(position)
        savePreferenceLanguage(languageType)

        soundEngine.playSound(requireContext(), SoundType.Whoosh)
        adapter.selectedLanguageType = languageType

//        rootView.performHapticContextClick()
    }

    override fun onHelpTranslateSelected(position: Int) {
//        languageRecyclerView.smoothScrollToPosition(position)
        val intent = newEmailIntent(
            TRANSLATION_CONTACT_EMAIL,
            "King's Cup 🍺 - Translation Help",
            "Hey mate,\n\nI would love to translate King's Cup to [x] language."
        )
        startActivity(
            Intent.createChooser(
                intent,
                getString(R.string.fragment_menu_language_btn_help_translate)
            )
        )
    }
}