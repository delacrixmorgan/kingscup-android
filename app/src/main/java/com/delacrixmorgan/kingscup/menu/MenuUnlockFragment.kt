package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import kotlinx.android.synthetic.main.fragment_menu_unlock.*

/**
 * Created by Delacrix Morgan on 04/12/2017.
 **/

class MenuUnlockFragment : Fragment() {
    companion object {
        fun newInstance(): MenuUnlockFragment {
            return MenuUnlockFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_unlock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}