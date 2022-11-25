package com.cnting.ui1_4.practice;

import android.content.Context;
import android.graphics.*;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.cnting.ui1_4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Camera camera;

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        camera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = point1.x + bitmap.getWidth() / 2;
        int centerY = point1.y + bitmap.getHeight() / 2;
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);    //旋转后移回来
        camera.rotateX(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);   //旋转之前把中间点移到旋转的轴心（轴心是原点，且没法移动）
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        camera.restore();
        canvas.restore();

        centerX = point2.x + bitmap.getWidth() / 2;
        centerY = point2.y + bitmap.getHeight() / 2;
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        camera.rotateY(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
