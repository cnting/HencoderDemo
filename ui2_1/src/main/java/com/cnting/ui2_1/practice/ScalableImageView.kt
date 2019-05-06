package com.cnting.ui2_1.practice

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.cnting.ui2_1.R

/**
 * Created by cnting on 2019-04-25
 *
 */
class ScalableImageView : View {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap: Bitmap? = null
    var originOffsetX = 0f
    var originOffsetY = 0f
    var offsetX = 0f
    var offsetY = 0f
    var smallScale = 1f
    var bigScale = 1f
    val overScale = 2.5f
    var isBig = false
    var gestureDetectorCompat: GestureDetectorCompat? = null
    var scaleGestureDetector: ScaleGestureDetector? = null
    var overScroller: OverScroller? = null
    var scale = 0f
        set(value) {
            field = value
            invalidate()
        }

    private fun init() {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian)
        gestureDetectorCompat = GestureDetectorCompat(context, gestureListener)
        scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)
        overScroller = OverScroller(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originOffsetX = (width - bitmap!!.width) / 2f
        originOffsetY = (height - bitmap!!.height) / 2f
        if ((bitmap!!.width.toFloat() / bitmap!!.height) > (width.toFloat() / height)) {  //图片比较宽
            smallScale = width.toFloat() / bitmap!!.width
            bigScale = height.toFloat() / bitmap!!.height * overScale
        } else {
            smallScale = height.toFloat() / bitmap!!.height
            bigScale = width.toFloat() / bitmap!!.width * overScale
        }
        scale = smallScale
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (scale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap!!, originOffsetX, originOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = scaleGestureDetector!!.onTouchEvent(event)
        if (!scaleGestureDetector!!.isInProgress) {
            result = gestureDetectorCompat!!.onTouchEvent(event)
        }
        return result
    }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "scale", smallScale, bigScale)
    }

    private fun fixOffset() {
        offsetX = Math.min(offsetX, (bitmap!!.width * bigScale - width) / 2)
        offsetX = Math.max(offsetX, -(bitmap!!.width * bigScale - width) / 2)
        offsetY = Math.min(offsetY, (bitmap!!.height * bigScale - height) / 2)
        offsetY = Math.max(offsetY, -(bitmap!!.height * bigScale - height) / 2)
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            overScroller?.fling(
                offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                (-(bitmap!!.width * bigScale - width) / 2).toInt(),
                ((bitmap!!.width * bigScale - width) / 2).toInt(),
                (-(bitmap!!.height * bigScale - height) / 2).toInt(),
                ((bitmap!!.height * bigScale - height) / 2).toInt()
            )
            ViewCompat.postInvalidateOnAnimation(this@ScalableImageView)
            return false
        }

        override fun onScroll(down: MotionEvent?, event: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffset()
            invalidate()
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBig = if (scale < smallScale) {
                false
            } else scale < bigScale

            if (isBig) {
                offsetX = (e.x - width.toFloat() / 2) - (e.x - width.toFloat() / 2) * (bigScale / scale)
                offsetY = (e.y - height.toFloat() / 2) - (e.y - height.toFloat() / 2) * (bigScale / scale)
                fixOffset()
                animator.setFloatValues(scale, bigScale)
                animator.start()
            } else {
                animator.setFloatValues(scale, smallScale)
                animator.start()
            }
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }
    }

    private val scaleGestureListener = object : ScaleGestureDetector.OnScaleGestureListener {
        var initScale = 1f
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            initScale = scale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale = detector.scaleFactor * initScale
            return false
        }

    }

    /**
     * invalidate会调用onDraw(),onDraw()会自动调用这个方法
     */
    override fun computeScroll() {
        super.computeScroll()
        if (overScroller?.computeScrollOffset() == true) {   //滚动还未结束
            offsetX = overScroller!!.currX.toFloat()
            offsetY = overScroller!!.currY.toFloat()
            fixOffset()
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}