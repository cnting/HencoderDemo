package com.cnting.ui2_1.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginRight
import androidx.core.view.setMargins
import com.cnting.ui2_1.Utils

/**
 * Created by cnting on 2019-04-23
 *
 */
class ColorTextView : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val colors = arrayOf(Color.RED, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA)
    private val provinces = arrayOf(
        "北京市",
        "天津市",
        "上海市",
        "重庆市",
        "河北省",
        "山西省",
        "辽宁省",
        "吉林省",
        "黑龙江省",
        "江苏省",
        "浙江省",
        "安徽省",
        "福建省",
        "江西省",
        "山东省",
        "河南省",
        "湖北省",
        "湖南省",
        "广东省",
        "海南省",
        "四川省",
        "贵州省",
        "云南省",
        "陕西省",
        "甘肃省",
        "青海省",
        "台湾省",
        "内蒙古自治区",
        "广西壮族自治区",
        "西藏自治区",
        "宁夏回族自治区",
        "新疆维吾尔自治区",
        "香港特别行政区",
        "澳门特别行政区"
    )

    init {
        setBackgroundColor(colors.random())
        text = provinces.random()
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
    }
}