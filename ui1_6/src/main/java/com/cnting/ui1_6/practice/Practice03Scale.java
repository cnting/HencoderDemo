package com.cnting.ui1_6.practice;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cnting.ui1_6.R;

public class Practice03Scale extends RelativeLayout {
    Button animateBt;
    ImageView imageView;

    public Practice03Scale(Context context) {
        super(context);
    }

    public Practice03Scale(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice03Scale(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int clickIndex = 0;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO 在这里处理点击事件，通过 View.animate().scaleX/Y() 来让 View 放缩
                if (clickIndex == 0) {
                    imageView.animate().scaleX(2);
                } else if (clickIndex == 1) {
                    imageView.animate().scaleX(1);
                } else if (clickIndex == 2) {
                    imageView.animate().scaleYBy(2);
                } else if (clickIndex == 3) {
                    imageView.animate().scaleYBy(-2);
                }
                clickIndex++;
                if (clickIndex >= 4) {
                    clickIndex = 0;
                }
            }
        });
    }
}
