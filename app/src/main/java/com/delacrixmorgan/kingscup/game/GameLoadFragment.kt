package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.GameEngine

/**
 * Created by Delacrix Morgan on 25/12/2017.
 **/

class GameLoadFragment : BaseFragment() {
    companion object {
        fun newInstance(): GameLoadFragment = GameLoadFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game_load, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GameEngine.newInstance(activity)

        Handler().postDelayed({
            run {
                activity.fragmentManager.popBackStack()
                showFragmentSliding(activity = activity, fragment = GameBoardFragment.newInstance(), gravity = Gravity.TOP)
            }
        }, 1000)
    }
}
