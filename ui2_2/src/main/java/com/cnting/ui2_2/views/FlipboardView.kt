package com.cnting.ui2_2.views

import android.animation.*
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import com.cnting.ui2_2.R

/**
 * Created by cnting on 2019-04-16
 *
 */
class FlipboardView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var bitmap: Bitmap? = null
    val paint = Paint()
    val camera = Camera()
    var degree: Int = 90
        set(value) {
            field = value
            invalidate()
        }
    private var topCameraRotate = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var bottomCameraRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.flipboard_logo)
//        camera.setLocation(0f, 0f, -8f * Resources.getSystem().displayMetrics.densityDpi)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val bitmapX = (width / 2 - bitmap!!.width / 2).toFloat()
        val bitmapY = (height / 2 - bitmap!!.height / 2).toFloat()

        canvas.save()
        camera.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.rotate(-degree.toFloat())
        camera.rotateX(topCameraRotate)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-(bitmap!!.width).toFloat(), -bitmap!!.height.toFloat(), (bitmap!!.width).toFloat(), 0f)
        canvas.rotate(degree.toFloat())
        canvas.translate(-width / 2f, -height / 2f)
        canvas.drawBitmap(
            bitmap!!,
            bitmapX,
            bitmapY,
            paint
        )
        camera.restore()
        canvas.restore()

        canvas.save()
        camera.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.rotate(-degree.toFloat())
        camera.rotateX(bottomCameraRotate)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-(bitmap!!.width).toFloat(), 0f, (bitmap!!.width).toFloat(), (bitmap!!.height).toFloat())
        canvas.rotate(degree.toFloat())
        canvas.translate(-width / 2f, -height / 2f)
        canvas.drawBitmap(
            bitmap!!,
            bitmapX,
            bitmapY,
            paint
        )
        camera.restore()
        canvas.restore()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        topCameraRotate = 0f
        bottomCameraRotate = 0f
        val animator1 = ObjectAnimator.ofFloat(this, "bottomCameraRotate", 0f, 40f)
        animator1.duration = 300

        val animator2 = ObjectAnimator.ofInt(this, "degree", 90, 360)
        animator2.duration = 2000
        animator2.interpolator = AccelerateDecelerateInterpolator()

        val animator3 = ObjectAnimator.ofFloat(this, "topCameraRotate", 0f, -40f)
        animator3.duration = 300

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(animator1, animator2, animator3)
        animatorSet.start()

        return super.onTouchEvent(event)
    }

}