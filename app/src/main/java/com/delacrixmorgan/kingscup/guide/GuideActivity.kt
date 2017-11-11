package com.delacrixmorgan.kingscup.guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.delacrixmorgan.kingscup.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Delacrix Morgan on 11/11/2017.
 */
class GuideActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GuideActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = GuideListFragment()

        fragmentManager.inTransaction {
            replace(mainContainer.id, fragment)
        }
    }
}