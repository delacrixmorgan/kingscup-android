package com.delacrixmorgan.kingscup.menu

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.FragmentListener
import com.delacrixmorgan.kingscup.common.launchPlayStore
import kotlinx.android.synthetic.main.fragment_menu_rate.*

/**
 * Created by Delacrix Morgan on 11/11/2017.
 **/

class MenuRateFragment : BaseFragment() {

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.backButton.setOnClickListener {
            this.fragmentListener?.onBackPressed()
        }

        this.starImageView.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.happy)
            this.starImageView.setColorFilter(ContextCompat.getColor(context, R.color.orange))

            this.fragmentListener?.onBackPressed()
            context.launchPlayStore()
        }

        this.rateButton.setOnClickListener {
            this.personImageView.setImageResource(R.drawable.happy)
            this.fragmentListener?.onBackPressed()
            context.launchPlayStore()
        }
    }
}