package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.launchWebsite
import com.delacrixmorgan.kingscup.engine.SoundEngine
import com.delacrixmorgan.kingscup.model.SoundType
import kotlinx.android.synthetic.main.fragment_menu_credits.*

class MenuCreditsFragment : Fragment() {

    private val soundEngine by lazy {
        SoundEngine.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu_credits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spartanImageView.setOnClickListener { launchWebsite("https://github.com/theleagueof/league-spartan") }
        kornerImageView.setOnClickListener { launchWebsite("https://github.com/JcMinarro/RoundKornerLayouts") }

        freesoundImageView.setOnClickListener { launchWebsite("https://freesound.org/") }
        freepikImageView.setOnClickListener { launchWebsite("https://freepik.com") }
        confettiImageView.setOnClickListener { launchWebsite("https://lottiefiles.com/4329-confetti") }

        doneButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
            soundEngine.playSound(view.context, SoundType.Whoosh)
        }
    }
}