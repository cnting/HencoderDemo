package com.cnting.ui2_3

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.ClipData
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat

/**
 * Created by cnting on 2019-05-06
 *
 */
class CustomViewPager : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth * childCount, measuredHeight)
    }

    private val screenWidth by lazy {
        Resources.getSystem().displayMetrics.widthPixels
    }
    private val flingVelocity by lazy {
        ViewConfiguration.get(context).scaledMinimumFlingVelocity
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (0 until childCount)
            .forEach {
                val cl = l + it * screenWidth
                val cr = cl + screenWidth
                getChildAt(it).layout(cl, t, cr, b)
            }
    }

    var offsetX = 0f
    var currentItem = 0
    var velocityTracker: VelocityTracker? = null
    var scroller: OverScroller? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        if (scroller == null) {
            scroller = OverScroller(context)
        }
        velocityTracker!!.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                offsetX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                var sx = scrollX + (offsetX - event.x).toInt()
                sx = Math.max(0, sx)
                sx = Math.min(width - screenWidth, sx)
                scrollTo(sx, 0)
                offsetX = event.x
            }
            MotionEvent.ACTION_UP -> {
                if (scrollX >= (currentItem * screenWidth + screenWidth / 3)) {
                    currentItem += 1
                } else if (scrollX < (currentItem * screenWidth - screenWidth / 3)) {
                    currentItem -= 1
                } else {
                    velocityTracker?.computeCurrentVelocity(1000)
                    if (Math.abs(velocityTracker?.xVelocity ?: 0f) >= flingVelocity) {  //超过滑动速度
                        if (velocityTracker?.xVelocity ?: 0f < 0) {
                            currentItem += 1
                        } else {
                            currentItem -= 1
                        }
                    }
                }
                currentItem = Math.max(0, currentItem)
                currentItem = Math.min(childCount - 1, currentItem)
                smoothScroll(currentItem * screenWidth)
            }
        }
        return true
    }

    private fun smoothScroll(scrollTo: Int) {
        scroller?.startScroll(scrollX, scrollY, scrollTo - scrollX, 0, 150)
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller?.computeScrollOffset() == true) {
            scrollTo(scroller!!.currX, scroller!!.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        velocityTracker?.clear()
        velocityTracker?.recycle()
    }

}