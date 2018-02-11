package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.game.GameLoadFragment
import com.delacrixmorgan.kingscup.game.LoadType
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 **/

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

        context?.let { context ->
            rateButton.setOnClickListener {
                showFragmentSliding(context, MenuRateFragment.newInstance(this), Gravity.START)
            }

            settingButton.setOnClickListener {
                showFragmentSliding(context, MenuSettingFragment.newInstance(this), Gravity.END)
            }

            startButton.setOnClickListener {
                if (!isGameStarted) {
                    isGameStarted = !isGameStarted
                    showFragmentSliding(context, GameLoadFragment.newInstance(LoadType.NEW_GAME), Gravity.BOTTOM)
                    Handler().postDelayed({
                        run {
                            isGameStarted = false
                        }
                    }, 2000)
                }
            }
        }
    }

    override fun onBackPressed() {
        appNameTextView.text = getString(R.string.app_name)
        activity?.title = getString(R.string.app_name)
        activity?.supportFragmentManager?.popBackStack()
    }
}