package com.cnting.ui1_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class Practice5DrawOvalView extends View {

    public Practice5DrawOvalView(Context context) {
        super(context);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint();
    RectF rectf = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawOval() 方法画椭圆

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        int left = getWidth() / 2 - 100;
        int right = getWidth() / 2 + 100;
        int top = getHeight() / 2 - 50;
        int bottom = getHeight() / 2 + 50;
        rectf.set(left, top, right, bottom);
        canvas.drawOval(rectf, paint);

    }
}
