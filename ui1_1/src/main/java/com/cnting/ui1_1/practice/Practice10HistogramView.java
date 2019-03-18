package com.cnting.ui1_1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Practice10HistogramView extends View {

    Map<String, Integer> map = new TreeMap<>();
    Paint textPaint = new Paint();
    Paint paint = new Paint();
    RectF rectF = new RectF();

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        map.put("Froyo", 1);
        map.put("GB", 5);
        map.put("ICS", 5);
        map.put("JB", 20);
        map.put("KitKat", 30);
        map.put("L", 35);
        map.put("M", 4);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        int margin = 100;
        int maxHeight = 500;
        int maxWidth = getWidth() - margin * 2;
        int gapWidth = 15;
        int columnWidth = (maxWidth - gapWidth * (map.size() + 1)) / map.size();

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        canvas.drawLine(margin, margin, margin, maxHeight + margin, paint);
        canvas.drawLine(margin, maxHeight + margin, maxWidth + margin, maxHeight + margin, paint);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        Iterator iterator = map.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();

            int left = margin + (index + 1) * gapWidth + (columnWidth * index);
            int right = left + columnWidth;
            int bottom = maxHeight + margin;
            int top = bottom - maxHeight * entry.getValue() / 100;
            rectF.set(left, top, right, bottom);
            canvas.drawRect(rectF, paint);

            canvas.drawText(entry.getKey(), left + columnWidth / 2, bottom + 40, textPaint);

            index++;
        }
    }
}
