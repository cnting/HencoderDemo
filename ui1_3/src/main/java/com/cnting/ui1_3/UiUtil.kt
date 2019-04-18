package com.cnting.ui1_3

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by cnting on 2019-04-17
 *
 */
object UiUtil {
    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }

    fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics)
    }
}