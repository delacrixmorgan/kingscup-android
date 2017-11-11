package com.delacrixmorgan.kingscup.menu

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.delacrixmorgan.kingscup.MainActivity
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.Helper

import android.content.Context.MODE_PRIVATE

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class MenuSettingFragment : PreferenceFragment() {

    internal val unicode = intArrayOf(0x1F648, 0x1F649, 0x1F64A)
    private var mQuickGuide: SwitchPreference? = null
    private var mSoundEffects: SwitchPreference? = null
    private var mLanguage: Preference? = null
    private var mCreditLibrary: Preference? = null
    private var mVersion: Preference? = null
    private var mMonkeyCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)

        mQuickGuide = findPreference("quick_guide") as SwitchPreference
        mSoundEffects = findPreference("sound_effects") as SwitchPreference

        mLanguage = findPreference("language")
        mCreditLibrary = findPreference("credit_library")
        mVersion = findPreference("version_number")

        mMonkeyCounter = 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mQuickGuide!!.isChecked = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.QUICK_GUIDE_PREFERENCE, true)
        mQuickGuide!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
            editor.putBoolean(Helper.QUICK_GUIDE_PREFERENCE, newValue as Boolean)
            editor.apply()
            true
        }

        mSoundEffects!!.isChecked = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getBoolean(Helper.SOUND_EFFECTS_PREFERENCE, true)
        mSoundEffects!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
            editor.putBoolean(Helper.SOUND_EFFECTS_PREFERENCE, newValue as Boolean)
            editor.apply()
            true
        }

        mLanguage!!.summary = Helper.mLanguageItems[activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0)]
        mLanguage!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            AlertDialog.Builder(activity)
                    .setTitle("Language")
                    .setSingleChoiceItems(Helper.mLanguageItems, activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).getInt(Helper.LANGUAGE_PREFERENCE, 0)
                    ) { dialog, item ->
                        val editor = activity.getSharedPreferences(Helper.SHARED_PREFERENCE, MODE_PRIVATE).edit()
                        editor.putInt(Helper.LANGUAGE_PREFERENCE, item)
                        editor.apply()

                        mLanguage!!.summary = Helper.mLanguageItems[item]
                        Helper.setLocaleLanguage(activity)

                        val refresh = Intent(activity, MainActivity::class.java)
                        activity.finish()
                        startActivity(refresh)
                    }
                    .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }
                    .show()

            false
        }


        mCreditLibrary!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val creditDialog = Dialog(activity)

            creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            creditDialog.setContentView(R.layout.view_credit)
            creditDialog.setTitle("Credits")

            creditDialog.show()

            val spartanImageView = creditDialog.findViewById<View>(R.id.iv_spartan_github) as ImageView
            spartanImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/theleagueof/league-spartan")
                startActivity(intent)
            }

            val jenkinsImageView = creditDialog.findViewById<View>(R.id.iv_jenkins_github) as ImageView
            jenkinsImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/chrisjenx/Calligraphy")
                startActivity(intent)
            }

            val balysImageView = creditDialog.findViewById<View>(R.id.iv_balys_github) as ImageView
            balysImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/balysv/material-ripple")
                startActivity(intent)
            }

            val doneButton = creditDialog.findViewById<View>(R.id.button_done) as Button
            doneButton.setOnClickListener { creditDialog.dismiss() }

            false
        }

        try {
            mVersion!!.summary = activity.packageManager.getPackageInfo(activity.packageName, 0).versionName
            mVersion!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                mMonkeyCounter = if (mMonkeyCounter > 2) 0 else mMonkeyCounter
                Toast.makeText(activity, String(Character.toChars(unicode[mMonkeyCounter++])), Toast.LENGTH_SHORT).show()

                false
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "MenuSettingFragment"
    }
}
