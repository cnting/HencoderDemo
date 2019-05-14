package com.cnting.ui2_4.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import kotlinx.android.synthetic.main.drawer_view.view.*

/**
 * Created by cnting on 2019-05-13
 *从边缘拖拽出view
 */
class DrawerLayout : ViewGroup {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private var navigateView: View? = null
    private var viewDragHelper: ViewDragHelper? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        addView(navigateView)
        clickBtn.setOnClickListener {
            viewDragHelper?.smoothSlideViewTo(getChildAt(0), 600, 600)  //注意这里需要调用invalidate
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var useHeight = 0
        (0 until childCount - 1)
            .forEach {
                val child = getChildAt(it)
                child.layout(0, useHeight, child.measuredWidth, useHeight + child.measuredHeight)
                useHeight += child.measuredHeight
            }
        navigateView?.layout(-navigateView?.measuredWidth!!, 0, 0, navigateView!!.measuredHeight)
    }

    fun init() {
        viewDragHelper = ViewDragHelper.create(this, dragCallback)
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)  //设置可以左边缘拖拽
        isChildrenDrawingOrderEnabled = true
        navigateView = View(context)
        navigateView?.setBackgroundColor(Color.RED)
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        navigateView?.layoutParams = LayoutParams((screenWidth * 0.6).toInt(), LayoutParams.MATCH_PARENT)
        navigateView?.contentDescription = "抽屉"
    }

    private val dragCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return if (child == navigateView) {
                Math.min(0, left)
            } else {
                left
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return if (child == navigateView) {
                0
            } else {
                return top
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            if (releasedChild == navigateView) {
                if (Math.abs(navigateView!!.left) > navigateView!!.width / 2) {
                    viewDragHelper!!.settleCapturedViewAt(-navigateView!!.width, 0)
                } else {
                    viewDragHelper!!.settleCapturedViewAt(0, 0)
                }
                invalidate()
            } else if (releasedChild == getChildAt(0)) {
                viewDragHelper?.flingCapturedView(  //释放后再滑动一段距离，注意需要调用invalidate
                    0,
                    0,
                    width - releasedChild.width,
                    height - releasedChild.height
                )
                invalidate()
            }
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)
            viewDragHelper?.captureChildView(navigateView!!, pointerId)
        }

        override fun onEdgeLock(edgeFlags: Int): Boolean {
            return super.onEdgeLock(edgeFlags)
        }

        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            super.onEdgeTouched(edgeFlags, pointerId)
        }

        override fun getViewHorizontalDragRange(child: View): Int {  //针对button这种可点击的view，设置
            return 1
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return 1
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper?.continueSettling(true) == true) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper!!.processTouchEvent(event)
        return true
    }
}