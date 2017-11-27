package com.delacrixmorgan.kingscup.menu

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import kotlinx.android.synthetic.main.fragment_menu_setting.*
import java.util.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class MenuSettingFragment : Fragment() {
    companion object {
        fun newInstance(): MenuSettingFragment = MenuSettingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_menu_setting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.backButton.setOnClickListener {
            fragmentManager.popBackStack()
        }

        this.languageTextView.setOnClickListener {
            this.changeLanguage()
        }
    }

    private fun changeLanguage() {
        val languageCodeList = arrayListOf("en", "zh")
        val currentLanguage = Locale.getDefault().language
        val locale = Locale(when (currentLanguage) {
            languageCodeList[0] -> languageCodeList[1]
            languageCodeList[1] -> languageCodeList[0]
            else -> languageCodeList[0]
        })

        with(resources) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            updateConfiguration(configuration, displayMetrics)
            onConfigurationChanged(configuration)
        }

        Locale.setDefault(locale)
        this.updateSettingLanguage()
    }

    private fun updateSettingLanguage() {
        this.titleTextView.text = activity.getString(R.string.app_name)
        this.settingTextView.text = activity.getString(R.string.preference_title)
        this.creditTextView.text = activity.getString(R.string.preference_credits_summary)
        this.shareTextView.text = activity.getString(R.string.preference_share_friend)

        this.languageTextView.text = Locale.getDefault().displayLanguage
    }

    private fun legacyCode() {
//        mLanguage!!.summary = Helper.mLanguageItems[activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0)]
//        mLanguage!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            AlertDialog.Builder(activity)
//                    .setTitle("Language")
//                    .setSingleChoiceItems(Helper.mLanguageItems, activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0)
//                    ) { dialog, item ->
//                        val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
//                        editor.putInt(Helper.LANGUAGE_PREFERENCE, item)
//                        editor.apply()
//
//                        mLanguage!!.summary = Helper.mLanguageItems[item]
//        var languageCode = Locale.getDefault().language
//
//        when (context.getSharedPreferences(Helper.SHARED_PREFERENCE, Context.MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 1)) {
//            1 -> languageCode = "en"
//            2 -> languageCode = "zh"
//        }
//
//        val configuration = Configuration(context.resources.configuration)
//        configuration.locale = Locale(languageCode)
//        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
//
//                        GameEngine.newInstance(activity)
//                        val refresh = Intent(activity, MainActivity::class.java)
//                        activity.finish()
//                        startActivity(refresh)
//                    }
//                    .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }
//                    .show()
//
//            false
//        }

//        mQuickGuide!!.isChecked = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.QUICK_GUIDE_PREFERENCE, true)
//        mQuickGuide!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
//            val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
//            editor.putBoolean(Helper.QUICK_GUIDE_PREFERENCE, newValue as Boolean)
//            editor.apply()
//            true
//        }
//
//        mSoundEffects!!.isChecked = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true)
//        mSoundEffects!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
//            val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
//            editor.putBoolean(Helper.SOUND_EFFECTS_PREFERENCE, newValue as Boolean)
//            editor.apply()
//            true
//        }
//
//
//
//
//        mCreditLibrary!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//            val creditDialog = Dialog(activity)
//
//            creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            creditDialog.setContentView(R.layout.view_credit)
//            creditDialog.setTitle("Credits")
//
//            creditDialog.show()
//
//            val spartanImageView = creditDialog.findViewById<View>(R.id.iv_spartan_github) as ImageView
//            spartanImageView.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://github.com/theleagueof/league-spartan")
//                startActivity(intent)
//            }
//
//            val jenkinsImageView = creditDialog.findViewById<View>(R.id.iv_jenkins_github) as ImageView
//            jenkinsImageView.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://github.com/chrisjenx/Calligraphy")
//                startActivity(intent)
//            }
//
//            val balysImageView = creditDialog.findViewById<View>(R.id.iv_balys_github) as ImageView
//            balysImageView.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("https://github.com/balysv/material-ripple")
//                startActivity(intent)
//            }
//
//            val doneButton = creditDialog.findViewById<View>(R.id.button_done) as Button
//            doneButton.setOnClickListener { creditDialog.dismiss() }
//
//            false
//        }
//
//        try {
//            mVersion!!.summary = activity.packageManager.getPackageInfo(activity.packageName, 0).versionName
//            mVersion!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                mMonkeyCounter = if (mMonkeyCounter > 2) 0 else mMonkeyCounter
//                Toast.makeText(activity, String(Character.toChars(unicode[mMonkeyCounter++])), Toast.LENGTH_SHORT).show()
//
//                false
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
    }
}
