package com.cnting.ui2_1.practice

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.cnting.ui2_1.R

/**
 * Created by cnting on 2019-04-26
 *
 */
class MultiTouchView1 : View {

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
    var offsetX = 0f
    var offsetY = 0f
    var downX = 0f
    var downY = 0f
    var originOffsetX = 0f
    var originOffsetY = 0f
    var trackingPointId = 0

    fun init() {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap!!, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {  //注意，多点触控这里要用actionMasked
            MotionEvent.ACTION_DOWN -> {
                trackingPointId = event.getPointerId(event.actionIndex)
                downX = event.x
                downY = event.y
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                val actionIndex = event.findPointerIndex(trackingPointId)
                offsetX = originOffsetX + event.getX(actionIndex) - downX
                offsetY = originOffsetY + event.getY(actionIndex) - downY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {//第二根手指按下时，传递给新的手指
                val newPointId = event.getPointerId(event.actionIndex)
                trackingPointId = newPointId
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {  //多个手指的其中一个抬起时调用。如果抬起的正是当前追踪的手指，这时候需要传递给其他手指，我们传递给最大的actionIndex
                val trackingActionIndex = event.findPointerIndex(trackingPointId)  //正在追踪的手指
                val curActionIndex = event.actionIndex   //抬起的手指
                if (trackingActionIndex == curActionIndex) {  //是当前正在追踪的手指
                    val newActionIndex = if (curActionIndex == event.pointerCount - 1) {  //如果当前是最大的actionIndex，则再取前一个
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    trackingPointId = event.getPointerId(newActionIndex)
                    downX = event.getX(newActionIndex)
                    downY = event.getY(newActionIndex)
                    originOffsetX = offsetX
                    originOffsetY = offsetY
                }
            }
        }
        return true
    }

}