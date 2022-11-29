package com.cnting.ui2_4.views

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Created by cnting on 2019-05-14
 * 拖动放到指定位置变色
 */
class DragAndDropView : FrameLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        (0 until childCount)
            .forEach { it ->
                val child = getChildAt(it)
                child.setOnDragListener(onDragListener)
                child.setOnLongClickListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        it.startDragAndDrop(null, DragShadowBuilder(it), it, 0)
                    } else {
                        it.startDrag(null, DragShadowBuilder(it), it, 0)
                    }
                }
            }
    }

    private val onDragListener = OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {  //每个view都会调用
                if (event.localState == v) {
                    v.visibility = View.INVISIBLE
                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {  //进入到某个view的区域，该view调用
                if (event.localState == getChildAt(0) && v == getChildAt(1)) {
                    getChildAt(1).setBackgroundColor(Color.RED)
                }
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
            }
            DragEvent.ACTION_DRAG_EXITED -> {  //退出某个view的区域，该view调用
                if (event.localState == getChildAt(0) && v == getChildAt(1)) {
                    getChildAt(1).setBackgroundColor(Color.GRAY)
                }
            }
            DragEvent.ACTION_DROP -> {    //在某个view里释放，该view调用
            }
            DragEvent.ACTION_DRAG_ENDED -> {  //每个view都会调用
                v.visibility = View.VISIBLE
                getChildAt(1).setBackgroundColor(Color.GRAY)
            }
        }
        true
    }
}