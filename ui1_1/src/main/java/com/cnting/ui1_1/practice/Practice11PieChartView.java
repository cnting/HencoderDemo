package com.cnting.ui1_1.practice;

import android.content.Context;
import android.graphics.*;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Practice11PieChartView extends View {

    Map<String, Integer> map = new TreeMap<>();
    int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
    float gap = 1.5f;
    int pieRadius = 300;
    Paint paint = new Paint();
    Paint linePaint = new Paint();
    Paint textPaint = new TextPaint();
    RectF rectF = new RectF();
    RectF clickRectF = new RectF();
    Path path = new Path();
    List<Pie> list = new ArrayList<>();
    boolean hasInit = false;

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        map.put("Froyo", 5);
        map.put("GB", 5);
        map.put("ICS", 5);
        map.put("JB", 15);
        map.put("KitKat", 30);
        map.put("L", 35);
        map.put("M", 5);

        paint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(3);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        if (!hasInit) {
            rectF.set(getWidth() / 2 - pieRadius, getHeight() / 2 - pieRadius, getWidth() / 2 + pieRadius, getHeight() / 2 + pieRadius);
            clickRectF.set(rectF.left + 20, rectF.top + 20, rectF.right + 20, rectF.bottom + 20);
            hasInit = true;

            int startDegree = 0;
            int index = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                paint.setColor(colors[index]);
                int degree = 360 * entry.getValue() / 100;
                Pie pie = new Pie(startDegree, degree, rectF, entry.getKey());
                //画扇形
                pie.drawArc(canvas);
                //画线
                pie.drawLineAndText(canvas);
                list.add(pie);

                startDegree = startDegree + degree;
                index++;
            }
        } else {


        }
    }

    class Pie {
        int startDegree;
        int degree;
        RectF rectF;
        String text;

        public Pie(int startDegree, int degree, RectF rectF, String text) {
            this.startDegree = startDegree;
            this.degree = degree;
            this.rectF = rectF;
            this.text = text;
        }

        public void drawArc(Canvas canvas) {
            canvas.drawArc(rectF, startDegree + gap, degree - gap, true, paint);
        }

        public void drawLineAndText(Canvas canvas) {
            double centerDegree = (startDegree + degree / 2) * Math.PI / 180;
            float lineXStart = (float) (getWidth() / 2 + pieRadius * Math.cos(centerDegree));
            float lineYStart = (float) (getHeight() / 2 + pieRadius * Math.sin(centerDegree));
            float lineXEnd = (float) (getWidth() / 2 + (pieRadius + 20) * Math.cos(centerDegree));
            float lineYEnd = (float) (getHeight() / 2 + (pieRadius + 20) * Math.sin(centerDegree));

            path.reset();
            path.moveTo(lineXStart, lineYStart);
            path.lineTo(lineXEnd, lineYEnd);
            if (centerDegree <= Math.PI / 2 || centerDegree > 3 * Math.PI / 2) {
                path.lineTo(lineXEnd + 70, lineYEnd);
            } else {
                path.lineTo(lineXEnd - 70, lineYEnd);
            }
            canvas.drawPath(path, linePaint);

            float textWidth = textPaint.measureText(text);
            if (centerDegree <= Math.PI / 2 || centerDegree > 3 * Math.PI / 2) {
                canvas.drawText(text, lineXEnd + 70, lineYEnd + 15, textPaint);
            } else {
                canvas.drawText(text, lineXEnd - 70 - textWidth, lineYEnd + 15, textPaint);
            }
        }

        public void clickPie() {
            
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            double zSqure = Math.pow(Math.abs(getWidth() / 2 - x), 2) + Math.pow(Math.abs(getHeight() / 2 - y), 2);   //最长边的平方
            if (zSqure < Math.pow(pieRadius, 2)) {//在圆内
                double radian = Math.atan2(y - getHeight() / 2, x - getWidth() / 2);   //弧度
                double degree = radian * 180 / Math.PI;
                if (degree < 0) {
                    degree = degree + 360;
                }
                for (Pie pie : list) {
                    if (degree >= pie.startDegree && degree <= (pie.startDegree + pie.degree)) {
                        pie.clickPie();
                        break;
                    }
                }
                return true;
            } else {
                return super.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }
}