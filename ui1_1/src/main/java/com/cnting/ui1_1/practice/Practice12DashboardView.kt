package com.cnting.ui1_1.practice

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.cnting.ui1_1.UiUtil

import java.util.ArrayList
import java.util.TreeMap

class Practice12DashboardView : View {

    //仪表盘
    private val boardRadius: Float = UiUtil.dp2px(130f)
    private var boardRect: RectF? = null
    private val boardPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //刻度
    private val dashWidth = UiUtil.dp2px(2f)
    private val dashRect: RectF? = RectF(0f, 0f, dashWidth, UiUtil.dp2px(10f))
    private val dashSize = 20
    private val dashPath = Path()
    private val path = Path()
    private var pathEffect: PathDashPathEffect? = null
    var pointerIndex = 0
        set(value) {
            field = value
            invalidate()
        }

    private val pointerRadius = UiUtil.dp2px(100f)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    init {
        boardPaint.style = Paint.Style.STROKE
        boardPaint.strokeWidth = UiUtil.dp2px(3f)
        dashPath.addRect(dashRect, Path.Direction.CW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        boardRect = RectF(
            (width / 2 - boardRadius),
            (height / 2 - boardRadius),
            (width / 2 + boardRadius),
            (height / 2 + boardRadius)
        )

        path.addArc(boardRect, (60 + 90).toFloat(), (360 - 120).toFloat())
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path, false)
        val length = pathMeasure.length  //计算周长
        pathEffect = PathDashPathEffect(dashPath, (length - dashWidth) / dashSize, 0f, PathDashPathEffect.Style.MORPH)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, boardPaint)
        boardPaint.pathEffect = pathEffect
        canvas.drawPath(path, boardPaint)
        boardPaint.pathEffect = null

        val degree = getDegree()
        canvas.drawLine(
            width / 2f, height / 2f,
            width / 2f + pointerRadius * Math.cos(degree).toFloat(),
            height / 2f + pointerRadius * Math.sin(degree).toFloat(),
            boardPaint
        )
    }

    private fun getDegree(): Double {
        return Math.toRadians((60 + 90) + (360 - 120) / dashSize * pointerIndex.toDouble())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val animator = ObjectAnimator.ofInt(this, "pointerIndex", 0, dashSize + 1)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.RESTART
        animator.duration = dashSize * 1000L
        animator.interpolator = LinearInterpolator()
        animator.start()
        return super.onTouchEvent(event)
    }

}
