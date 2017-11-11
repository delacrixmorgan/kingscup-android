package com.delacrixmorgan.kingscup

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.delacrixmorgan.kingscup.common.BaseActivity
import com.delacrixmorgan.kingscup.common.GameEngine
import com.delacrixmorgan.kingscup.common.Helper
import com.delacrixmorgan.kingscup.menu.MenuListFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Delacrix Morgan on 26/03/2017.
 */

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Helper.setLocaleLanguage(this)

        if (savedInstanceState == null) {
            GameEngine.newInstance(this)
        }

        fragmentManager.inTransaction {
            add(mainContainer.id, MenuListFragment())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTaskDescription(ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(resources, R.drawable.kingscup_logo_icon),
                    ContextCompat.getColor(this, R.color.colorPrimary)))
        }
    }
}
