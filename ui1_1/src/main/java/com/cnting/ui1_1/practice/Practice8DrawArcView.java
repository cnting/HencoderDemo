package com.cnting.ui1_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class Practice8DrawArcView extends View {

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint();
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        rectF.set(getWidth() / 2 - 200, getHeight() / 2 - 100, getWidth() / 2 + 200, getHeight() / 2 + 100);
        canvas.drawArc(rectF, 0, 100, true, paint);
        canvas.drawArc(rectF, 110, 160, false, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, 180, 260, false, paint);
    }
}
