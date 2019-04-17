package com.cnting.ui2_2

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cnting.ui2_2.views.FlipboardView
import com.cnting.ui2_2.views.LikeView
import com.cnting.ui2_2.views.RulerView
import com.cnting.ui2_2.views.SportView

/**
 * Created by cnting on 2019-04-16
 *
 */
class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    val array = intArrayOf(1, 2, 3, 4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        val view: View = when (viewType) {
            1 -> LikeView(parent.context)
            2 -> RulerView(parent.context)
            3 -> SportView(parent.context)
            4 -> FlipboardView(parent.context)
            else -> View(parent.context)
        }
        view.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return array.get(position)
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}