package com.cnting.ui1_4.practice;

import android.content.Context;
import android.graphics.*;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;

import com.cnting.ui1_4.R;

public class Practice04ScaleView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice04ScaleView(Context context) {
        super(context);
    }

    public Practice04ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(1.5f, 1.5f, point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();
        //看原始位置
        paint.setColorFilter(new LightingColorFilter(0xffffff, 0x003300));
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);

        paint.setColorFilter(null);
        canvas.save();
        canvas.scale(0.5f, 0.5f);   //这里是以左上角为原点进行缩放
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
        //看原始位置
        paint.setColorFilter(new LightingColorFilter(0xffffff, 0x990000));
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
    }
}
