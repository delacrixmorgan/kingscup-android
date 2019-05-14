package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.common.launchWebsite
import com.delacrixmorgan.kingscup.model.SoundType
import kotlinx.android.synthetic.main.fragment_menu_credits.*

/**
 * MenuCreditsFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 14/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuCreditsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_credits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.spartanImageView.setOnClickListener { launchWebsite("https://github.com/theleagueof/league-spartan") }
        this.kornerImageView.setOnClickListener { launchWebsite("https://github.com/JcMinarro/RoundKornerLayouts") }

        this.chineseImageView.setOnClickListener { launchWebsite("https://en.wikipedia.org/wiki/China") }
        this.portugueseImageView.setOnClickListener { launchWebsite("https://en.wikipedia.org/wiki/Brazil") }
        this.netherlandsImageView.setOnClickListener { launchWebsite("https://en.wikipedia.org/wiki/Netherlands") }
        this.spanishImageView.setOnClickListener { launchWebsite("https://en.wikipedia.org/wiki/Spain") }

        this.freesoundImageView.setOnClickListener { launchWebsite("https://freesound.org/") }
        this.freepikImageView.setOnClickListener { launchWebsite("https://freepik.com") }

        this.doneButton?.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
            SoundEngine.getInstance().playSound(view.context, SoundType.WHOOSH)
        }
    }
}