package com.cnting.ui1_4.practice;

import android.content.Context;
import android.graphics.*;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;

import com.cnting.ui1_4.R;

public class Practice07MatrixTranslateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Matrix matrix;
    ColorMatrixColorFilter colorFilter;

    public Practice07MatrixTranslateView(Context context) {
        super(context);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        matrix = new Matrix();

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        colorFilter = new ColorMatrixColorFilter(colorMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        paint.setColorFilter(null);

        canvas.save();
        matrix.preTranslate(-100, -100);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();
        matrix.reset();

        canvas.save();
        matrix.preTranslate(-point2.x - bitmap.getWidth() / 2, -point2.y - bitmap.getHeight() / 2);
        matrix.postScale(0.5f, 0.5f);
        matrix.postTranslate(point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
