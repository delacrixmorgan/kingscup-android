package com.delacrixmorgan.kingscup.game

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.launchPlayStore
import com.delacrixmorgan.kingscup.common.setupProgressBar
import kotlinx.android.synthetic.main.dialog_pause.*
import kotlinx.android.synthetic.main.fragment_game_board.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 **/

class GameBoardFragment : BaseFragment(), View.OnClickListener, CardListener {
    companion object {
        lateinit var FRAGMENT_TAG: String

        fun newInstance(): GameBoardFragment {
            val fragment = GameBoardFragment()
            this.FRAGMENT_TAG = fragment.javaClass.simpleName

            return fragment
        }
    }

    private lateinit var cardAdapter: GameCardAdapter
    private lateinit var menuDialog: Dialog
    private var isCardSelected: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupView()

        this.restartButton.setOnClickListener {
            this.startNewGame()
        }

        this.menuButton.setOnClickListener {
            this.menuDialog.show()
        }
    }

    private fun setupView() {
        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        this.cardAdapter = GameCardAdapter(this)
        this.isCardSelected = false

        this.recyclerView.removeAllViews()
        this.recyclerView.layoutManager = manager
        this.recyclerView.adapter = this.cardAdapter

        this.statusTextView.text = activity.resources.getStringArray(R.array.taunt).first()
        this.volumeImageView.setImageResource(R.drawable.cup_whole)
        
        this.setupMenuDialog()
        setupProgressBar(manager, recyclerView, progressBar)
    }

    override fun onCardSelected(position: Int) {
        if (!this.isCardSelected) {
            val card = GameEngine.getInstance().deckList[position]
            val fragment = GameCardFragment.newInstance(card, position)

            this.isCardSelected = true

            showFragmentSliding(activity, fragment, Gravity.BOTTOM)
            GameEngine.getInstance().vibrateFeedback()
        }
    }

    private fun setupMenuDialog() {
        this.menuDialog = Dialog(activity)
        this.menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.menuDialog.setContentView(R.layout.dialog_pause)

        this.menuDialog.quitDialogButton.setOnClickListener(this)
        this.menuDialog.rateDialogButton.setOnClickListener(this)
        this.menuDialog.volumeDialogButton.setOnClickListener(this)
        this.menuDialog.resumeDialogButton.setOnClickListener(this)
        this.menuDialog.restartDialogButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        menuDialog.dismiss()

        when (view.id) {
            R.id.restartDialogButton -> {
                this.startNewGame()
            }

            R.id.rateDialogButton -> {
                context.launchPlayStore()
            }

            R.id.volumeDialogButton -> {

            }

            R.id.resumeDialogButton -> {
            }

            R.id.quitDialogButton -> {
                this.activity.fragmentManager.popBackStack()
            }
        }
    }

    private fun startNewGame() {
        this.activity.fragmentManager.popBackStack()
        showFragmentSliding(activity, GameLoadFragment.newInstance(LoadType.RESTART_GAME), Gravity.BOTTOM)
    }

    fun removeCardFromDeck(position: Int) {
        GameEngine.getInstance().removeCard(position, cardAdapter, progressBar)

        val args: Bundle? = GameEngine.getInstance().updateGraphicStatus(activity)
        this.isCardSelected = false

        statusTextView.text = args?.getString(GameEngine.GAME_ENGINE_TAUNT)
        args?.getInt(GameEngine.GAME_ENGINE_CUP_VOLUME)?.let { volumeImageView.setImageResource(it) }

        when (args?.getInt(GameEngine.GAME_ENGINE_KING_COUNTER)) {
            3 -> this.kingFourImageView.visibility = View.GONE
            2 -> this.kingThreeImageView.visibility = View.GONE
            1 -> this.kingTwoImageView.visibility = View.GONE
            0 -> {
                this.kingOneImageView.visibility = View.GONE
                this.restartButton.visibility = View.VISIBLE
            }
        }
    }
}
