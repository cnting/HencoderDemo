package com.cnting.ui2_1.practice

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller
import androidx.core.view.ViewConfigurationCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight

/**
 * Created by cnting on 2019-04-23
 *
 */
class PracticeTagLayout : ViewGroup {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private val childrenBounds = mutableListOf<Rect>()
    private var scroller: Scroller? = null
    private var minTouchSlop = 0
    private var topBorder = 0
    private var bottomBorder = 0

    private fun init() {
        scroller = Scroller(context)
        minTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineMaxHeight = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        (0 until childCount).forEach {
            val child = getChildAt(it)
            //测量子view
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            if (lineWidthUsed + (child.measuredWidth + child.marginLeft + child.marginRight) > widthSize) {  //剩余空间放不下，换行
                widthUsed = Math.max(widthUsed, lineWidthUsed)
                heightUsed += lineMaxHeight
                lineWidthUsed = 0
                lineMaxHeight = 0
            }

            val bound = Rect()
            bound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight)
            boundAddMargin(bound, child.layoutParams as MarginLayoutParams)
            childrenBounds.add(it, bound)

            lineWidthUsed += (bound.right - bound.left)
            lineMaxHeight = Math.max(lineMaxHeight, (bound.bottom - bound.top))
        }

        val width = View.resolveSize(widthUsed, widthMeasureSpec)
        topBorder = 0
        bottomBorder = heightUsed
        val height = View.resolveSize(heightUsed, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun boundAddMargin(bound: Rect, layoutParams: MarginLayoutParams) {
        bound.right += (layoutParams.leftMargin + layoutParams.rightMargin)
        bound.bottom += (layoutParams.topMargin + layoutParams.bottomMargin)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (0 until childCount).forEach {
            val child = getChildAt(it)
            val layoutParams = child.layoutParams as MarginLayoutParams
            val bound = childrenBounds[it]
            child.layout(
                bound.left + layoutParams.leftMargin,
                bound.top + layoutParams.topMargin,
                bound.right - layoutParams.rightMargin,
                bound.bottom - layoutParams.bottomMargin
            )
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            touchY = ev.y
        } else if (ev.action == MotionEvent.ACTION_MOVE) {
            if (Math.abs(ev.y - touchY) > minTouchSlop) {
                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private var touchY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchY = event.y
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val needScrollY = touchY - event.y
            when {
                scrollY + needScrollY < topBorder -> {
                    scrollTo(0, 0)
                }
                scrollY + needScrollY + height > bottomBorder -> {
                    scrollTo(0, bottomBorder - height)
                }
                else -> {
                    scrollBy(0, needScrollY.toInt())
                    touchY = event.y
                }
            }
        }
        return true
    }

    override fun computeScroll() {
        if (scroller?.computeScrollOffset() == true) {  //返回true表明滚动还未结束，同时在函数里获取currX和currY的值
            scrollTo(scroller!!.currX, scroller!!.currY) //滚动到当前位置
            postInvalidate()  //强制重新绘制View,在框架里,View 的绘制会重新调用到computeScroll方法，达到循环绘制的目的
        }
    }
}