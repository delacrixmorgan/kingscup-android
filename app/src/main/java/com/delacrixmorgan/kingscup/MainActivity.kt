package com.delacrixmorgan.kingscup

import android.os.Bundle
import com.delacrixmorgan.kingscup.common.BaseActivity
import com.delacrixmorgan.kingscup.menu.MenuFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.inTransaction {
            add(mainContainer.id, MenuFragment.newInstance())
        }
    }
}
