package com.delacrixmorgan.kingscup.game

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Button
import android.widget.ImageView
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseFragment
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.setupProgressBar
import kotlinx.android.synthetic.main.fragment_game_board.*

/**
 * Created by Delacrix Morgan on 04/03/2017.
 */

class GameBoardFragment : BaseFragment(), GameCardSelectionListener {
    companion object {
        lateinit var FRAGMENT_TAG: String
        fun newInstance(): GameBoardFragment {
            val fragment = GameBoardFragment()
            this.FRAGMENT_TAG = fragment.javaClass.simpleName

            return fragment
        }
    }

    private lateinit var cardAdapter: GameCardAdapter
    private var isCardSelected: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        this.cardAdapter = GameCardAdapter(this)
        this.isCardSelected = false

        this.recyclerView.layoutManager = manager
        this.recyclerView.adapter = this.cardAdapter

        this.statusTextView.text = activity.resources.getStringArray(R.array.taunt).first()
        this.volumeImageView.setImageResource(R.drawable.cup_whole)

        this.quitButton.setOnClickListener {
            this@GameBoardFragment.showMenuDialog()
        }

        setupProgressBar(manager, recyclerView, progressBar)
    }

    override fun onCardSelected(position: Int) {
        if (!this.isCardSelected) {
            val card = GameEngine.getInstance().deckList[position]
            val fragment = GameCardFragment.newInstance(card, position)

            this.isCardSelected = true

            showFragmentWithSlide(activity, fragment, Gravity.BOTTOM)
            GameEngine.getInstance().vibrateFeedback()
        }
    }

    private fun showMenuDialog() {
        val menuDialog = Dialog(activity)

        menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        menuDialog.setContentView(R.layout.dialog_credit)

        menuDialog.show()

        val spartanImageView = menuDialog.findViewById<ImageView>(R.id.spartanImageView)
        val kornerImageView = menuDialog.findViewById<ImageView>(R.id.kornerImageView)
        val doneButton = menuDialog.findViewById<Button>(R.id.doneButton)

        spartanImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/theleagueof/league-spartan")
            startActivity(intent)
        }

        kornerImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/JcMinarro/RoundKornerLayouts")
            startActivity(intent)
        }

        doneButton.setOnClickListener {
            menuDialog.dismiss()
            this.activity.fragmentManager.popBackStack()
        }
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
