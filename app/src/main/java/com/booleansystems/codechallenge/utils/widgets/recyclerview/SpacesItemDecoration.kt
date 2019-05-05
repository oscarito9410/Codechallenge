package com.booleansystems.codechallenge.utils.widgets.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by oscar on 04/05/19
 * operez@na-at.com.mx
 */
class SpacesItemDecoration(private val mSpace: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.bottom = mSpace

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace
    }
}