package com.cnting.ui2_1.practice;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;

/**
 * 需要把它写成正方形的 ImageView
 */
public class Practice01SquareImageView extends ImageView {
    public Practice01SquareImageView(Context context) {
        super(context);
    }

    public Practice01SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写 onMeasure()，调整尺寸，让 ImageView 总是正方形
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.不再调用父类的onMeasure()，全靠自己
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 100;
        int height = 300;
        //2.使用resolveSize()来检查width和height是否符合父view的限制
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);

        //3.再用 setMeasuredDimension(width, height) 来保存最终的宽度和高度
        setMeasuredDimension(width, height);
    }

    //        //1.调用父类的onMeasure()获得测量后的尺寸，是保存在measureWidth和measureHeight中
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        //2.使用自己的算法得到新的尺寸结果
//        int measureWidth = getMeasuredWidth();
//        int measureHeight = getMeasuredHeight();
//        int size = Math.min(measureWidth, measureHeight);
//
//        //3.再用 setMeasuredDimension(width, height) 来保存最终的宽度和高度
//        setMeasuredDimension(size, size);
}
