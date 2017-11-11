package com.delacrixmorgan.kingscup.game

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.delacrixmorgan.kingscup.R
import com.delacrixmorgan.kingscup.common.BaseActivity
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.guide.TutorialFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class GameActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = TutorialFragment()

        fragmentManager.inTransaction {
            replace(mainContainer.id, fragment)
        }
    }

    override fun onBackPressed() {
        if (GameEngine.getInstance().getmKingCounter() < 1) {
            finish()
        } else {
            AlertDialog.Builder(this)
                    .setTitle(R.string.quit_title)
                    .setMessage(R.string.quit_header)
                    .setPositiveButton(R.string.quit_yes) { dialog, which ->
                        GameEngine.getInstance().playSound(applicationContext, "CARD_WHOOSH")
                        finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.quit_no) { dialog, which ->
                        GameEngine.getInstance().playSound(applicationContext, "CARD_WHOOSH")
                        dialog.dismiss()
                    }
                    .show()
        }
    }
}
