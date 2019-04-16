package com.cnting.ui1_6.practice;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.cnting.ui1_6.R;

public class Practice05MultiProperties extends ConstraintLayout {
    Button animateBt;
    ImageView imageView;

    public Practice05MultiProperties(Context context) {
        super(context);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int clickIndex = 0;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        imageView.setAlpha(0f);
        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 在这里处理点击事件，同时对多个属性做动画
                if(clickIndex%2==0){
                    imageView.animate()
                            .scaleX(1)
                            .scaleY(1)
                            .alpha(1)
                            .rotation(360)
                            .translationX(400)
                            .setInterpolator(new AccelerateDecelerateInterpolator());
                }else{
                    imageView.animate()
                            .scaleX(0)
                            .scaleY(0)
                            .alpha(0)
                            .rotation(0)
                            .translationX(0)
                            .setInterpolator(new DecelerateInterpolator());
                }
                clickIndex++;

            }
        });
    }
}
