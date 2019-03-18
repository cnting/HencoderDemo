package com.cnting.ui1_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class Practice3DrawRectView extends View {

    public Practice3DrawRectView(Context context) {
        super(context);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice3DrawRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint();
    RectF rectF = new RectF();
    int width = 300;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawRect() 方法画矩形

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        int left = getWidth() / 2 - width / 2;
        int right = left + width;
        int top = getHeight() / 2 - width / 2;
        int bottom = top + width;
        rectF.set(left, top, right, bottom);
        canvas.drawRect(rectF, paint);
    }
}
