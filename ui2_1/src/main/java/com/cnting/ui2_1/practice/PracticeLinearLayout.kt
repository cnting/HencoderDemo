package com.cnting.ui2_1.practice

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

/**
 * Created by cnting on 2019-04-15
 * 实现LinearLayout效果
 */
class PracticeLinearLayout : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //先获取自己的宽高限制
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var useHeight = 0
        var useWidth = 0

        (0 until childCount).forEach {
            val child = getChildAt(it)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            useHeight += (child.measuredHeight + child.marginTop + child.marginBottom)
            useWidth = Math.max(useWidth, child.measuredWidth + child.marginLeft + child.marginRight)
            Log.d("measureChild", "====>$it,height:${child.measuredHeight},useHeight:$useHeight")
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = useWidth
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = useHeight
        }
        Log.i("measure", "===>widthSize:$widthSize,heightSize:$heightSize")

        setMeasuredDimension(widthSize, heightSize)
    }

    private fun measureChild2(
        child: View, parentWidthMeasureSpec: Int,
        parentHeightMeasureSpec: Int
    ) {
        val layoutParams = child.layoutParams
        val childWidthSpec = measureSize(layoutParams.width, parentWidthMeasureSpec)
        val childHeightSpec = measureSize(layoutParams.height, parentHeightMeasureSpec)
        child.measure(childWidthSpec, childHeightSpec)
    }


    private fun measureSize(childSize: Int, parentSpec: Int): Int {
        val parentSize = MeasureSpec.getSize(parentSpec)
        val parentMode = MeasureSpec.getMode(parentSpec)

        return when (childSize) {
            LayoutParams.MATCH_PARENT -> {
                if (parentMode == MeasureSpec.EXACTLY || parentMode == MeasureSpec.AT_MOST) {  //父控件有上限
                    MeasureSpec.makeMeasureSpec(parentSize, MeasureSpec.EXACTLY)
                } else {
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)   //没法得到确定尺寸，继续把UNSPECIFIED传下去
                }
            }
            LayoutParams.WRAP_CONTENT -> {
                if (parentMode == MeasureSpec.EXACTLY || parentMode == MeasureSpec.AT_MOST) {
                    MeasureSpec.makeMeasureSpec(parentSize, MeasureSpec.AT_MOST)
                } else {
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                }
            }
            else -> {
                MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var useHeight = t
        (0 until childCount).onEach {
            val child = getChildAt(it)
            child.layout(
                l + child.marginLeft,
                useHeight + child.marginTop,
                l + child.marginLeft + child.measuredWidth,
                useHeight + child.marginTop + child.measuredHeight
            )
            useHeight += (child.measuredHeight + child.marginTop)
            Log.i("useHeight", "===>useHeight:$useHeight,child.marginTop:${child.marginTop}")
        }

    }

    //如果不重写这个方法，获取子view的margin返回值都是0
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}