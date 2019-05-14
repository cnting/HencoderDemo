package com.cnting.ui2_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by cnting on 2019-04-16
 *
 */
class MyAdapter(private val layouts: IntArray) : RecyclerView.Adapter<MyAdapter.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        view.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return layouts.size
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return layouts[position]
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}