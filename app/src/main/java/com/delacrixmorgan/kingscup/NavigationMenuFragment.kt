package com.delacrixmorgan.kingscup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.delacrixmorgan.kingscup.model.GameType
import kotlinx.android.synthetic.main.fragment_navigation_menu.*

class NavigationMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateButton.setOnClickListener {
            val action = NavigationMenuFragmentDirections.actionMenuFragmentToMenuRateFragment()
            Navigation.findNavController(view).navigate(action)
        }

        settingButton.setOnClickListener {
            val action = NavigationMenuFragmentDirections.actionMenuFragmentToMenuSettingFragment()
            Navigation.findNavController(view).navigate(action)
        }

        startButton.setOnClickListener {
            val action =
                NavigationMenuFragmentDirections.actionMenuFragmentToGameLoadFragment(GameType.NewGame)
            Navigation.findNavController(view).navigate(action)
        }
    }
}