package com.delacrixmorgan.kingscup.menu

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import kotlinx.android.synthetic.main.dialog_credit.*
import kotlinx.android.synthetic.main.fragment_menu_setting.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 **/

class MenuSettingFragment : Fragment() {

    companion object {
        fun newInstance(listener: FragmentListener? = null): MenuSettingFragment {
            val fragment = MenuSettingFragment()
            fragment.fragmentListener = listener

            return fragment
        }
    }

    var fragmentListener: FragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.backButton.setOnClickListener {
            this.fragmentListener?.onBackPressed()
        }

        this.guideViewGroup.setOnClickListener {
            showFragmentSliding(context!!, MenuGuideFragment.newInstance(), Gravity.TOP)
        }

        this.creditViewGroup.setOnClickListener {
            this.displayCredits()
        }

        this.shareViewGroup.setOnClickListener {
            this.launchShareGameIntent()
        }

        this.languageButton.setOnClickListener {
            this.changeLanguage()
        }
    }

    private fun launchShareGameIntent() {
        val message = getString(R.string.preference_message_share_friend)
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(intent, getString(R.string.preference_title_share_friend)))
    }

    private fun changeLanguage() {
        val preference = PreferenceHelper.getPreference(context!!)
        val currentLanguage: LanguageType = when (preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]) {
            LanguageType.ENGLISH.countryIso -> LanguageType.CHINESE
            LanguageType.CHINESE.countryIso -> LanguageType.PORTUGUESE_BRAZIL
            LanguageType.PORTUGUESE_BRAZIL.countryIso -> LanguageType.DUTCH
            LanguageType.DUTCH.countryIso -> LanguageType.SPANISH
            LanguageType.SPANISH.countryIso -> LanguageType.ENGLISH
            else -> LanguageType.ENGLISH
        }

        preference[PreferenceHelper.LANGUAGE] = currentLanguage.countryIso
        setLocale(currentLanguage.countryIso, resources)

        updateSettingLanguage()
    }

    private fun updateSettingLanguage() {
        this.settingTextView.text = getString(R.string.preference_title)
        this.quickGuideTextView.text = getString(R.string.preference_title_quick_guide)
        this.creditTextView.text = getString(R.string.preference_title_credits_summary)
        this.shareTextView.text = getString(R.string.preference_title_share_friend)
        this.languageButton.text = getString(R.string.preference_current_language)
    }

    private fun displayCredits() {
        val creditDialog = Dialog(activity)

        with(creditDialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_credit)
            show()

            spartanImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://github.com/theleagueof/league-spartan") }
            kornerImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://github.com/JcMinarro/RoundKornerLayouts") }

            poiImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://github.com/YukiSora") }
            laysImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://en.wikipedia.org/wiki/Brazil") }
            kasperImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("http://kaspernooteboom.nl/khas") }

            freesoundImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://freesound.org/") }
            freepikImageView.setOnClickListener { this@MenuSettingFragment.launchWebsite("https://www.freepik.com") }

            doneButton.setOnClickListener {
                creditDialog.dismiss()
                SoundEngine.getInstance().playSound(context, SoundType.WHOOSH)
            }
        }
    }

    private fun launchWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}