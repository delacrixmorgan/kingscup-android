package com.delacrixmorgan.kingscup

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.delacrixmorgan.kingscup.engine.GameEngine
import com.delacrixmorgan.kingscup.fragment.GuideFragment

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class GameActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, GuideFragment())
                .commit()
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}
