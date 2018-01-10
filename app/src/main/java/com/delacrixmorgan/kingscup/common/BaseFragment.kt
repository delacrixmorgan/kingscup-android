package com.delacrixmorgan.kingscup.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by Delacrix Morgan on 10/01/2018.
 **/

open class BaseFragment : Fragment() {
    lateinit var baseActivity: AppCompatActivity
    lateinit var baseContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let { this.baseActivity = it as AppCompatActivity }
        context?.let { this.baseContext = it }
    }
}