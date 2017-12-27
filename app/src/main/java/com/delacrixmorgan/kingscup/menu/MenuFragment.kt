package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.FragmentListener
import com.delacrixmorgan.kingscup.common.PreferenceHelper
import com.delacrixmorgan.kingscup.common.PreferenceHelper.get
import com.delacrixmorgan.kingscup.common.setLocale
import com.delacrixmorgan.kingscup.game.GameLoadFragment
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * Created by Delacrix Morgan on 09/10/2016.
 */

class MenuFragment : BaseFragment(), FragmentListener {
    companion object {
        fun newInstance(): MenuFragment = MenuFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preference = PreferenceHelper.getPreference(context)
        setLocale(preference[PreferenceHelper.LANGUAGE, PreferenceHelper.LANGUAGE_DEFAULT], resources)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.rateButton.setOnClickListener {
            showFragmentSliding(activity, MenuRateFragment.newInstance(this), Gravity.START)
        }

        this.settingButton.setOnClickListener {
            showFragmentSliding(activity, MenuSettingFragment.newInstance(this), Gravity.END)
        }

        this.startButton.setOnClickListener {
            showFragmentSliding(activity, GameLoadFragment.newInstance(), Gravity.BOTTOM)
        }
    }

    override fun onBackPressed() {
        this.appNameTextView.text = getString(R.string.app_name)
        fragmentManager.popBackStack()
    }
}
