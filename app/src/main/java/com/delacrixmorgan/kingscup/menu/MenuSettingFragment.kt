package com.delacrixmorgan.kingscup.menu

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.FragmentListener
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.setLocale
import kotlinx.android.synthetic.main.dialog_credit.*
import kotlinx.android.synthetic.main.fragment_menu_setting.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 **/

class MenuSettingFragment : BaseFragment() {
    companion object {
        fun newInstance(listener: FragmentListener? = null): MenuSettingFragment {
            val fragment = MenuSettingFragment()
            fragment.fragmentListener = listener

            return fragment
        }
    }

    var fragmentListener: FragmentListener? = null
    private val languageCodeList = arrayListOf("en", "zh")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_menu_setting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.backButton.setOnClickListener {
            this.fragmentListener?.onBackPressed()
        }

        this.guideViewGroup.setOnClickListener {
            showFragmentSliding(activity, MenuGuideFragment.newInstance(), Gravity.TOP)
        }

        this.creditViewGroup.setOnClickListener {
            this.displayCredits()
        }

        this.shareViewGroup.setOnClickListener {
            this.launchShareGameIntent()
        }

        this.unlockButton.setOnClickListener {
            showFragmentSliding(activity, MenuUnlockFragment.newInstance(), Gravity.BOTTOM)
        }

        this.languageButton.setOnClickListener {
            this.changeLanguage()
        }
    }

    private fun launchShareGameIntent() {
        val message = "Found the perfect drinking game \"King's Cup\". No beverages included!\n" + "https://play.google.com/store/apps/details?id=com.delacrixmorgan.kingscup"
        val intent = Intent(Intent.ACTION_SEND)
        
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(intent, context.getString(R.string.preference_share_friend)))
    }

    private fun changeLanguage() {
        val preference = PreferenceHelper.getPreference(context)
        val currentLanguage = if (preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT] == PreferenceHelper.LANGUAGE_DEFAULT) {
            this.languageCodeList[1]
        } else {
            this.languageCodeList[0]
        }

        preference[PreferenceHelper.LANGUAGE] = currentLanguage
        setLocale(currentLanguage, resources)

        this.updateSettingLanguage()
    }

    private fun updateSettingLanguage() {
        this.settingTextView.text = getString(R.string.preference_title)
        this.quickGuideTextView.text = getString(R.string.quick_guide)
        this.creditTextView.text = getString(R.string.preference_credits_summary)
        this.shareTextView.text = getString(R.string.preference_share_friend)
        this.languageButton.text = getString(R.string.current_language)
    }

    private fun displayCredits() {
        val creditDialog = Dialog(activity)

        creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        creditDialog.setContentView(R.layout.dialog_credit)
        creditDialog.show()

        creditDialog.spartanImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/theleagueof/league-spartan")
            startActivity(intent)
        }

        creditDialog.kornerImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/JcMinarro/RoundKornerLayouts")
            startActivity(intent)
        }

        creditDialog.doneButton.setOnClickListener { creditDialog.dismiss() }
    }
}
