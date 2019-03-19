package com.cnting.ui1_2.practice;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class Practice01LinearGradientView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Shader shader1 = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
    Shader shader2 = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
    Shader shader3 = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);

    public Practice01LinearGradientView(Context context) {
        super(context);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 LinearGradient
        // LinearGradient 的参数：坐标：(100, 100) 到 (500, 500) ；颜色：#E91E63 到 #2196F3
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(shader1);
        canvas.drawCircle(300, 300, 200, paint);
        paint.setShader(shader2);
        canvas.drawCircle(700, 300, 200, paint);
        paint.setShader(shader3);
        canvas.drawCircle(300, 700, 200, paint);
    }
}
