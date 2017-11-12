package com.delacrixmorgan.kingscup.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class GameActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val fragment = GuideListFragment()
        val fragment = GameBoardFragment()

        fragmentManager.inTransaction {
            add(mainContainer.id, fragment, fragment.javaClass.simpleName)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle(R.string.quit_title)
                .setMessage(R.string.quit_header)
                .setPositiveButton(R.string.quit_yes) { dialog, _ ->
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.quit_no) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }
}
