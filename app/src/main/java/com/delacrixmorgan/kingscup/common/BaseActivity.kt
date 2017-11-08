package com.delacrixmorgan.kingscup.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.FragmentManager
import android.app.FragmentTransaction

/**
 * Created by Delacrix Morgan on 08/11/2017.
 */

@SuppressLint("Registered")
open class BaseActivity : Activity() {
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}