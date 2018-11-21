package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.FragmentListener
import com.delacrixmorgan.kingscup.common.launchPlayStore
import kotlinx.android.synthetic.main.fragment_menu_rate.*

/**
 * MenuRateFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuRateFragment : Fragment() {

    companion object {
        fun newInstance(fragmentListener: FragmentListener? = null): MenuRateFragment {
            val fragment = MenuRateFragment()
            fragment.fragmentListener = fragmentListener

            return fragment
        }
    }

    var fragmentListener: FragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = this.context ?: return

        backButton.setOnClickListener {
            this.fragmentListener?.onBackPressed()
        }

        starImageView.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)
            this.starImageView.setColorFilter(ContextCompat.getColor(context, R.color.orange))

            this.fragmentListener?.onBackPressed()
            context.launchPlayStore()
        }

        rateButton.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)

            this.fragmentListener?.onBackPressed()
            context.launchPlayStore()
        }
    }
}