package com.delacrixmorgan.kingscup.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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

class MenuSettingFragment : Fragment() {

    companion object {
        private const val TRANSLATION_CONTACT_EMAIL = "delacrixmorgan@gmail.com"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu_setting, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildNumberTextView.text = "v${BuildConfig.VERSION_NAME}#${BuildConfig.VERSION_CODE}"

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        guideCardView.setOnClickListener {
            val action =
                MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuGuideFragment()
            Navigation.findNavController(view).navigate(action)
        }

        languageCardView.setOnClickListener {
            val action =
                MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuLanguageFragment()
            Navigation.findNavController(view).navigate(action)
        }

        creditsCardView.setOnClickListener {
            val action =
                MenuSettingFragmentDirections.actionMenuSettingFragmentToMenuCreditsFragment()
            Navigation.findNavController(view).navigate(action)
        }

        sourceCodeCardView.setOnClickListener {
            launchWebsite("https://github.com/delacrixmorgan/kingscup-android")
        }

        translationCardView.setOnClickListener {
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

        shareCardView.setOnClickListener {
            val message = getString(R.string.preference_message_share_friend)
            launchShareGameIntent(message)
        }
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
}