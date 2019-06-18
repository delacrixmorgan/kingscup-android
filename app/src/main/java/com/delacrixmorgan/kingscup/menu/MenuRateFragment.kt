package com.delacrixmorgan.kingscup.menu

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.model.SoundType
import kotlinx.android.synthetic.main.fragment_menu_rate.*

/**
 * MenuRateFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class MenuRateFragment : Fragment() {

    companion object {
        const val SQUARK_PACKAGE_NAME = "com.delacrixmorgan.squark"
        const val MAMIKA_PACKAGE_NAME = "com.delacrixmorgan.mamika"
    }

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val packageName = context.packageName

        this.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        this.starImageView.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)
            this.starImageView.setColorFilter(ContextCompat.getColor(context, R.color.orange))

            launchPlayStore(packageName)
            Navigation.findNavController(view).navigateUp()
        }

        this.rateButton.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)

            launchPlayStore(packageName)
            Navigation.findNavController(view).navigateUp()
        }

        this.squarkViewGroup.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)

            launchPlayStore(SQUARK_PACKAGE_NAME)
            Navigation.findNavController(view).navigateUp()
        }

        this.mamikaViewGroup.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.ic_human_happy)

            launchPlayStore(MAMIKA_PACKAGE_NAME)
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun launchPlayStore(packageName: String) {
        val url = "https://play.google.com/store/apps/details?id=$packageName"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        intent.flags = FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        this.soundEngine.playSound(requireContext(), SoundType.OOOH)
    }
}