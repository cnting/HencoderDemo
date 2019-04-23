package com.cnting.ui1_1.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.cnting.ui1_1.R

class Practice13BitmapView : View {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    val paint = Paint(ANTI_ALIAS_FLAG)
    val bitmapMatrix = Matrix()

    init {
    }

    private fun getBitmap(): Bitmap {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.batman1, option)
        option.inSampleSize = calculateSampleSize(option, 200, 200)
        option.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, R.drawable.batman1, option)
    }

    private fun calculateSampleSize(option: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val width = option.outWidth
        val height = option.outHeight
        var inSampleSize = 1
        val halfWidth = width / 2
        val halfHeight = height / 2
        while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
            inSampleSize *= 2
        }
        return inSampleSize
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmap = getBitmap()

        //旋转
        bitmapMatrix.postRotate(30f)
        canvas.drawBitmap(bitmap, bitmapMatrix, paint)
        bitmapMatrix.reset()

        //画圆角
        val saved = canvas.saveLayer(null, null)
        val rectF = RectF(400f, 0f, 800f, 200f)
        canvas.drawRoundRect(rectF, 30f, 30f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rectF.left, rectF.top, paint)
        canvas.restoreToCount(saved)

        bitmap.recycle()


    }

}
