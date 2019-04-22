package com.cnting.ui2_1.practice

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * Created by cnting on 2019-04-22
 * 自定义Drawable
 */
class PracticeCustomDrawable : Drawable() {

    val paint = Paint()
    val padding = 50

    override fun draw(canvas: Canvas) {
        var x = bounds.left
        var y = bounds.top

        while (x < bounds.right && y < bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(), y.toFloat(), bounds.right.toFloat(), y.toFloat(), paint)
            canvas.drawLine(x.toFloat(), bounds.top.toFloat(), x.toFloat(), bounds.bottom.toFloat(), paint)
            x += padding
            y += padding
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return super.getAlpha()
    }

    override fun getOpacity(): Int {
        return when (alpha) {
            255 -> PixelFormat.OPAQUE
            0 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

}
 