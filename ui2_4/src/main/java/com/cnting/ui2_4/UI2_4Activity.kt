package com.cnting.ui2_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ui2_4.*

/**
 * Created by cnting on 2019-04-16
 *
 */
class UI2_4Activity : AppCompatActivity() {

    val titles = arrayOf("ViewDragHelper拖动排序", "OnDragListener拖动排序", "拖动卡片", "抽屉", "拖到下面区域", "拖动回到原位")
    val layouts =
        intArrayOf(
            R.layout.drag_helper_sort,
            R.layout.drag_listener_sort,
            R.layout.drag_card,
            R.layout.drawer_view,
            R.layout.drag_and_drop,
            R.layout.drag_and_back
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui2_4)
        viewPager.adapter = MyAdapter(layouts)
        viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = titles[position] }.attach()
    }
}