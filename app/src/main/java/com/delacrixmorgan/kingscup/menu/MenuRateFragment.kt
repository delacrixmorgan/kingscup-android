package com.delacrixmorgan.kingscup.menu

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import kotlinx.android.synthetic.main.fragment_menu_rate.*

/**
 * Created by Delacrix Morgan on 11/11/2017.
 */

class MenuRateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_menu_rate, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        this.backButton.setOnClickListener {
            fragmentManager.popBackStack()
        }
    }
}