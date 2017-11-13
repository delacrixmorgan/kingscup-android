package com.delacrixmorgan.kingscup.common

import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar

/**
 * Created by Delacrix Morgan on 13/11/2017.
 */

fun setupProgressBar(manager: LinearLayoutManager, recyclerView: RecyclerView, progressBar: ProgressBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (manager.findFirstVisibleItemPosition() == 0) {
                progressBar.progress = 0
            } else {
                progressBar.progress = manager.findLastVisibleItemPosition()
            }
        }
    } else {
        progressBar.visibility = View.GONE
    }
}