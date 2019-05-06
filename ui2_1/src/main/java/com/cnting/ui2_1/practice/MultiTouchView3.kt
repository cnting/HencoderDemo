package com.cnting.ui2_1.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.MotionEvent
import android.view.View
import androidx.core.util.contains
import androidx.core.util.forEach
import androidx.core.util.keyIterator
import com.cnting.ui2_1.R

/**
 * Created by cnting on 2019-04-26
 *
 */
class MultiTouchView3 : View {

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
    val paths = SparseArray<Path>()   //key为pointId

    fun init() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.color = Color.BLACK
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach { _, path ->
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                paths.clear()
                invalidate()
            }
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val id = event.getPointerId(index)
                val path = paths.get(id, Path())
                path.reset()
                path.moveTo(event.getX(index), event.getY(index))
                paths.append(id, path)
            }
            MotionEvent.ACTION_MOVE -> {  //这里是拿不到actionIndex的
                (0 until event.pointerCount)
                    .forEach {
                        val id = event.getPointerId(it)
                        val path = paths.get(id, Path())
                        path.lineTo(event.getX(it), event.getY(it))
                    }
                invalidate()
            }
        }
        return true
    }


}