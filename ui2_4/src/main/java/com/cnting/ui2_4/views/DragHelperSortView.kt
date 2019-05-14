package com.cnting.ui2_4.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

/**
 * Created by cnting on 2019-05-07
 * 使用ViewDragHelper拖动，使用这种方式实现排序有点麻烦，没有深入
 */
class DragHelperSortView : ViewGroup {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private val columns = 2
    private val rows = 3
    private var viewDragHelper: ViewDragHelper? = null
    private var childWidth = 0
    private var childHeight = 0
    private val orderedChildren = mutableListOf<View>()

    fun init() {
        viewDragHelper = ViewDragHelper.create(this, dragCallback)
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)  //设置可以左边缘拖拽
        isChildrenDrawingOrderEnabled = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        childWidth = width / columns
        childHeight = height / rows
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (0 until childCount)
            .forEach {
                val child = getChildAt(it)
                val cl = (it % 2) * child.measuredWidth
                val cr = cl + child.measuredWidth
                val ct = (it / 2) * child.measuredHeight
                val cb = ct + child.measuredHeight
                child.layout(cl, ct, cr, cb)
            }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        (0 until childCount)
            .forEach { orderedChildren.add(getChildAt(it)) }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper!!.processTouchEvent(event)
        return true
    }

    var originLeft = -1
    var originTop = -1
    private val dragCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        //水平方向拖动距离
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        //垂直方向拖动距离
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        //被释放时
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)

            viewDragHelper?.settleCapturedViewAt(originLeft, originTop)   //让它回到原来的位置
            invalidate()
        }

        //当view被拖拽并且位置改变时调用，调用好多次
//        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
//            super.onViewPositionChanged(changedView, left, top, dx, dy)

//            sortChild2(changedView, left, top)
//        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            originLeft = capturedChild.left
            originTop = capturedChild.top
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper?.continueSettling(true) == true) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}