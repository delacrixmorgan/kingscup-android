package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * GuideType
 * kingscup-android
 *
 * Created by Delacrix Morgan on 25/03/2018.
 * Copyright (c) 2018 licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */

enum class GuideType {
    PAGE_ONE,
    PAGE_TWO,
    PAGE_THREE;

    fun getLocalisedText(context: Context): String {
        return when (this) {
            PAGE_ONE -> context.getString(R.string.guide_page_one)
            PAGE_TWO -> context.getString(R.string.guide_page_two)
            PAGE_THREE -> context.getString(R.string.guide_page_three)
        }
    }
}