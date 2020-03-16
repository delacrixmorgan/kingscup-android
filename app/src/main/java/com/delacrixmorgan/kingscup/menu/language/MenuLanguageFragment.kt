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
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.performHapticContextClick
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.LanguageType
import com.delacrixmorgan.kingscup.model.SoundType
import kotlinx.android.synthetic.main.fragment_menu_language.*

class MenuLanguageFragment : Fragment(), LanguageListener {
    private lateinit var languageAdapter: LanguageRecyclerViewAdapter

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
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

        val deckAnimation = AnimationUtils.loadLayoutAnimation(
            context, R.anim.layout_animation_slide_right
        )

        languageAdapter = LanguageRecyclerViewAdapter(
            languageTypes = LanguageType.values(),
            listener = this
        )

        languageRecyclerView.adapter = languageAdapter
        languageRecyclerView.layoutAnimation = deckAnimation
        languageRecyclerView.scheduleLayoutAnimation()

        languageTextView.text = getString(R.string.preference_current_language)

        saveButton.setOnClickListener {
            soundEngine.playSound(it.context, SoundType.King)
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun savePreferenceLanguage(languageType: LanguageType) {
        val preference = PreferenceHelper.getPreference(requireContext())

        preference[PreferenceHelper.LANGUAGE] = languageType.countryIso
        resources.setLocale(languageType.countryIso)
        updateLayoutLanguage()
    }

    private fun updateLayoutLanguage() {
        nameTextView.text = getString(R.string.app_name)
        titleTextView.text = getString(R.string.fragment_menu_language_title_choose_language)
        languageTextView.text = getString(R.string.preference_current_language)
    }

    override fun onLanguageSelected(languageType: LanguageType) {
        soundEngine.playSound(requireContext(), SoundType.Whoosh)
        savePreferenceLanguage(languageType)

        rootView.performHapticContextClick()
    }
}