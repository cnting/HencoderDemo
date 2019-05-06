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
class MultiTouchView2 : View {

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
    var originOffsetX = 0f
    var originOffsetY = 0f
    var downX = 0f
    var downY = 0f

    fun init() {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap!!, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {  //注意，多点触控这里要用actionMasked
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                var sumX = 0f
                var sumY = 0f
                (0 until event.pointerCount)
                    .forEach {
                        sumX += event.getX(it)
                        sumY += event.getY(it)
                    }
                downX = sumX / event.pointerCount
                downY = sumY / event.pointerCount
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                var sumX = 0f
                var sumY = 0f
                (0 until event.pointerCount)
                    .filter { it != event.actionIndex }  //ACTION_POINTER_UP时抬起手指的数据是还在的
                    .forEach {
                        sumX += event.getX(it)
                        sumY += event.getY(it)
                    }
                downX = sumX / (event.pointerCount - 1)
                downY = sumY / (event.pointerCount - 1)
                originOffsetX = offsetX
                originOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                var sumX = 0f
                var sumY = 0f

                (0 until event.pointerCount)
                    .forEach {
                        sumX += event.getX(it)
                        sumY += event.getY(it)
                    }
                sumX -= downX
                sumY -= downY
                sumX += originOffsetX
                sumY += originOffsetY
                offsetX = sumX / event.pointerCount
                offsetY = sumY / event.pointerCount
                invalidate()
            }
        }
        return true
    }


}