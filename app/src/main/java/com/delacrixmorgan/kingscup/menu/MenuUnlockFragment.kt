package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_menu_unlock.*

/**
 * Created by Delacrix Morgan on 04/12/2017.
 **/

class MenuUnlockFragment : BaseFragment() {
    companion object {
        fun newInstance(): MenuUnlockFragment {
            return MenuUnlockFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_unlock, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.backButton.setOnClickListener {
            this.fragmentManager.popBackStack()
        }
    }
}