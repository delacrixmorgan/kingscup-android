package com.delacrixmorgan.kingscup.model

import android.content.Context
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 08/03/2018.
 **/

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