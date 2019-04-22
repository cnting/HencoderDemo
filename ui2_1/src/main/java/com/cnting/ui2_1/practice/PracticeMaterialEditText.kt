package com.cnting.ui2_1.practice

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.cnting.ui2_1.R
import com.cnting.ui2_1.Utils

/**
 * Created by cnting on 2019-04-22
 *
 */
class PracticeMaterialEditText : EditText {
    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private val floatingTextSize = Utils.dpToPixel(12f)
    private val floatingTextBottomMargin = Utils.dpToPixel(8f)
    private val floatingTextLeftPadding = Utils.dpToPixel(5f)
    private var floatingFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val floatingAnimator by lazy {
        ObjectAnimator.ofFloat(
            this@PracticeMaterialEditText,
            "floatingFraction",
            0f,
            1f
        )
    }
    var backgroundRect = Rect()
    var hasShowFloatingHint = false
    var useFloatingHint = true
        set(value) {
            field = value
            onUseFloatingHintChange()
        }
    var textPaint = Paint()

    private fun init(context: Context?, attrs: AttributeSet?) {
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.PracticeMaterialEditText)
        useFloatingHint =
            typeArray?.getBoolean(R.styleable.PracticeMaterialEditText_useFloatingHint, useFloatingHint)
                ?: useFloatingHint
        typeArray?.recycle()

        textPaint.textSize = Utils.spToPixel(12f)
        onUseFloatingHintChange()
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!useFloatingHint) {
                    return
                }
                if (hasShowFloatingHint && TextUtils.isEmpty(text)) {
                    hasShowFloatingHint = false
                    floatingAnimator.reverse()
                } else if (!hasShowFloatingHint && !TextUtils.isEmpty(text)) {
                    hasShowFloatingHint = true
                    floatingAnimator.start()
                }
            }
        })
    }

    private fun onUseFloatingHintChange() {
        backgroundRect = background.bounds
        if (useFloatingHint) {
            setPadding(
                paddingLeft,
                (backgroundRect.top + floatingTextSize + floatingTextBottomMargin).toInt(),
                paddingRight,
                paddingBottom
            )
        } else {
            setPadding(
                paddingLeft,
                backgroundRect.top,
                paddingRight,
                paddingBottom
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.alpha = (255 * floatingFraction).toInt()
        val y = floatingTextSize * (1 - floatingFraction)
        canvas.drawText(hint as String, floatingTextLeftPadding, floatingTextSize + y, textPaint)
    }
}