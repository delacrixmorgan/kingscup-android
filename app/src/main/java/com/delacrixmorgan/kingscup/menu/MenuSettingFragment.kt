package com.delacrixmorgan.kingscup.menu

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.FragmentListener
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.PreferenceHelper.set
import com.delacrixmorgan.kingscup.common.setLocale
import kotlinx.android.synthetic.main.fragment_menu_setting.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class MenuSettingFragment : BaseFragment() {
    companion object {
        fun newInstance(fragmentListener: FragmentListener? = null): MenuSettingFragment {
            val fragment = MenuSettingFragment()
            fragment.fragmentListener = fragmentListener

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
            this@MenuSettingFragment.fragmentListener?.onBackPressed()
        }

        this.creditLayout.setOnClickListener {
            this@MenuSettingFragment.displayCredits()
        }

        this.languageTextView.setOnClickListener {
            this@MenuSettingFragment.changeLanguage()
        }
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
        this.titleTextView.text = activity.getString(R.string.app_name)
        this.settingTextView.text = activity.getString(R.string.preference_title)
        this.creditTextView.text = activity.getString(R.string.preference_credits_summary)
        this.shareTextView.text = activity.getString(R.string.preference_share_friend)
        this.languageTextView.text = getString(R.string.current_language)
    }

    private fun displayCredits() {
        val creditDialog = Dialog(activity)

        creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        creditDialog.setContentView(R.layout.view_credit)

        creditDialog.show()

        val spartanImageView = creditDialog.findViewById<ImageView>(R.id.spartanImageView)
        val kornerImageView = creditDialog.findViewById<ImageView>(R.id.kornerImageView)
        val doneButton = creditDialog.findViewById<Button>(R.id.doneButton)

        spartanImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/theleagueof/league-spartan")
            startActivity(intent)
        }

        kornerImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/JcMinarro/RoundKornerLayouts")
            startActivity(intent)
        }

        doneButton.setOnClickListener { creditDialog.dismiss() }
    }
}
