package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.*
import kotlinx.android.synthetic.main.fragment_game_load.*

/**
 * Created by Delacrix Morgan on 25/12/2017.
 **/

class GameLoadFragment : Fragment() {

    companion object {
        private const val GAME_LOAD_TYPE = "Type"

        fun newInstance(loadType: LoadType): GameLoadFragment {
            val fragment = GameLoadFragment()
            val args = Bundle()

            args.putSerializable(GAME_LOAD_TYPE, loadType)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var loadType: LoadType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadType = arguments.getSerializable(GAME_LOAD_TYPE) as LoadType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game_load, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.loadingTextView.text = this.loadType.localisedDisplayStatusText(context)

        GameEngine.newInstance(activity)
        SoundEngine.getInstance().playSound(context, SoundType.KING)

        Handler().postDelayed({
            run {
                activity.supportFragmentManager.popBackStack()
                showFragmentSliding(activity = activity, fragment = GameBoardFragment.newInstance(), gravity = Gravity.TOP)
            }
        }, 2000)
    }
}
