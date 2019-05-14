package com.cnting.ui2_4.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

/**
 * Created by cnting on 2019-05-07
 * 使用ViewDragListener拖动排序
 */
class DragListenerSortView : ViewGroup {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private val columns = 2
    private val rows = 3
    private var childWidth = 0
    private var childHeight = 0
    private val orderedChildren = mutableListOf<View>()
    private var dragedView: View? = null

    fun init() {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        childWidth = width / columns
        childHeight = height / rows
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (0 until childCount)
            .forEach {
                val child = getChildAt(it)
                val cl = (it % 2) * child.measuredWidth
                val cr = cl + child.measuredWidth
                val ct = (it / 2) * child.measuredHeight
                val cb = ct + child.measuredHeight
                child.layout(cl, ct, cr, cb)
            }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        (0 until childCount)
            .forEach { it ->
                val child = getChildAt(it)
                orderedChildren.add(child)
                child.setOnDragListener(viewDragListener)
                child.setOnLongClickListener {
                    dragedView = it
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        it.startDragAndDrop(  //拖拽的是生成的阴影，而不是实际的view。view在拖拽和释放后都没有改变位置，需要我们自己改变
                            null,
                            DragShadowBuilder(it),
                            it,
                            0
                        )
                    } else {
                        it.startDrag(null, DragShadowBuilder(it), it, 0)
                    }
                }
            }
    }

    private val viewDragListener = OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {  //每个view都会调用
//                Log.d("start", "===>view:${v.contentDescription}")
                if (event.localState == v) {
                    v.visibility = View.INVISIBLE
                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {  //进入到某个view的区域
//                Log.i("enter", "===>view:${v.contentDescription}")
                if (event.localState != v) {
                    sortChild2(v)
                }
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
            }
            DragEvent.ACTION_DRAG_EXITED -> {
            }
            DragEvent.ACTION_DROP -> {    //在某个view里释放
            }
            DragEvent.ACTION_DRAG_ENDED -> {  //每个view都会调用
                v.visibility = View.VISIBLE
            }
        }
        true
    }

    private fun sortChild2(enterView: View) {
        if (dragedView == null || enterView == dragedView) {
            return
        }
        val index = orderedChildren.indexOf(dragedView!!)
        if (index < 0) {
            return
        }
        val moveIndex = orderedChildren.indexOf(enterView)
        if (index == moveIndex) {
            return
        }
        orderedChildren.removeAt(index)
        orderedChildren.add(moveIndex, dragedView!!)

        orderedChildren.forEachIndexed { i, view ->
            val childLeft = (i % 2) * childWidth
            val childTop = (i / 2) * childHeight
            view.animate()
                .x(childLeft.toFloat())
                .y(childTop.toFloat())
                .duration = 150
        }
    }
}