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
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var bitmap: Bitmap
    var bitmapWidth = 0f
    var bitmapHeight = 0f
    val paint = Paint()
    val camera = Camera()
    val animatorSet = AnimatorSet()
    var degree: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var topCameraRotate = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var rightCameraRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.flipboard_logo)
        bitmapWidth = bitmap.width.toFloat()
        bitmapHeight = bitmap.height.toFloat()
//        camera.setLocation(0f, 0f, -8f * Resources.getSystem().displayMetrics.densityDpi)

        val animator0 = ObjectAnimator.ofFloat(this, "rightCameraRotate", 0f, -40f)
        animator0.duration = 500

        val animator2 = ObjectAnimator.ofFloat(this, "degree", 0f, 270f)
        animator2.duration = 2000
        animator2.interpolator = AccelerateDecelerateInterpolator()

        val animator3 = ObjectAnimator.ofFloat(this, "topCameraRotate", 0f, -40f)
        animator3.duration = 500

        animatorSet.playSequentially(animator0, animator2, animator3)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val bitmapX = (width / 2 - bitmapWidth / 2)
        val bitmapY = (height / 2 - bitmapHeight / 2)

        //不变换的一半
        canvas.save()
        camera.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.rotate(-degree)
        camera.rotateY(-topCameraRotate) //这是最后一步，此时canvas已经旋转了270，所以是沿着y轴（没理解的话把x、y坐标系箭头标出来就知道了）
        camera.applyToCanvas(canvas)
        canvas.clipRect(-bitmapWidth, -bitmapHeight, 0f, bitmapHeight)
        canvas.rotate(degree)
        canvas.translate(-width / 2f, -height / 2f)
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint)
        camera.restore()
        canvas.restore()

        //变换的一半
        canvas.save()
        camera.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.rotate(-degree)
        camera.rotateY(rightCameraRotate)
        camera.applyToCanvas(canvas)
        canvas.clipRect(0f, -bitmapHeight, bitmapWidth, bitmapHeight)
        canvas.rotate(degree)
        canvas.translate(-width / 2f, -height / 2f)
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint)
        camera.restore()
        canvas.restore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnClickListener {
            topCameraRotate = 0f
            rightCameraRotate = 0f
            degree = 0f

            postDelayed({
                animatorSet.start()
            }, 500)
        }
    }

}