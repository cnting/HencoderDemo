package com.cnting.ui1_3.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cnting.ui1_3.R
import com.cnting.ui1_3.UiUtil

/**
 * Created by cnting on 2019-04-18
 *
 */
class Practice16View : View {

    val paint = Paint()
    private var bitmap: Bitmap? = null
    val text =
        "台湾历年来发生大地震均有地裂、山崩、断层等地壳变动之状况，如1906年3月17日嘉义大地震，产生梅山断层，长达13千米，水平变位最大为240公分，垂直变位最大为180公分，并有显著之地裂及喷泥等现象。1999年9月21日集集大地震，由车笼埔断层造成约100千米长之地表破裂，水平最大变位7米，垂直最大变位达4米，多处有喷沙、喷泥现象[4]。然而也有地震起因于未破裂到地表上而无法用肉眼判断之盲断层所致，例如2013年3月27日于南投县仁爱乡发生之南投地震以及2016年2月6日于高雄市美浓区发生之台南地震即是。台湾历年来发生大地震均有地裂、山崩、断层等地壳变动之状况，如1906年3月17日嘉义大地震，产生梅山断层，长达13千米，水平变位最大为240公分，垂直变位最大为180公分，并有显著之地裂及喷泥等现象。1999年9月21日集集大地震，由车笼埔断层造成约100千米长之地表破裂，水平最大变位7米，垂直最大变位达4米，多处有喷沙、喷泥现象[4]。然而也有地震起因于未破裂到地表上而无法用肉眼判断之盲断层所致，例如2013年3月27日于南投县仁爱乡发生之南投地震以及2016年2月6日于高雄市美浓区发生之台南地震即是。台湾历年来发生大地震均有地裂、山崩、断层等地壳变动之状况，如1906年3月17日嘉义大地震，产生梅山断层，长达13千米，水平变位最大为240公分，垂直变位最大为180公分，并有显著之地裂及喷泥等现象。1999年9月21日集集大地震，由车笼埔断层造成约100千米长之地表破裂，水平最大变位7米，垂直最大变位达4米，多处有喷沙、喷泥现象[4]。然而也有地震起因于未破裂到地表上而无法用肉眼判断之盲断层所致，例如2013年3月27日于南投县仁爱乡发生之南投地震以及2016年2月6日于高雄市美浓区发生之台南地震即是。台湾历年来发生大地震均有地裂、山崩、断层等地壳变动之状况，如1906年3月17日嘉义大地震，产生梅山断层，长达13千米，水平变位最大为240公分，垂直变位最大为180公分，并有显著之地裂及喷泥等现象。1999年9月21日集集大地震，由车笼埔断层造成约100千米长之地表破裂，水平最大变位7米，垂直最大变位达4米，多处有喷沙、喷泥现象[4]。然而也有地震起因于未破裂到地表上而无法用肉眼判断之盲断层所致，例如2013年3月27日于南投县仁爱乡发生之南投地震以及2016年2月6日于高雄市美浓区发生之台南地震即是。台湾历年来发生大地震均有地裂、山崩、断层等地壳变动之状况，如1906年3月17日嘉义大地震，产生梅山断层，长达13千米，水平变位最大为240公分，垂直变位最大为180公分，并有显著之地裂及喷泥等现象。1999年9月21日集集大地震，由车笼埔断层造成约100千米长之地表破裂，水平最大变位7米，垂直最大变位达4米，多处有喷沙、喷泥现象[4]。然而也有地震起因于未破裂到地表上而无法用肉眼判断之盲断层所致，例如2013年3月27日于南投县仁爱乡发生之南投地震以及2016年2月6日于高雄市美浓区发生之台南地震即是。"
    private var yBaseline = 100f
    private val textMeasureWidth = floatArrayOf(0f)
    private var bitmapTop = 0
    private var bitmapBottom = 0
    private var fontSpacing = 0f


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.batman)
        paint.textSize = UiUtil.sp2px(14f)
        fontSpacing = paint.fontSpacing
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmapTop = (height / 2 - bitmap!!.height / 2)
        bitmapBottom = bitmapTop + bitmap!!.height
        canvas.drawBitmap(bitmap!!, 0f, bitmapTop.toFloat(), paint)

        var endIndex: Int
        var oldEndIndex = 0
        while (oldEndIndex < text.length) {

            val maxWidth =
                if (yBaseline < bitmapTop || yBaseline - fontSpacing > bitmapBottom) {
                    width.toFloat()
                } else {
                    (width - bitmap!!.width).toFloat()
                }
            endIndex = paint.breakText(text, oldEndIndex, text.length, true, maxWidth, textMeasureWidth)
            canvas.drawText(text, oldEndIndex, oldEndIndex + endIndex, width - textMeasureWidth[0], yBaseline, paint)

            oldEndIndex += endIndex
            yBaseline += fontSpacing
        }

        bitmap?.recycle()
    }


}