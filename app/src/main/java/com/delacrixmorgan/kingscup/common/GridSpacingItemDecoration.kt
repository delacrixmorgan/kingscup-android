package com.delacrixmorgan.kingscup.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val columnCount: Int,
    private val spacing: Int,
    private val shouldShowHorizontalMargin: Boolean = false,
    private val shouldShowVerticalMargin: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = recyclerView.getChildAdapterPosition(view)
        val column = position % columnCount

        // spacing - column * ((1f / columnCount) * spacing)
        outRect.left = spacing - column * spacing / columnCount

        // (column + 1) * ((1f / columnCount) * spacing)
        outRect.right = (column + 1) * spacing / columnCount

        // add spacing to every item's bottom
        outRect.bottom = spacing

        if (position < columnCount) { // top row
            outRect.top = spacing
        }

        if (!shouldShowHorizontalMargin) {
            val isFirstColumn = column == 0
            val isLastColumn = column == columnCount - 1

            if (isFirstColumn) {
                outRect.left = 0
            } else if (isLastColumn) {
                outRect.right = 0
            }
        }

        val itemSize = recyclerView.adapter?.itemCount ?: 1
        val numberOfRows = itemSize / columnCount

        if (!shouldShowVerticalMargin) {
            val isFirstRow = position < columnCount
            val isLastRow = position > (numberOfRows - 1)

            if (isFirstRow) {
                outRect.top = 0
            } else if (isLastRow) {
                outRect.bottom = 0
            }
        }
    }
}