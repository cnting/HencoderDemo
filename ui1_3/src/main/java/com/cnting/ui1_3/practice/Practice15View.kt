package com.cnting.ui1_3.practice

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cnting.ui1_3.UiUtil

/**
 * Created by cnting on 2019-04-18
 *
 */
class Practice15View : View {

    private val edgePaint = Paint()
    private val textPaint = Paint()
    private val radius = UiUtil.dp2px(130f)
    private var fontYOffset = 0f
    var degree: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        edgePaint.style = Paint.Style.STROKE
        edgePaint.strokeWidth = UiUtil.dp2px(20f)

        textPaint.textSize = UiUtil.sp2px(80f)
        textPaint.color = Color.BLUE
        textPaint.textAlign = Paint.Align.CENTER
        val fontMetrics = textPaint.fontMetrics
        fontYOffset = (fontMetrics.ascent + fontMetrics.descent) / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        edgePaint.color = Color.GRAY
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, edgePaint)
        edgePaint.color = Color.BLUE
        edgePaint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            -90f,
            degree.toFloat(),
            false,
            edgePaint
        )
        canvas.drawText("$degree°", (width / 2).toFloat(), height / 2 - fontYOffset, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val animator = ObjectAnimator.ofInt(this, "degree", 0, 360)
        animator.duration = 5000
        animator.start()
        return super.onTouchEvent(event)
    }

}