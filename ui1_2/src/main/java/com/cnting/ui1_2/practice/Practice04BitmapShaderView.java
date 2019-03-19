package com.cnting.ui1_2.practice;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.cnting.ui1_2.R;

public class Practice04BitmapShaderView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    BitmapShader bitmapShader1, bitmapShader2, bitmapShader3;

    public Practice04BitmapShaderView(Context context) {
        super(context);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 BitmapShader
        // Bitmap: R.drawable.batman
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        bitmapShader1 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        bitmapShader3 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(bitmapShader1);
        canvas.drawCircle(200, 200, 200, paint);
        paint.setShader(bitmapShader2);
        canvas.drawRect(500, 0, 1100, 400, paint);
        paint.setShader(bitmapShader3);
        canvas.drawCircle(200, 600, 200, paint);
    }
}
