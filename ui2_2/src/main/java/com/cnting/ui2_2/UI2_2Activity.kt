package com.cnting.ui2_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ui2_2.*

/**
 * Created by cnting on 2019-04-16
 *
 */
class UI2_2Activity : AppCompatActivity() {

    val titles = arrayOf("即刻点赞效果", "薄荷健康滑动卷尺", "小米运动记录", "Flipboard翻页")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui2_2)
        viewPager.adapter = MyAdapter()
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> tab.text = titles[position] }.attach()
    }
}