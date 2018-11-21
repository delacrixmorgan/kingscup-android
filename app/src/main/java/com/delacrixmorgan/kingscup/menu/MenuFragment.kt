package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.game.GameLoadFragment
import com.delacrixmorgan.kingscup.model.LoadType
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * MenuFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuFragment : Fragment(), FragmentListener {

    companion object {
        fun newInstance(): MenuFragment = MenuFragment()
    }

    private var isGameStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = PreferenceHelper.getPreference(context!!)
        val selectedLanguage = LanguageType.values().asList().first {
            it.countryIso == preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT]
        }

        setLocale(selectedLanguage.countryIso, resources)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = this.context ?: return

        this.rateButton.setOnClickListener {
            context.showFragmentSliding(MenuRateFragment.newInstance(this), Gravity.START)
        }

        this.settingButton.setOnClickListener {
            context.showFragmentSliding(MenuSettingFragment.newInstance(this), Gravity.END)
        }

        this.startButton.setOnClickListener {
            if (!this.isGameStarted) {
                this.isGameStarted = !this.isGameStarted
                context.showFragmentSliding(GameLoadFragment.newInstance(LoadType.NEW_GAME), Gravity.BOTTOM)

                Handler().postDelayed({
                    run {
                        isGameStarted = false
                    }
                }, 2000)
            }
        }
    }

    override fun onBackPressed() {
        this.activity?.supportFragmentManager?.popBackStack()
        getString(R.string.app_name).let {
            this.appNameTextView.text = it
            this.activity?.title = it
        }
    }
}