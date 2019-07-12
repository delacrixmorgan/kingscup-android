package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.BuildConfig
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.AdController
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.SoundEngine
import com.delacrixmorgan.kingscup.model.SoundType
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_game_load.*

/**
 * GameLoadFragment
 * kingscup-android
 *
 * Created by Delacrix Morgan on 08/05/2019.
 * Copyright (c) 2019 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

class GameLoadFragment : Fragment() {

    private val gameEngine by lazy {
        GameEngine.getInstance(requireContext())
    }

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        this.arguments?.let {
            val gameType = GameLoadFragmentArgs.fromBundle(it).gameType
            this.loadingTextView.text = gameType.getLocalisedText(context)
        }

        this.gameEngine.setupGame(context)
        this.soundEngine.playSound(context, SoundType.KING)

        if (BuildConfig.DEBUG == true) {
            launchGameFragment()
        } else {
            val interstitialAd = AdController.getInstance(context).interstitialAd
            val adRequest = AdController.getInstance(context).adRequest

            interstitialAd.loadAd(adRequest)
            interstitialAd.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    if (interstitialAd.isLoaded) {
                        interstitialAd.show()
                    }
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    launchGameFragment()
                }

                override fun onAdClicked() {
                    launchGameFragment()
                }

                override fun onAdLeftApplication() {
                    launchGameFragment()
                }

                override fun onAdClosed() {
                    launchGameFragment()
                }
            }
        }
    }

    private fun launchGameFragment() {
        val action = GameLoadFragmentDirections.actionGameLoadFragmentToGameBoardFragment()
        Navigation.findNavController(this.rootView).navigate(action)
    }
}